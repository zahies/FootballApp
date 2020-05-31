package DataAccess.SeasonManagmentDAL;

import DataAccess.DAL;
import DataAccess.EventsDAL.EventLoggersDAL;
import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.MySQLConnector;
import DataAccess.UsersDAL.RefereesDAL;
import Domain.Events.Event_Logger;
import Domain.SeasonManagment.Game;
import Domain.SeasonManagment.Team;
import Domain.Users.Player;
import Domain.Users.Referee;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import javafx.util.Pair;

import java.sql.*;
import java.util.LinkedList;
import java.util.Observer;
import java.util.UUID;

public class GamesDAL implements DAL<Game, String> {

    @Override
    public boolean insert(Game objectToInsert) throws SQLException, NoConnectionException, UserInformationException, UserIsNotThisKindOfMemberException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {

        Connection connection = MySQLConnector.getInstance().connect();
        String statement = "INSERT INTO games (ObjectID, homeTeam, HomeTeamScore, awayTeam, AwayTeamScore, date, mainReferee, secondaryReferee, season, logger) VALUES (?,?,?,?,?,?,?,?,?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1, objectToInsert.getObjectId().toString());
        if (objectToInsert.getHome() == null) {
            preparedStatement.setNull(2, Types.VARCHAR);
        } else {
            preparedStatement.setString(2, objectToInsert.getHome().getId().toString());
        }
        preparedStatement.setInt(3, objectToInsert.getScoreHome());
        if (objectToInsert.getAway() == null) {
            preparedStatement.setNull(4, Types.VARCHAR);
        } else {
            preparedStatement.setString(4, objectToInsert.getAway().getId().toString());
        }
        preparedStatement.setInt(5, objectToInsert.getScoreAway());
        java.util.Date date = objectToInsert.getDateGame();
        if(date == null){
            preparedStatement.setNull(6,Types.DATE);
        }else {
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            preparedStatement.setDate(6, sqlDate);
        }
        if (objectToInsert.getMainReferee() == null) {
            preparedStatement.setNull(7, Types.VARCHAR);
        } else {
            preparedStatement.setString(7, objectToInsert.getMainReferee().getName());
        }
        if (objectToInsert.getSeconderyReferee() == null) {
            preparedStatement.setNull(8, Types.VARCHAR);
        } else {
            preparedStatement.setString(8, objectToInsert.getSeconderyReferee().getName());
        }
        if (objectToInsert.getSeason() == null) {
            preparedStatement.setNull(9, Types.VARCHAR);
        } else {
            preparedStatement.setString(9, objectToInsert.getSeason().getObjectID().toString());
        }
        preparedStatement.setString(10,objectToInsert.event_logger.getObjectID().toString());
        preparedStatement.execute();
        connection.close();

        return true;
    }

    @Override
    public boolean update(Game objectToUpdate) throws SQLException, UserIsNotThisKindOfMemberException, UserInformationException, NoConnectionException, NoPermissionException {
        Connection connection = MySQLConnector.getInstance().connect();
        String statement = "UPDATE games SET HomeTeam=?, HomeTeamScore=?,AwayTeam=?,AwayTeamScore=?,Date=?,MainReferee=?,MainRefType=?,SecondaryReferee=?,SecRefType=?,Season=?,Logger=? WHERE ObjectID=?;";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(10, objectToUpdate.getObjectId().toString());
        if (objectToUpdate.getHome() == null) {
            preparedStatement.setNull(1, Types.VARCHAR);
        } else {
            preparedStatement.setString(1, objectToUpdate.getHome().getId().toString());
        }
        preparedStatement.setInt(2, objectToUpdate.getScoreHome());
        if (objectToUpdate.getAway() == null) {
            preparedStatement.setNull(3, Types.VARCHAR);
        } else {
            preparedStatement.setString(3, objectToUpdate.getAway().getId().toString());
        }
        preparedStatement.setInt(4, objectToUpdate.getScoreAway());
        java.util.Date date = objectToUpdate.getDateGame();
        if(date == null){
            preparedStatement.setNull(5,Types.DATE);
        }else {
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            preparedStatement.setDate(5, sqlDate);
        }
        if (objectToUpdate.getMainReferee() == null) {
            preparedStatement.setNull(6, Types.VARCHAR);
        } else {
            preparedStatement.setString(6, objectToUpdate.getMainReferee().getName());
        }
        if (objectToUpdate.getSeconderyReferee() == null) {
            preparedStatement.setNull(7, Types.VARCHAR);
        } else {
            preparedStatement.setString(7, objectToUpdate.getSeconderyReferee().getName());
        }
        if (objectToUpdate.getSeason() == null) {
            preparedStatement.setNull(8, Types.VARCHAR);
        } else {
            preparedStatement.setString(8, objectToUpdate.getSeason().getObjectID().toString());
        }
        preparedStatement.setString(9,objectToUpdate.event_logger.getObjectID().toString());
        int ans = preparedStatement.executeUpdate();
        connection.close();

        return ans==1;
    }

    @Override
    public Game select(String objectIdentifier, boolean  bidirectionalAssociation) throws SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException, NoPermissionException {
        Connection connection = MySQLConnector.getInstance().connect();

        /**GAMES DETAILS*/
        String statement ="SELECT * FROM games WHERE ObjectID=?";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectIdentifier);
        ResultSet rs = preparedStatement.executeQuery();
        if(!rs.next()){
            return null;
        }
        Team homeTeam = null;
        Team awayTeam = null;
        if(bidirectionalAssociation){
            homeTeam = new TeamsDAL().select(rs.getString("HomeTeam"),false);
            awayTeam = new TeamsDAL().select(rs.getString("AwayTeam"),false);
        }
        int homeScore = rs.getInt("HomeTeamScore");
        int awayScore = rs.getInt("AwayTeamScore");
        java.util.Date date = rs.getDate("date");

        Referee mainRef = null;
        Referee secRef = null;
        if(bidirectionalAssociation) {
            mainRef = new RefereesDAL().select(rs.getString("MainReferee"), false);
            secRef = new RefereesDAL().select(rs.getString("SecondaryReferee"), false);
        }

        LinkedList<Observer> referees = new LinkedList<>();
        referees.add(mainRef);
        referees.add(secRef);
        Event_Logger event_logger = new EventLoggersDAL().select(rs.getString("Logger"),false);

        return new Game(UUID.fromString(objectIdentifier),awayTeam,homeTeam,date,mainRef,secRef,homeScore,awayScore,null,referees,event_logger);


    }

    @Override
    public boolean delete(String objectIdentifier) {
        return false;
    }
}
