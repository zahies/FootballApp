package DataAccess.SeasonManagmentDAL;

import DataAccess.DAL;
import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import Domain.SeasonManagment.ComplaintForm;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ComplaintFormsDAL implements DAL<ComplaintForm, Integer> {
    Connection connection = null;

    @Override
    public boolean insert(ComplaintForm objectToInsert) throws SQLException, NoConnectionException, UserInformationException, UserIsNotThisKindOfMemberException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        connection = connect();
        if (connection == null) {
            throw new NoConnectionException();
        }

        String statement = "INSERT INTO complaint (objectID, fanSubmitting, complaint, response) VALUES(?,?,?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setInt(1, objectToInsert.getObjectID());
        preparedStatement.setString(2, objectToInsert.getFanSubmitingForm().getName());
        preparedStatement.setString(3, objectToInsert.getComplaint());
        preparedStatement.setString(4, objectToInsert.getResponse());
        preparedStatement.execute();
        connection.close();
        return true;

    }

    @Override
    public boolean update(ComplaintForm objectToUpdate, Pair<String, Object> valToUpdate) throws SQLException, UserIsNotThisKindOfMemberException, UserInformationException, NoConnectionException, NoPermissionException {
        return false;
    }

    @Override
    public ComplaintForm select(Integer objectIdentifier) throws SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException, NoPermissionException {
        return null;
    }

    @Override
    public boolean delete(Integer objectIdentifier) {
        return false;
    }
}
