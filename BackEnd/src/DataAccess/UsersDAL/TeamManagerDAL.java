package DataAccess.UsersDAL;

import DataAccess.DAL;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.SeasonManagmentDAL.AssetsDAL;
import DataAccess.SeasonManagmentDAL.TeamsDAL;
import DataAccess.UserInformationDAL.TeamManagerPermissionsDAL;
import Domain.SeasonManagment.IAsset;
import Domain.SeasonManagment.Team;
import Domain.Users.Member;
import Domain.Users.TeamManager;
import Domain.Users.TeamOwner;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class TeamManagerDAL implements DAL<Member, String> {

    Connection connection = null;
    MembersDAL membersDAL = new MembersDAL();
    AssetsDAL assetsDAL = new AssetsDAL();
    TeamsDAL teamsDAL = new TeamsDAL();
    TeamManagerPermissionsDAL teamManagerPermissionsDAL = new TeamManagerPermissionsDAL();

    @Override
    public boolean insert(Member objectToInsert) throws SQLException, NoConnectionException, UserIsNotThisKindOfMemberException, UserInformationException, NoPermissionException, mightBeSQLInjectionException {
        connection = connect();
        if (connection == null) {
            return false;
        }

        membersDAL.insert(objectToInsert);
        assetsDAL.insert((IAsset) objectToInsert);
        objectToInsert = ((TeamManager) objectToInsert);
        String statement = "INSERT INTO teammanagers (UserName,AssetID,Team,TeamOwnerAssignedThis) VALUES (?,?,?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1, objectToInsert.getName());
        preparedStatement.setInt(2, ((TeamManager) objectToInsert).getAssetID());
        if (((TeamManager) objectToInsert).getMyTeam() == null) {
            preparedStatement.setInt(3, 0);
        } else {
            preparedStatement.setInt(3, ((TeamManager) objectToInsert).getMyTeam().getId());
        }
        preparedStatement.setString(4, ((TeamManager) objectToInsert).getTeamOwnerAssignedThis().getName());
        preparedStatement.execute();

        HashMap<String, Boolean> permissions = ((TeamManager) objectToInsert).getPermissions();
        for (String permission : permissions.keySet()) {
            teamManagerPermissionsDAL.insert(new Pair<>(new Pair<>(permission, objectToInsert), permissions.get(permission)));
        }

        connection.close();
        return true;

    }

    @Override
    public boolean update(Member objectToUpdate, Pair<String, Object/**IN CASE OF PERMISSION UPDATE THIS WILL BE PAIR**/> valToUpdate) throws SQLException, NoConnectionException, UserIsNotThisKindOfMemberException, UserInformationException, NoPermissionException {
        this.select(objectToUpdate.getName());

        /**MEMBER DETAILS UPDATE*/
        if (valToUpdate.getKey().equals("UserName") || valToUpdate.getKey().equals("RealName") || valToUpdate.getKey().equals("Password") || valToUpdate.getKey().equals("isActive") || valToUpdate.getKey().equals("AlertsViaMail") || valToUpdate.getKey().equals("MailAddress")) {
            return membersDAL.update(objectToUpdate, valToUpdate);
        }
        connection = connect();
        if (connection == null) {
            throw new NoConnectionException();
        }
        /***PERMISSION UPDATE**/
        if (valToUpdate.getValue() instanceof Pair) {
            return teamManagerPermissionsDAL.update(new Pair<Pair<String, Member>, Boolean>(new Pair<>(valToUpdate.getKey(), objectToUpdate), false), valToUpdate);
        }
        /**ASSET UPDATE*/
        if (valToUpdate.getKey().equals("AssetVal")) {
            return assetsDAL.update(((IAsset) objectToUpdate), valToUpdate);
        }
        /**TEAM MANAGER DETAILS UPDATE*/
        String statement = "UPDATE teammanagers SET " + valToUpdate.getKey() + " =  ? " +
                "WHERE UserName = ?; ";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        if (valToUpdate.getValue() instanceof String) {
            preparedStatement.setString(1, ((String) valToUpdate.getValue()));
        } else {
            preparedStatement.setInt(1, (Integer) valToUpdate.getValue());
        }
        preparedStatement.setString(2, objectToUpdate.getName());
        preparedStatement.execute();
        connection.close();
        return true;
    }

    @Override
    public Member select(String objectIdentifier) throws NoConnectionException, UserInformationException, SQLException, UserIsNotThisKindOfMemberException {
        connection = connect();
        if (connection == null) {
            throw new NoConnectionException();
        }
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

        /**TEAM MANAGER DETAILS*/
        statement = "SELECT AssetID,Team,TeamOwnerAssignedThis FROM teammanagers WHERE UserName = ?;";
        preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1, objectIdentifier);
        rs = preparedStatement.executeQuery();

        if (!rs.next()) {
            throw new UserIsNotThisKindOfMemberException();
        }

        int assetID = rs.getInt(1);
        int teamID = rs.getInt(2);
        Team team = teamsDAL.select(teamID);
        String ownerAssigningUserName = rs.getString(3);
        Member ownerAssigning = new TeamOwnersDAL().select(ownerAssigningUserName);


        /**ASSET DETAILS*/
        statement = "SELECT AssetVal FROM assets WHERE AssetID = ?;";
        preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setInt(1, assetID);
        rs = preparedStatement.executeQuery();
        rs.next();
        int assetVal = rs.getInt(1);

        /**PERMISSIONS DETAILS**/
        statement = "SELECT PermissionDescription,  isPermitted FROM permissions WHERE TeamManager=?;";
        preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1, objectIdentifier);
        rs = preparedStatement.executeQuery();

        HashMap<String, Boolean> permissions = new HashMap<>();
        while (rs.next()) {
            permissions.put(rs.getString(1), rs.getBoolean(2));
        }

        Member member = new TeamManager(objectIdentifier, password, realName, assetVal, assetID, team, ((TeamOwner) ownerAssigning), permissions);
        connection.close();
        return member;
    }

    @Override
    public boolean delete(String objectIdentifier) {
        return false;
    }
}
