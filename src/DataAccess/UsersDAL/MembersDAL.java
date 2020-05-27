package DataAccess.UsersDAL;

import DataAccess.AlertsDAL.MemberAlertsDAL;
import DataAccess.DAL;
import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import Domain.Alerts.IAlert;
import Domain.Users.*;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;

public class MembersDAL implements DAL<Member, String> {

    Connection connection = null;

    @Override
    public boolean insert(Member member) throws SQLException, NoConnectionException, UserInformationException, mightBeSQLInjectionException, NoPermissionException, UserIsNotThisKindOfMemberException, DuplicatedPrimaryKeyException {

        connection = this.connect();
        if (checkExist(member.getName(), "members", "UserName","")) {
            throw new UserInformationException();
        }
        String statement = "INSERT INTO members (UserName,Password, RealName, MailAddress,isActive,AlertsViaMail,Type) VALUES (?,?,?,?,?,?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1, member.getName());
        preparedStatement.setString(2, member.getPassword());
        preparedStatement.setString(3, member.getReal_Name());
        preparedStatement.setString(4, member.getMailAddress());
        preparedStatement.setBoolean(5, member.isActive());
        preparedStatement.setBoolean(6, member.isAlertViaMail());
        if(member instanceof Coach){
            preparedStatement.setString(7,"Coach");
        }else if(member instanceof Commissioner){
            preparedStatement.setString(7,"Commissioner");
        }else if(member instanceof Fan){
            preparedStatement.setString(7,"Fan");
        }else if(member instanceof Player){
            preparedStatement.setString(7,"Player");
        }else if(member instanceof Referee){
            preparedStatement.setString(7,"Referee");
        }else if(member instanceof SystemManager){
            preparedStatement.setString(7,"SystemManager");
        }else if(member instanceof TeamManager){
            preparedStatement.setString(7,"TeamManager");
        }else if(member instanceof TeamOwner){
            preparedStatement.setString(7,"TeamOwner");
        }
        preparedStatement.execute();

        Queue<IAlert> memberAlerts = member.getAlertsList();
        for (IAlert alert : memberAlerts) {
            new MemberAlertsDAL().insert(new Pair<>(new Pair<>(member.getName(),alert),alert.getType()));
        }
        connection.close();
        return true;
    }

    @Override
    public boolean update(Member member) throws SQLException, NoConnectionException, mightBeSQLInjectionException, UserIsNotThisKindOfMemberException, UserInformationException, NoPermissionException, DuplicatedPrimaryKeyException {

        connection = connect();
        String statement = "UPDATE members SET Password=?, RealName=?, MailAddress=?,isActive=?,AlertsViaMail =  ? WHERE UserName = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(6, member.getName());
        preparedStatement.setString(1, member.getPassword());
        preparedStatement.setString(2, member.getReal_Name());
        preparedStatement.setString(3, member.getMailAddress());
        preparedStatement.setBoolean(4, member.isActive());
        preparedStatement.setBoolean(5, member.isAlertViaMail());
        int ans = preparedStatement.executeUpdate();

        Queue<IAlert> memberAlerts = member.getAlertsList();
        for (IAlert alert : memberAlerts) {
            new MemberAlertsDAL().update(new Pair<>(new Pair<>(member.getName(),alert),alert.getType()));
        }

        connection.close();
        return ans ==1;
    }

    @Override
    public Member select(String userName) {
        return null;
    }

    @Override
    public boolean delete(String userName) {
        return false;
    }
}
