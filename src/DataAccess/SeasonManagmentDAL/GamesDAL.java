package DataAccess.SeasonManagmentDAL;

import DataAccess.DAL;
import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import Domain.SeasonManagment.Game;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GamesDAL implements DAL<Game, Integer> {
    Connection connection;

    @Override
    public boolean insert(Game objectToInsert) throws SQLException, NoConnectionException, UserInformationException, UserIsNotThisKindOfMemberException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {

        if (checkExist(objectToInsert.getObjectId(), "game", "gameID")) {
            throw new DuplicatedPrimaryKeyException();
        }
        connection = connect();
        String statement = "INSERT INTO game (gameID, homeTeam, homeScore, awayTeam, awayScore, date, mainReferee, secondaryReferee, season) VALUES (?,?,?,?,?,?,?,?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setInt(1, objectToInsert.getObjectId());
        preparedStatement.setInt(2, objectToInsert.getHome().getId());
        preparedStatement.setInt(3, objectToInsert.getScoreHome());
        preparedStatement.setInt(4, objectToInsert.getAway().getId());
        preparedStatement.setInt(5, objectToInsert.getScoreAway());
        preparedStatement.setDate(6, (Date) objectToInsert.getDateGame());
        preparedStatement.setString(7, objectToInsert.getMainReferee().getName());
        preparedStatement.setString(8, objectToInsert.getSeconderyReferee().getName());
        preparedStatement.setInt(9, objectToInsert.getSeason().getObjectID());
        preparedStatement.execute();
        connection.close();

        return true;
    }

    @Override
    public boolean update(Game objectToUpdate, Pair<String, Object> valToUpdate) throws SQLException, UserIsNotThisKindOfMemberException, UserInformationException, NoConnectionException, NoPermissionException {
        return false;
    }

    @Override
    public Game select(Integer objectIdentifier) throws SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException, NoPermissionException {
        return null;
    }

    @Override
    public boolean delete(Integer objectIdentifier) {
        return false;
    }
}
