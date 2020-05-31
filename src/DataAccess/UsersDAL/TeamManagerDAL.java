package DataAccess.UsersDAL;

import DataAccess.DAL;
import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.MySQLConnector;
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

import java.sql.*;
import java.util.HashMap;

public class TeamManagerDAL implements DAL<TeamManager, String> {


    @Override
    public boolean insert(TeamManager objectToInsert) throws SQLException, NoConnectionException, UserIsNotThisKindOfMemberException, UserInformationException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        Connection connection = MySQLConnector.getInstance().connect();


        new MembersDAL().insert(objectToInsert);
        new AssetsDAL().insert((IAsset) objectToInsert);
        objectToInsert = ((TeamManager) objectToInsert);
        String statement = "INSERT INTO teammanagers (UserName,AssetID,Team,TeamOwnerAssignedThis) VALUES (?,?,?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1, objectToInsert.getName());
        preparedStatement.setInt(2, ((TeamManager) objectToInsert).getAssetID());
        if (((TeamManager) objectToInsert).getMyTeam() == null) {
            preparedStatement.setNull(3, Types.VARCHAR);
        } else {
            preparedStatement.setString(3, ((TeamManager) objectToInsert).getMyTeam().getId().toString());
        }
        if (((TeamManager) objectToInsert).getTeamOwnerAssignedThis() == null) {
            preparedStatement.setNull(4, Types.VARCHAR);
        } else {
            preparedStatement.setString(4, ((TeamManager) objectToInsert).getTeamOwnerAssignedThis().getName());
        }
        preparedStatement.execute();

        HashMap<String, Boolean> permissions = ((TeamManager) objectToInsert).getPermissions();
        for (String permission : permissions.keySet()) {
            new TeamManagerPermissionsDAL().insert(new Pair<>(new Pair<>(permission, objectToInsert.getName()), permissions.get(permission)));
        }

        return true;

    }

    @Override
    public boolean update(TeamManager objectToUpdate) throws SQLException, NoConnectionException, UserIsNotThisKindOfMemberException, UserInformationException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        /**MEMBER DETAILS UPDATE*/
        new MembersDAL().update(objectToUpdate);

        /**ASSET DETAILS UPDATE*/
        new AssetsDAL().update(((IAsset) objectToUpdate));
        Connection connection = MySQLConnector.getInstance().connect();


        /***PERMISSION UPDATE**/
        HashMap<String,Boolean> permissions = ((TeamManager)objectToUpdate).getPermissions();
        for (String desc : permissions.keySet()) {
            new TeamManagerPermissionsDAL().update(new Pair<>(new Pair<>(objectToUpdate.getName(),desc),permissions.get(desc)));
        }

        /**TEAM MANAGER DETAILS UPDATE*/
        String statement = "UPDATE teammanagers SET AssetID =?, Team =?, TeamOwnerAssignedThis =?";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setInt(1, ((TeamManager) objectToUpdate).getAssetID());
        if (((TeamManager) objectToUpdate).getMyTeam() == null) {
            preparedStatement.setNull(2, Types.VARCHAR);
        } else {
            preparedStatement.setString(2, ((TeamManager) objectToUpdate).getMyTeam().getId().toString());
        }
        if (((TeamManager) objectToUpdate).getTeamOwnerAssignedThis() == null) {
            preparedStatement.setNull(3, Types.VARCHAR);
        } else {
            preparedStatement.setString(3, ((TeamManager) objectToUpdate).getTeamOwnerAssignedThis().getName());
        }
        int ans = preparedStatement.executeUpdate();

        return ans ==1;
    }

    @Override
    public TeamManager select(String objectIdentifier, boolean  bidirectionalAssociation) throws NoConnectionException, UserInformationException, SQLException, UserIsNotThisKindOfMemberException, NoPermissionException {
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

        /**TEAM MANAGER DETAILS*/
        statement = "SELECT AssetID,Team,TeamOwnerAssignedThis FROM teammanagers WHERE UserName = ?;";
        preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1, objectIdentifier);
        rs = preparedStatement.executeQuery();

        if (!rs.next()) {
            throw new UserIsNotThisKindOfMemberException();
        }

        int assetID = rs.getInt(1);
        String teamID = rs.getString(2);
        Team team = null;
        if(bidirectionalAssociation) {
            team = new TeamsDAL().select(teamID,true);
        }
        String ownerAssigningUserName = rs.getString(3);
        TeamOwner ownerAssigning = new TeamOwnersDAL().select(ownerAssigningUserName,false);


        /**ASSET DETAILS*/
        statement = "SELECT Value FROM assets WHERE AssetID = ?;";
        preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setInt(1, assetID);
        rs = preparedStatement.executeQuery();
        rs.next();
        int assetVal = rs.getInt(1);

        /**PERMISSIONS DETAILS**/
        statement = "SELECT Description,  isPermitted FROM permissions WHERE TeamManager=?;";
        preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1, objectIdentifier);
        rs = preparedStatement.executeQuery();

        HashMap<String, Boolean> permissions = new HashMap<>();
        while (rs.next()) {
            permissions.put(rs.getString(1), rs.getBoolean(2));
        }

        TeamManager member = new TeamManager(objectIdentifier, password, realName, assetVal, assetID, team, ownerAssigning, permissions);

        return member;
    }

    @Override
    public boolean delete(String objectIdentifier) {
        return false;
    }
}
