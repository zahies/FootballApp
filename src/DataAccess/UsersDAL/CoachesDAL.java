package DataAccess.UsersDAL;

import DataAccess.AlertsDAL.MemberAlertsDAL;
import DataAccess.DAL;
import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.MySQLConnector;
import DataAccess.SeasonManagmentDAL.AssetsDAL;
import DataAccess.SeasonManagmentDAL.TeamsDAL;
import DataAccess.UserInformationDAL.PersonalPagesDAL;
import Domain.Alerts.IAlert;
import Domain.SeasonManagment.IAsset;
import Domain.SeasonManagment.Team;
import Domain.Users.Coach;
import Domain.Users.CoachRole;
import Domain.Users.Member;
import Domain.Users.PersonalInfo;
import FootballExceptions.EmptyPersonalPageException;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import javafx.util.Pair;

import java.lang.reflect.Type;
import java.sql.*;
import java.util.LinkedList;
import java.util.Queue;

public class CoachesDAL implements DAL<Coach, String> {


    @Override
    public boolean insert(Coach objectToInsert) throws SQLException, NoConnectionException, mightBeSQLInjectionException, UserInformationException, NoPermissionException, UserIsNotThisKindOfMemberException, DuplicatedPrimaryKeyException {
        Connection connection = MySQLConnector.getInstance().connect();

        new MembersDAL().insert(objectToInsert);
        new AssetsDAL().insert((IAsset) objectToInsert);

        if (checkExist(objectToInsert.getName(), "coaches", "UserName","")) {
            throw new UserInformationException();
        }
        String statement = "INSERT INTO coaches (UserName, Team, PersonalPage, AssetID, training,Role) VALUES(?,?,?,?,?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1, objectToInsert.getName());
        if(((Coach) objectToInsert).getMyTeam()==null){
            preparedStatement.setNull(2, Types.VARCHAR);
        }else {
            preparedStatement.setString(2, ((Coach) objectToInsert).getMyTeam().getId().toString());
        }
        if(((Coach) objectToInsert).getInfo()==null){
            preparedStatement.setNull(3, Types.VARCHAR);
        }else {
            preparedStatement.setInt(3, ((Coach) objectToInsert).getInfo().getPageID());
        }
        preparedStatement.setInt(4, ((Coach) objectToInsert).getAssetID());
        preparedStatement.setString(5, ((Coach) objectToInsert).getTraining());
        preparedStatement.setString(6,objectToInsert.getRole().toString());
        preparedStatement.execute();

        return true;
    }

    @Override
    public boolean update(Coach objectToUpdate) throws SQLException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException, NoPermissionException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException {
        Connection connection = MySQLConnector.getInstance().connect();
        new MembersDAL().update(objectToUpdate);

        String statement = "UPDATE coaches SET Team =? , PersonalPage=?, AssetID=?, Training=? , Role=? WHERE UserName=?";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(6, objectToUpdate.getName());
        if (((Coach) objectToUpdate).getMyTeam() == null) {
            preparedStatement.setNull(1, Types.VARCHAR);
        } else {
            preparedStatement.setString(1, ((Coach) objectToUpdate).getMyTeam().getId().toString());
        }
        if (((Coach) objectToUpdate).getInfo() == null) {
            preparedStatement.setNull(2, Types.VARCHAR);
        } else {
            preparedStatement.setInt(2, ((Coach) objectToUpdate).getInfo().getPageID());
        }
        preparedStatement.setInt(3, ((Coach) objectToUpdate).getAssetID());
        preparedStatement.setString(4, ((Coach) objectToUpdate).getTraining());
        preparedStatement.setString(5, ((Coach) objectToUpdate).getRole().toString());

        int ans = preparedStatement.executeUpdate();

        new AssetsDAL().update((IAsset)objectToUpdate);
        return ans ==1;
    }

    @Override
    public Coach select(String objectIdentifier, boolean  bidirectionalAssociation) throws NoConnectionException, SQLException, UserInformationException, NoPermissionException, UserIsNotThisKindOfMemberException {
        Connection connection = MySQLConnector.getInstance().connect();

        /**MEMBER DETAILS*/
        String statement = "SELECT * FROM members WHERE UserName = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1, objectIdentifier);
        ResultSet rs = preparedStatement.executeQuery();

        if (!rs.next()) {
            throw new UserInformationException();
        }
        String password = rs.getString("Password");
        String realName = rs.getString("RealName");
        String mail = rs.getString("MailAddress");
        boolean isActive = rs.getBoolean("isActive");
        boolean AlertsViaMail = rs.getBoolean("AlertsViaMail");

        statement = "SELECT alertObjectID FROM member_alerts WHERE memberUserName = ? ;";
        preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectIdentifier);
        rs = preparedStatement.executeQuery();
        Queue<IAlert> memberAlerts = new LinkedList<>();
        while (rs.next()){
            memberAlerts.add(new MemberAlertsDAL().select(new Pair<String, String>(objectIdentifier,rs.getString(1)),true).getKey().getValue());
        }

        /**COACH DETAILS*/
        statement = "SELECT  * FROM coaches WHERE UserName=?";
        preparedStatement= connection.prepareStatement(statement);
        preparedStatement.setString(1,objectIdentifier);
        rs = preparedStatement.executeQuery();
        if (!rs.next()) {
            throw new UserIsNotThisKindOfMemberException();
        }
        Team team = null;
        if(bidirectionalAssociation){
            team = new TeamsDAL().select(rs.getString("Team"),true);
        }
        PersonalInfo personalInfo = null;
        try {
            personalInfo = new PersonalPagesDAL().select(rs.getInt("PersonalPage"),false);
        } catch (EmptyPersonalPageException e) {
        }
        int assetID = rs.getInt("AssetID");
        String training = rs.getString("Training");
        CoachRole role = CoachRole.valueOf(rs.getString("Role"));

        /**ASSET DETAILS*/
        statement = "SELECT Value FROM assets WHERE AssetID = ?;";
        preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setInt(1, assetID);
        rs = preparedStatement.executeQuery();
        rs.next();
        int assetVal = rs.getInt(1);

        Coach coach = new Coach(objectIdentifier,password,realName,memberAlerts,isActive,AlertsViaMail,mail,assetVal,team,training,role,personalInfo,assetID);
        return coach;
    }

    @Override
    public boolean delete(String objectIdentifier) {
        return false;
    }
}
