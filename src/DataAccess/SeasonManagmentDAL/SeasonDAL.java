package DataAccess.SeasonManagmentDAL;

import DataAccess.DAL;
import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.MySQLConnector;
import Domain.SeasonManagment.IPlaceTeamsPolicy;
import Domain.SeasonManagment.IScorePolicy;
import Domain.SeasonManagment.Season;
import Domain.SeasonManagment.Team;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import javafx.util.Pair;

import java.sql.*;
import java.util.LinkedList;

public class SeasonDAL implements DAL<Season, String> {


    @Override
    public boolean insert(Season objectToInsert) throws SQLException, NoConnectionException, UserInformationException, UserIsNotThisKindOfMemberException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        Connection connection = MySQLConnector.getInstance().connect();
        String statement =" INSERT INTO seasons (seasonID, year, IsItTheBeginningOfSeason, scorePolicy, placingPolicy) VALUES(?,?,?,?,?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectToInsert.getObjectID().toString());
        preparedStatement.setInt(2,objectToInsert.getYear());
        preparedStatement.setBoolean(3,objectToInsert.isItTheBeginningOfSeason());
        if (objectToInsert.getScorePolicy() == null) {
            preparedStatement.setNull(4, Types.VARCHAR);
        } else {
            preparedStatement.setString(4, objectToInsert.getScorePolicy().getId().toString());
        }
        if (objectToInsert.getPlaceTeamsPolicy() == null) {
            preparedStatement.setNull(5, Types.VARCHAR);
        } else {
            preparedStatement.setString(5, objectToInsert.getPlaceTeamsPolicy().getId().toString());
        }
        preparedStatement.execute();
        LinkedList <Pair<Integer, Team>> teams = objectToInsert.getScore_teams();
        for (Pair <Integer, Team> team : teams) {
            new SeasonTeamsDAL().insert(new Pair<>(new Pair<>(objectToInsert.getObjectID().toString(),team.getValue().getId().toString()),team.getKey()));
        }
        connection.close();
        return true;
    }

    @Override
    public boolean update(Season objectToUpdate) throws SQLException, UserIsNotThisKindOfMemberException, UserInformationException, NoConnectionException, NoPermissionException {
        Connection connection = MySQLConnector.getInstance().connect();

        String statement = "UPDATE seasons SET Year=?, IsItTheBeginningOfSeason=?,ScorePolicy = ?, PlacingPolicy=? WHERE SeasonID=?";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setInt(1,objectToUpdate.getYear());
        preparedStatement.setBoolean(2,objectToUpdate.isItTheBeginningOfSeason());
        if (objectToUpdate.getScorePolicy() == null) {
            preparedStatement.setNull(3, Types.VARCHAR);
        } else {
            preparedStatement.setString(3, objectToUpdate.getScorePolicy().getId().toString());
        }
        if (objectToUpdate.getPlaceTeamsPolicy() == null) {
            preparedStatement.setNull(4, Types.VARCHAR);
        } else {
            preparedStatement.setString(4, objectToUpdate.getPlaceTeamsPolicy().getId().toString());
        }
        preparedStatement.setString(5,objectToUpdate.getObjectID().toString());
        LinkedList <Pair<Integer, Team>> teams = objectToUpdate.getScore_teams();
        for (Pair <Integer, Team> team : teams) {
            new SeasonTeamsDAL().update(new Pair<>(new Pair<>(objectToUpdate.getObjectID().toString(),team.getValue().getId().toString()),team.getKey()));
        }
        int ans = preparedStatement.executeUpdate();
        connection.close();

        return ans==1;
    }

    @Override
    public Season select(String objectIdentifier, boolean  bidirectionalAssociation) throws SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException, NoPermissionException {
        Connection connection = MySQLConnector.getInstance().connect();

        /***SEASON DETAILS*/
        String statement ="SELECT * FROM seasons WHERE SeasonID=?";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectIdentifier);
        ResultSet rs = preparedStatement.executeQuery();
        rs.next();
        int year = rs.getInt("Year");
        boolean isItBeginning = rs.getBoolean("IsItBeginningOfSeason");
        IScorePolicy scorePolicy = new ScorePoliciesDAL().select(rs.getString("ScorePolicy"),false);
        IPlaceTeamsPolicy placeTeamsPolicy = new PlaceTeamsPoliciesDAL().select(rs.getString("PlacingPolicy"),false);
        return null;
    }

    @Override
    public boolean delete(String objectIdentifier) {
        return false;
    }
}
