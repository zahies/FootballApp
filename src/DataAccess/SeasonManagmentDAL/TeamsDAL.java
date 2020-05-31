package DataAccess.SeasonManagmentDAL;

import DataAccess.DAL;
import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.MySQLConnector;
import DataAccess.UserInformationDAL.PersonalPagesDAL;
import DataAccess.UsersDAL.CoachesDAL;
import DataAccess.UsersDAL.PlayersDAL;
import DataAccess.UsersDAL.TeamManagerDAL;
import DataAccess.UsersDAL.TeamOwnersDAL;
import Domain.SeasonManagment.*;
import Domain.Users.*;
import FootballExceptions.EmptyPersonalPageException;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import javafx.util.Pair;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class TeamsDAL implements DAL<Team, String>  {

    Connection connection =null;
    @Override
    public boolean insert(Team objectToInsert) throws SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        connection = MySQLConnector.getInstance().connect();

        String statement = "INSERT INTO teams(TeamID,Name,PersonalPage,Owner,TeamStatus,ControlBudget,isClosed,playersFootballRate,SystemManagerCloser) VALUES (?,?,?,?,?,?,?,?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1, objectToInsert.getId().toString());
        preparedStatement.setString(2, objectToInsert.getName());
        if (objectToInsert.getInfo() != null) {
            preparedStatement.setInt(3, objectToInsert.getInfo().getPageID());
        } else {
            preparedStatement.setNull(3, Types.INTEGER);
        }
        preparedStatement.setString(4, objectToInsert.getOwner().getName());
        preparedStatement.setString(5, objectToInsert.getStatus().toString());
        if (objectToInsert.getControlBudget() != null) {
            preparedStatement.setString(6, objectToInsert.getControlBudget().getObjectID().toString());
        } else {
            preparedStatement.setNull(6,Types.VARCHAR);
        }
        preparedStatement.setBoolean(7, objectToInsert.isActive());
        preparedStatement.setDouble(8, objectToInsert.getPlayersFootballRate());
        preparedStatement.setBoolean(9, objectToInsert.isClosed());
        preparedStatement.execute();


        new TeamOwnersDAL().update(objectToInsert.getOwner());

        MySQLConnector.getInstance().disconnect();

        return true;
    }

    @Override
    public boolean update(Team objectToUpdate) throws SQLException, NoConnectionException {
        connection = MySQLConnector.getInstance().connect();

        String statement ="UPDATE teams SET Name=?,PersonalPage=?,Owner=?,TeamStatus=?,ControlBudget=?,isClosed=?,PlayersFootballRate=?,SystemManagerCloser=? WHERE TeamID=?";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(9, objectToUpdate.getId().toString());
        preparedStatement.setString(1, objectToUpdate.getName());
        if (objectToUpdate.getInfo() != null) {
            preparedStatement.setInt(2, objectToUpdate.getInfo().getPageID());
        } else {
            preparedStatement.setNull(2,Types.INTEGER);
        }
        preparedStatement.setString(3, objectToUpdate.getOwner().getName());
        preparedStatement.setString(4, objectToUpdate.getStatus().toString());
        if (objectToUpdate.getControlBudget() != null) {
            preparedStatement.setString(5, objectToUpdate.getControlBudget().getObjectID().toString());
        } else {
            preparedStatement.setNull(5,Types.VARCHAR);
        }
        preparedStatement.setBoolean(6, objectToUpdate.isActive());
        preparedStatement.setDouble(7, objectToUpdate.getPlayersFootballRate());
        preparedStatement.setBoolean(8, objectToUpdate.isClosed());
        int ans = preparedStatement.executeUpdate();

        MySQLConnector.getInstance().disconnect();

        return ans ==1 ;
    }

    @Override
    public Team select(String objectIdentifier,boolean  bidirectionalAssociation) throws NoConnectionException, SQLException, UserInformationException, NoPermissionException, UserIsNotThisKindOfMemberException {
        connection = MySQLConnector.getInstance().connect();

        /**TEAM DETAILS*/
        String statement = "SELECT * FROM teams WHERE TeamID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectIdentifier);
        ResultSet rs = preparedStatement.executeQuery();
        if(!rs.next()){
            return null;
        }
        String teamID = rs.getString("TeamID");
        String name = rs.getString("Name");
        TeamStatus teamStatus = TeamStatus.valueOf(rs.getString("TeamStatus"));
        boolean isClosed = rs.getBoolean("isClosed");
        double playersRate = rs.getDouble("PlayersFootballRate");
        boolean sysManClosed = rs.getBoolean("SystemManagerCloser");
        PersonalInfo personalInfo =null;
        try {
            personalInfo = new PersonalPagesDAL().select(rs.getInt("PersonalPage"),false);
        } catch (EmptyPersonalPageException e) {
        }
        ControlBudget controlBudget = new ControlBudgetDAL().select(rs.getString("ControlBudget"),false);

        /**MainTeamOwner**/
        TeamOwner owner = new TeamOwnersDAL().select(rs.getString("Owner"),false);

        /**FIELDS*/
        HashMap<Integer, IAsset> fields = new HashMap<>();
        statement = "SELECT * FROM fields WHERE teamID=?";
        preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectIdentifier);
        rs = preparedStatement.executeQuery();
        while (rs.next()){
            Field field = new FieldsDAL().select(rs.getInt("AssetID"), true);
            fields.put(field.getAssetID(),field);
        }

        /**PLAYERS*/
        HashMap<Integer, IAsset> players = new HashMap<>();
        statement = "SELECT * FROM players WHERE Team=?";
        preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectIdentifier);
        rs = preparedStatement.executeQuery();
        while (rs.next()){
            Player player = new PlayersDAL().select(rs.getString("UserName"),false);
            players.put(player.getAssetID(),player);
        }

        /**COACHES*/
        HashMap<CoachRole, IAsset> coaches = new HashMap<>();
        statement = "SELECT * FROM coaches WHERE Team=?";
        preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectIdentifier);
        rs = preparedStatement.executeQuery();
        while (rs.next()){
            Coach coach = new CoachesDAL().select(rs.getString("UserName"),false);
            coaches.put(coach.getRole(),coach);
        }

        /**TeamManagers*/
        HashMap<Integer, TeamManager> teamManagers = new HashMap<>();
        statement = "SELECT * FROM teammanagers WHERE Team=?";
        preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectIdentifier);
        rs = preparedStatement.executeQuery();
        while (rs.next()){
            TeamManager teamManager =  new TeamManagerDAL().select(rs.getString("UserName"),false);
            teamManagers.put(teamManager.getAssetID(),teamManager);
        }

        /**SECONDARY TEAM OWNERS*/
        LinkedList <TeamOwner> secondaryTeamOwner = new LinkedList<>();
        statement = "SELECT * FROM teamowners WHERE Team=?";
        preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectIdentifier);
        rs = preparedStatement.executeQuery();
        while (rs.next()){
            TeamOwner teamOwner = new TeamOwnersDAL().select(rs.getString("UserName"),false);
            secondaryTeamOwner.add(teamOwner);
        }

        /**UPCOMING GAMES*/
        LinkedList<Game> upcomingGames = new LinkedList<>();
        String string = "SELECT * FROM games WHERE HomeTeam = ? OR AwayTeam=?";
        PreparedStatement preparedStatement1 = connection.prepareStatement(string);
        preparedStatement1.setString(1,objectIdentifier);
        preparedStatement1.setString(2,objectIdentifier);
        ResultSet rs1 = preparedStatement1.executeQuery();
        while (rs1.next()){
            Game game = new GamesDAL().select(rs1.getString("ObjectID"),false);
            upcomingGames.add(game);
        }

        Team team = new Team(null,name,personalInfo,owner,teamStatus,0, UUID.fromString(teamID),controlBudget,isClosed,secondaryTeamOwner,players,fields,teamManagers,coaches,playersRate,upcomingGames,sysManClosed);

        /**Setters for bidirectional Association */
        team.getOwner().setTeam(team);
        for (TeamOwner mem : secondaryTeamOwner) {
            mem.setTeam(team);
        }
        for (Integer key: players.keySet()) {
            ((Player)players.get(key)).setMyTeam(team);
        }
        for (Integer key: fields.keySet()) {
            ((Field)fields.get(key)).setMyTeam(team);
        }
        for (CoachRole key: coaches.keySet()) {
            ((Coach)coaches.get(key)).setMyTeam(team);
        }
        for (Integer key: teamManagers.keySet()) {
            ((TeamManager)teamManagers.get(key)).setMyTeam(team);
            teamManagers.get(key).getTeamOwnerAssignedThis().setTeam(team);
        }
        MySQLConnector.getInstance().disconnect();
        return team;
    }

    @Override
    public boolean delete(String objectIdentifier) {
        return false;
    }

    public HashMap<UUID, Team> selectALl() throws NoConnectionException, SQLException, NoPermissionException, UserInformationException, UserIsNotThisKindOfMemberException {
        HashMap<UUID, Team> allTeams = new HashMap<>();
        connection = MySQLConnector.getInstance().connect();

        String statement ="SELECT TeamID FROM teams";
        PreparedStatement preparedStatement =connection.prepareStatement(statement);
        ResultSet rs = preparedStatement.executeQuery();
        LinkedList <String> teamsIDs = new LinkedList<>();
        while (rs.next()){
            teamsIDs.add(rs.getString("TeamID"));
        }
        MySQLConnector.getInstance().disconnect();
        for (String string : teamsIDs) {
            Team team = this.select(string,true);
            allTeams.put(team.getId(),team);
        }
        return allTeams;
    }

}
