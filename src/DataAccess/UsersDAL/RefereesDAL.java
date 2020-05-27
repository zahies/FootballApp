package DataAccess.UsersDAL;

import DataAccess.DAL;
import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.SeasonManagmentDAL.GamesDAL;
import DataAccess.UserInformationDAL.RefereeGamesDAL;
import Domain.SeasonManagment.Game;
import Domain.Users.Referee;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

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
    public Referee select(String objectIdentifier) throws SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException, NoPermissionException {
        return null;
    }

    @Override
    public boolean delete(String objectIdentifier) {
        return false;
    }
}
