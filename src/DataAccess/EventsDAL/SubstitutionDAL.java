package DataAccess.EventsDAL;

import DataAccess.DAL;
import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.MySQLConnector;
import DataAccess.UsersDAL.PlayersDAL;
import Domain.Events.Substitution;
import Domain.Users.Player;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SubstitutionDAL implements DAL<Substitution,String> {


    @Override
    public boolean insert(Substitution objectToInsert) throws SQLException, NoConnectionException, UserInformationException, UserIsNotThisKindOfMemberException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        Connection connection = MySQLConnector.getInstance().connect();

        String statement = "INSERT INTO events_substitutions (objectID, playerIn, playerOut) VALUES (?,?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectToInsert.getObjectID().toString());
        preparedStatement.setString(2,objectToInsert.getGoesIn().getName());
        preparedStatement.setString(3,objectToInsert.getGoesOut().getName());
        preparedStatement.execute();
        connection.close();
        return true;
    }

    @Override
    public boolean update(Substitution objectToUpdate) throws SQLException, UserIsNotThisKindOfMemberException, UserInformationException, NoConnectionException, NoPermissionException {
        Connection connection = MySQLConnector.getInstance().connect();

        String statement = "UPDATE events_substitutions SET PlayerIN =?, PlayerOut=? WHERE ObjectID=?";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectToUpdate.getGoesIn().getName());
        preparedStatement.setString(2,objectToUpdate.getGoesOut().getName());
        preparedStatement.setString(3,objectToUpdate.getObjectID().toString());
        preparedStatement.execute();
        connection.close();
        return true;
    }

    @Override
    public Substitution select(String objectIdentifier, boolean  bidirectionalAssociation) throws SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException, NoPermissionException {
        Connection connection = MySQLConnector.getInstance().connect();

        String statement ="SELECT * FROM events WHERE ObjectID=?";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectIdentifier);
        ResultSet rs = preparedStatement.executeQuery();
        String type= rs.getString("Type");
        double eventMin = rs.getDouble("EventMinute");
        Player playerIn= new PlayersDAL().select(rs.getString("PlayerIn"),false);
        Player playerOut= new PlayersDAL().select(rs.getString("PlayerOut"),false);

        return new Substitution(eventMin,null, UUID.fromString(objectIdentifier),playerOut,playerIn);

    }

    @Override
    public boolean delete(String objectIdentifier) {
        return false;
    }
}
