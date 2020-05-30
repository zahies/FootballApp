package DataAccess.UsersDAL;

import DataAccess.AlertsDAL.MemberAlertsDAL;
import DataAccess.DAL;
import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.MySQLConnector;
import DataAccess.UserInformationDAL.FinancialActivitiesDAL;
import Domain.Alerts.IAlert;
import Domain.Users.Commissioner;
import Domain.Users.Member;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

public class CommissionersDAL implements DAL<Commissioner,String> {



    @Override
    public boolean insert(Commissioner objectToInsert) throws SQLException, NoConnectionException, UserInformationException, UserIsNotThisKindOfMemberException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        new MembersDAL().insert(objectToInsert);
        Connection connection = MySQLConnector.getInstance().connect();

        String statement ="INSERT INTO commissioners (userName) VALUES (?);";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectToInsert.getName());
        preparedStatement.execute();

        LinkedList<Pair<String, Integer>> list = objectToInsert.getFinanceAssociationActivity();
        for (Pair<String,Integer> pair: list) {
            new FinancialActivitiesDAL().insert(new Pair<>(new Pair<>(objectToInsert.getName(),pair.getKey()),pair.getValue()));
        }
        connection.close();
        return true;
    }

    @Override
    public boolean update(Commissioner objectToUpdate) throws SQLException, UserIsNotThisKindOfMemberException, UserInformationException, NoConnectionException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        new MembersDAL().insert(objectToUpdate);
        Connection connection = MySQLConnector.getInstance().connect();

        LinkedList<Pair<String, Integer>> list = objectToUpdate.getFinanceAssociationActivity();
        for (Pair<String,Integer> pair: list) {
            new FinancialActivitiesDAL().update(new Pair<>(new Pair<>(objectToUpdate.getName(),pair.getKey()),pair.getValue()));
        }
        connection.close();
        return true;
    }

    @Override
    public Commissioner select(String objectIdentifier, boolean  bidirectionalAssociation) throws SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException, NoPermissionException {
        Connection connection = MySQLConnector.getInstance().connect();

        /**MEMBER DETAILS*/
        String statement = "SELECT Password,RealName,MailAddress,isActive, AlertsViaMail FROM members WHERE UserName = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1, objectIdentifier);
        ResultSet rs = preparedStatement.executeQuery();

        if (!rs.next()) {
            throw new UserInformationException();
        }
        String password = rs.getString(1);
        String realName = rs.getString(2);
        String mail = rs.getString(3);
        boolean isActive = rs.getBoolean(4);
        boolean AlertsViaMail = rs.getBoolean(5);

        statement = "SELECT alertObjectID FROM member_alerts WHERE memberUserName = ? ;";
        preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectIdentifier);
        rs = preparedStatement.executeQuery();
        Queue<IAlert> memberAlerts = new LinkedList<>();
        while (rs.next()){
            memberAlerts.add(new MemberAlertsDAL().select(new Pair<String, String>(objectIdentifier,rs.getString(1)),true).getKey().getValue());
        }

        LinkedList<Pair<String, Integer>> financeAssociationActivity = new LinkedList<>();
        statement = "SELECT * FROM association_financial_activities WHERE Commissioner =?";
        preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectIdentifier);
        rs = preparedStatement.executeQuery();
        while (rs.next()){
            financeAssociationActivity.add(new Pair<>(rs.getString("Info"),rs.getInt("Amount")));
        }


        return new Commissioner(objectIdentifier,password,realName,memberAlerts,isActive,AlertsViaMail,mail,financeAssociationActivity);
    }

    @Override
    public boolean delete(String objectIdentifier) {
        return false;
    }
}
