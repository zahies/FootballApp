package DataAccess.AlertsDAL;

import DataAccess.DAL;
import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.MySQLConnector;
import DataAccess.SeasonManagmentDAL.GamesDAL;
import Domain.Alerts.ChangedGameAlert;
import Domain.Users.Player;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class ChangedGameAlertsDAL implements DAL<ChangedGameAlert,String> {


    @Override
    public boolean insert(ChangedGameAlert objectToInsert) throws SQLException, NoConnectionException, UserInformationException, UserIsNotThisKindOfMemberException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        Connection connection = MySQLConnector.getInstance().connect();

        String statement = "INSERT INTO member_alerts_changed_game (ObjectID, game, date) VALUES (?,?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectToInsert.getObjectID().toString());
        if (objectToInsert.getGame() == null) {
            preparedStatement.setNull(2, Types.VARCHAR);
        } else {
            preparedStatement.setString(2, objectToInsert.getGame().getObjectId().toString());
        }
        java.util.Date date = objectToInsert.getMatchDate();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        preparedStatement.setDate(3, sqlDate);
        preparedStatement.execute();

        return true;
    }

    @Override
    public boolean update(ChangedGameAlert objectToUpdate) throws SQLException, UserIsNotThisKindOfMemberException, UserInformationException, NoConnectionException, NoPermissionException {
        return false;
    }

    @Override
    public ChangedGameAlert select(String objectIdentifier, boolean  bidirectionalAssociation) throws SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException, NoPermissionException {
        return null;
    }

    @Override
    public boolean delete(String objectIdentifier) {
        return false;
    }
}
