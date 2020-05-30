package DataAccess.UsersDAL;

import DataAccess.AlertsDAL.MemberAlertsDAL;
import DataAccess.DAL;
import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.MySQLConnector;
import DataAccess.UserInformationDAL.FanFollowingPagesDAL;
import DataAccess.UserInformationDAL.PersonalPagesDAL;
import Domain.Alerts.IAlert;
import Domain.Users.Fan;
import Domain.Users.PersonalInfo;
import FootballExceptions.EmptyPersonalPageException;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class FansDAL implements DAL<Fan, String> {

    @Override
    public boolean insert(Fan objectToInsert) throws SQLException, NoConnectionException, UserInformationException, UserIsNotThisKindOfMemberException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        new MembersDAL().insert(objectToInsert);
        Connection connection = MySQLConnector.getInstance().connect();

        if (checkExist(objectToInsert.getName(), "fans", "UserName","")) {
            throw new DuplicatedPrimaryKeyException();
        }

        String statement = "INSERT INTO fans(UserName) VALUES(?);";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1, objectToInsert.getName());
        preparedStatement.execute();

        HashMap<PersonalInfo, Boolean> personalPagesFollowed = objectToInsert.getPersonalPagesFollowed();
        for (PersonalInfo page : personalPagesFollowed.keySet()) {
            new FanFollowingPagesDAL().insert(new Pair<>(new Pair<>(objectToInsert.getName(), page.getPageID()), personalPagesFollowed.get(page)));
        }
        connection.close();
        return true;
    }

    @Override
    public boolean update(Fan objectToUpdate) throws SQLException, UserIsNotThisKindOfMemberException, UserInformationException, NoConnectionException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        Connection connection = MySQLConnector.getInstance().connect();
        new MembersDAL().update(objectToUpdate);

        HashMap<PersonalInfo, Boolean> personalPagesFollowed = objectToUpdate.getPersonalPagesFollowed();
        for (PersonalInfo page : personalPagesFollowed.keySet()) {
            new FanFollowingPagesDAL().update(new Pair<>(new Pair<>(objectToUpdate.getName(), page.getPageID()), personalPagesFollowed.get(page)));
        }
        connection.close();
        return true;
    }

    @Override
    public Fan select(String objectIdentifier, boolean  bidirectionalAssociation) throws SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException, NoPermissionException, EmptyPersonalPageException {
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

        /***FANS DETAILS*/
        statement = "SELECT * FROM fan_following_pages WHERE MemberUserName=?";
        preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectIdentifier);
        rs = preparedStatement.executeQuery();
        HashMap<PersonalInfo, Boolean> personalPagesFollowed = new HashMap<>();
        if(bidirectionalAssociation) {
            while (rs.next()) {
                PersonalInfo personalInfo = new PersonalPagesDAL().select(rs.getInt("PersonalPageID"), false);
                personalPagesFollowed.put(personalInfo,rs.getBoolean("Alerts"));
            }
        }

        return new Fan(objectIdentifier,password,realName,memberAlerts,isActive,AlertsViaMail,mail,personalPagesFollowed);
    }

    @Override
    public boolean delete(String objectIdentifier) {
        return false;
    }
}
