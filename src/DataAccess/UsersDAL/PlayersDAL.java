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
import Domain.Users.Member;
import Domain.Users.PersonalInfo;
import Domain.Users.Player;
import FootballExceptions.EmptyPersonalPageException;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import javafx.util.Pair;

import java.sql.*;
import java.util.LinkedList;
import java.util.Queue;

public class PlayersDAL implements DAL<Player, String> {


    @Override
    public boolean insert(Player member) throws SQLException, NoConnectionException, UserInformationException, mightBeSQLInjectionException, NoPermissionException, UserIsNotThisKindOfMemberException, DuplicatedPrimaryKeyException {

        new MembersDAL().insert(member);
        member = ((Player) member);
        new AssetsDAL().insert((IAsset) member);

        Connection connection = MySQLConnector.getInstance().connect();

        String statement = "INSERT INTO players (UserName,DateOfBirth,Team,PersonalPage,Role, AssetID,FootballRate) VALUES (?,?,?,?,?,?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1, member.getName());

        java.util.Date date = ((Player) member).getDateOfBirth();
        if(date == null){
            preparedStatement.setNull(2,Types.DATE);
        }else {
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            preparedStatement.setDate(2, sqlDate);
        }

        if (((Player) member).getMyTeam() == null) {
            preparedStatement.setNull(3,Types.VARCHAR);
        } else {
            preparedStatement.setString(3, ((Player) member).getMyTeam().getId().toString());
        }
        if (((Player) member).getInfo() != null) {
            preparedStatement.setInt(4, ((Player) member).getInfo().getPageID());
        } else {
            preparedStatement.setNull(4,Types.INTEGER);
        }
        preparedStatement.setString(5, ((Player) member).getRole());
        preparedStatement.setInt(6, ((Player) member).getAssetID());
        preparedStatement.setDouble(7, ((Player) member).getFootballRate());
        preparedStatement.execute();

        return true;
    }

    @Override
    public boolean update(Player member) throws SQLException, UserIsNotThisKindOfMemberException, UserInformationException, NoConnectionException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {

        /**MEMBER DETAILS UPDATE*/
        new MembersDAL().update(member);

//        if (checkExist(member.getName(), "fans", "UserName","")) {
//
//        }
        Connection connection = MySQLConnector.getInstance().connect();

        String statement = "UPDATE players SET DateOfBirth=?,Team=?,PersonalPage=?,Role=?,AssetID=?,FootballRate=? WHERE UserName=?";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        java.util.Date date = ((Player) member).getDateOfBirth();
        if(date==null){
            preparedStatement.setNull(1,Types.DATE);
        }else {
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            preparedStatement.setDate(1, sqlDate);
        }

        if (((Player) member).getMyTeam() == null) {
            preparedStatement.setNull(2,Types.VARCHAR);
        } else {
            preparedStatement.setString(2, ((Player) member).getMyTeam().getId().toString());
        }
        if (((Player) member).getInfo() != null) {
            preparedStatement.setInt(3, ((Player) member).getInfo().getPageID());
        } else {
            preparedStatement.setNull(3,Types.INTEGER);
        }
        preparedStatement.setString(4, ((Player) member).getRole());
        preparedStatement.setInt(5, ((Player) member).getAssetID());
        preparedStatement.setDouble(6, ((Player) member).getFootballRate());
        preparedStatement.setString(7,member.getName());
        int ans = preparedStatement.executeUpdate();
        new AssetsDAL().update(((IAsset) member));
        return ans==1;
    }

    @Override
    public Player select(String userName,boolean  bidirectionalAssociation) throws SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException, NoPermissionException {

        Connection connection = MySQLConnector.getInstance().connect();

        /**MEMBER DETAILS*/
        String statement = "SELECT Password,RealName,MailAddress,isActive, AlertsViaMail FROM members WHERE UserName = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1, userName);
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
        preparedStatement.setString(1,userName);
        rs = preparedStatement.executeQuery();
        Queue <IAlert> memberAlerts = new LinkedList<>();
        while (rs.next()){
            memberAlerts.add(new MemberAlertsDAL().select(new Pair<String, String>(userName,rs.getString(1)),true).getKey().getValue());
        }



        /**PLAYER DETAILS*/
        statement = "SELECT Team,PersonalPage,Role, AssetID,FootballRate,DateOfBirth FROM players WHERE UserName = ?;";
        preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1, userName);
        rs = preparedStatement.executeQuery();

        if (!rs.next()) {
            throw new UserIsNotThisKindOfMemberException();
        }

        String teamID = rs.getString(1);
        Team team = null;
        if(bidirectionalAssociation) {
            team = new TeamsDAL().select(teamID,true);
        }
        int personlPID = rs.getInt(2);
        PersonalInfo page = null;
        try {
            page = new PersonalPagesDAL().select(personlPID,false);
        } catch (EmptyPersonalPageException e) {
        }
        String role = rs.getString(3);
        int assetID = rs.getInt(4);
        double rate = rs.getDouble(5);
        java.util.Date dateOfBirth = rs.getDate(6);

        /**ASSET DETAILS*/
        statement = "SELECT Value FROM assets WHERE AssetID = ?;";
        preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setInt(1, assetID);
        rs = preparedStatement.executeQuery();
        rs.next();
        int assetVal = rs.getInt(1);

        Player member = new Player(userName,password,realName,memberAlerts,isActive,AlertsViaMail,mail,assetVal,assetID,team,role,page,dateOfBirth,rate);

        return member;
    }

    @Override
    public boolean delete(String userName) {
        return false;
    }
}
