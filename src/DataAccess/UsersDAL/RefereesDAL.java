package DataAccess.UsersDAL;

import DataAccess.AlertsDAL.MemberAlertsDAL;
import DataAccess.DAL;
import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.SeasonManagmentDAL.GamesDAL;
import DataAccess.UserInformationDAL.RefereeGamesDAL;
import Domain.Alerts.IAlert;
import Domain.SeasonManagment.Game;
import Domain.Users.Referee;
import Domain.Users.RefereeType;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class RefereesDAL implements DAL<Referee,String> {

    Connection connection = null;

    @Override
    public boolean insert(Referee objectToInsert) throws SQLException, NoConnectionException, UserInformationException, UserIsNotThisKindOfMemberException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        new MembersDAL().insert(objectToInsert);
        connection = connect();

        String statement ="INSERT INTO referees (userName, type) VALUES (?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectToInsert.getName());
        preparedStatement.setString(2,objectToInsert.getType().toString());
        preparedStatement.execute();

        List <Game> games = objectToInsert.getGames();
        for (Game game: games) {
            new RefereeGamesDAL().insert(new Pair<>(objectToInsert.getName(),game.getObjectId().toString()));
        }
        connection.close();
        return true;

    }

    @Override
    public boolean update(Referee objectToUpdate) throws SQLException, UserIsNotThisKindOfMemberException, UserInformationException, NoConnectionException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        new MembersDAL().update(objectToUpdate);
        connection =connect();

        String statement ="UPDATE referees SET Type=? WHERE UserName =?;";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectToUpdate.getType().toString());
        preparedStatement.setString(2,objectToUpdate.getName());
        int ans = preparedStatement.executeUpdate();
        List <Game> games = objectToUpdate.getGames();
        for (Game game: games) {
            new RefereeGamesDAL().update(new Pair<>(objectToUpdate.getName(),game.getObjectId().toString()));
        }
        connection.close();
        return ans ==1;

    }

    @Override
    public Referee select(String objectIdentifier, boolean  bidirectionalAssociation) throws SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException, NoPermissionException {
        connection = connect();

        connection = connect();

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

        statement = "SELECT alertObjectID FROM member_alerts WHERE memberUserName = ? ;";
        preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectIdentifier);
        rs = preparedStatement.executeQuery();
        Queue<IAlert> memberAlerts = new LinkedList<>();
        while (rs.next()){
            memberAlerts.add(new MemberAlertsDAL().select(new Pair<String, String>(objectIdentifier,rs.getString(1)),true).getKey().getValue());
        }

        /**REFEREE DETAILS*/
        statement = "SELECT Type FROM referees WHERE UserName=?";
        preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectIdentifier);
        rs = preparedStatement.executeQuery();
        if(!rs.next()){
            throw new UserIsNotThisKindOfMemberException();
        }
        RefereeType type = RefereeType.valueOf(rs.getString("Type"));

        /**GAMES*/
        statement = "SELECT * FROM referee_games WHERE Referee=?";
        preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectIdentifier);
        rs = preparedStatement.executeQuery();
        List<Game> games = new ArrayList<>();
        while (rs.next()){
            Game game = new GamesDAL().select(rs.getString("Game"),false);
            games.add(game);

        }
        Referee referee =  new Referee(objectIdentifier,password,realName,memberAlerts,isActive,AlertsViaMail,mail,type,games);

        /***BIDIRECTIONAL ASSOCIATION SETTERS*/
        for (Game game : games) {
            if(type == RefereeType.Main){
                game.setMainReferee(referee);
            }else if(type == RefereeType.Secondary){
                game.setSeconderyReferee(referee);
            }
        }

        return referee;
    }

    @Override
    public boolean delete(String objectIdentifier) {
        return false;
    }
}
