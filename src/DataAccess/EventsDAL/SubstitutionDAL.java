package DataAccess.EventsDAL;

import DataAccess.DAL;
import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import Domain.Events.Substitution;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SubstitutionDAL implements DAL<Substitution,String> {
    Connection connection = null;

    @Override
    public boolean insert(Substitution objectToInsert) throws SQLException, NoConnectionException, UserInformationException, UserIsNotThisKindOfMemberException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        connection = connect();

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
        connection = connect();

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
    public Substitution select(String objectIdentifier) throws SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException, NoPermissionException {
        return null;
    }

    @Override
    public boolean delete(String objectIdentifier) {
        return false;
    }
}
