package DataAccess.SeasonManagmentDAL;

import DataAccess.DAL;
import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import Domain.SeasonManagment.Season;
import Domain.SeasonManagment.Team;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;

public class SeasonDAL implements DAL<Season, String> {

    Connection connection = null;

    @Override
    public boolean insert(Season objectToInsert) throws SQLException, NoConnectionException, UserInformationException, UserIsNotThisKindOfMemberException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        connection = connect();

        String statement =" INSERT INTO seasons (seasonID, year, IsItTheBeginningOfSeason, scorePolicy, placingPolicy) VALUES(?,?,?,?,?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectToInsert.getObjectID().toString());
        preparedStatement.setInt(2,objectToInsert.getYear());
        preparedStatement.setBoolean(3,objectToInsert.isItTheBeginningOfSeason());
        preparedStatement.setString(4,objectToInsert.getScorePolicy().getId().toString());
        preparedStatement.setString(5,objectToInsert.getPlaceTeamsPolicy().toString());
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
        connection = connect();

        String statement = "UPDATE seasons SET Year=?, IsItTheBeginningOfSeason=?,ScorePolicy = ?, PlacingPolicy=? WHERE SeasonID=?";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setInt(1,objectToUpdate.getYear());
        preparedStatement.setBoolean(2,objectToUpdate.isItTheBeginningOfSeason());
        preparedStatement.setString(3,objectToUpdate.getScorePolicy().getId().toString());
        preparedStatement.setString(4, objectToUpdate.getPlaceTeamsPolicy().getId().toString());
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
    public Season select(String objectIdentifier) throws SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException, NoPermissionException {
        return null;
    }

    @Override
    public boolean delete(String objectIdentifier) {
        return false;
    }
}
