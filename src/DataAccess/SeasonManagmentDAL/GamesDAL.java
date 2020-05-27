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

public class GamesDAL implements DAL<Game, String> {
    Connection connection;

    @Override
    public boolean insert(Game objectToInsert) throws SQLException, NoConnectionException, UserInformationException, UserIsNotThisKindOfMemberException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {

        connection = connect();
        String statement = "INSERT INTO games (ObjectID, homeTeam, HomeTeamScore, awayTeam, awayScore, date, mainReferee, MainRefType, secondaryReferee, SecRefType, season, logger) VALUES (?,?,?,?,?,?,?,?,?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1, objectToInsert.getObjectId().toString());
        preparedStatement.setString(2, objectToInsert.getHome().getId().toString());
        preparedStatement.setInt(3, objectToInsert.getScoreHome());
        preparedStatement.setString(4, objectToInsert.getAway().getId().toString());
        preparedStatement.setInt(5, objectToInsert.getScoreAway());
        preparedStatement.setDate(6, (Date) objectToInsert.getDateGame());
        preparedStatement.setString(7, objectToInsert.getMainReferee().getName());
        preparedStatement.setString(8, objectToInsert.getSeconderyReferee().getName());
        preparedStatement.setString(9, objectToInsert.getSeason().getObjectID().toString());
        preparedStatement.setString(10,objectToInsert.event_logger.getObjectID().toString());
        preparedStatement.execute();
        connection.close();

        return true;
    }

    @Override
    public boolean update(Game objectToUpdate) throws SQLException, UserIsNotThisKindOfMemberException, UserInformationException, NoConnectionException, NoPermissionException {
        connection = connect();
        String statement = "UPDATE games SET HomeTeam=?, HomeTeamScore=?,AwayTeam=?,AwayScore=?,Date=?,MainReferee=?,MainRefType=?,SecondaryReferee=?,SecRefType=?,Season=?,Logger=? WHERE ObjectID=?;";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(10, objectToUpdate.getObjectId().toString());
        preparedStatement.setString(1, objectToUpdate.getHome().getId().toString());
        preparedStatement.setInt(2, objectToUpdate.getScoreHome());
        preparedStatement.setString(3, objectToUpdate.getAway().getId().toString());
        preparedStatement.setInt(4, objectToUpdate.getScoreAway());
        preparedStatement.setDate(5, (Date) objectToUpdate.getDateGame());
        preparedStatement.setString(6, objectToUpdate.getMainReferee().getName());
        preparedStatement.setString(7, objectToUpdate.getSeconderyReferee().getName());
        preparedStatement.setString(8, objectToUpdate.getSeason().getObjectID().toString());
        preparedStatement.setString(9,objectToUpdate.event_logger.getObjectID().toString());
        int ans = preparedStatement.executeUpdate();
        connection.close();

        return ans==1;
    }

    @Override
    public Game select(String objectIdentifier) throws SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException, NoPermissionException {
        return null;
    }

    @Override
    public boolean delete(String objectIdentifier) {
        return false;
    }
}
