package DataAccess.UsersDAL;

import DataAccess.AlertsDAL.MemberAlertsDAL;
import DataAccess.DAL;
import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.MySQLConnector;
import Domain.Alerts.IAlert;
import Domain.Users.Commissioner;
import Domain.Users.SystemManager;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SystemManagerDAL implements DAL<SystemManager,String> {



    @Override
    public boolean insert(SystemManager objectToInsert) throws SQLException, NoConnectionException, UserInformationException, UserIsNotThisKindOfMemberException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
       new MembersDAL().insert(objectToInsert);
        Connection connection = MySQLConnector.getInstance().connect();

        String statement = "INSERT INTO system_managers (userName) VALUES (?);";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectToInsert.getName());
        preparedStatement.execute();
        connection.close();
        return true;

    }

    @Override
    public boolean update(SystemManager objectToUpdate) throws SQLException, UserIsNotThisKindOfMemberException, UserInformationException, NoConnectionException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        return new MembersDAL().update(objectToUpdate);
    }

    @Override
    public SystemManager select(String objectIdentifier, boolean  bidirectionalAssociation) throws SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException, NoPermissionException {
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

        return new SystemManager(objectIdentifier,password,realName,memberAlerts,isActive,AlertsViaMail,mail);
    }

    @Override
    public boolean delete(String objectIdentifier) {
        return false;
    }

    public List<SystemManager> selectAll() throws NoConnectionException, SQLException, NoPermissionException, UserInformationException, UserIsNotThisKindOfMemberException {
        List<SystemManager> allInCharge = new ArrayList<>();
        Connection connection = MySQLConnector.getInstance().connect();
        String statement = "SELECT UserName FROM system_managers";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()){
            allInCharge.add(this.select(rs.getString("UserName"),true));
        }
        return allInCharge;
    }


}
