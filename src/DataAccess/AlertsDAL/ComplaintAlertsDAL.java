package DataAccess.AlertsDAL;

import DataAccess.DAL;
import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.MySQLConnector;
import Domain.Alerts.ComplaintAlert;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class ComplaintAlertsDAL implements DAL<ComplaintAlert,String> {

    @Override
    public boolean insert(ComplaintAlert objectToInsert) throws SQLException, NoConnectionException, UserInformationException, UserIsNotThisKindOfMemberException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        Connection connection = MySQLConnector.getInstance().connect();

        String statement = "INSERT INTO member_alerts_complaints (objectID, complaintForm) VALUES (?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectToInsert.getObjectID().toString());
        if (objectToInsert.getComplaintResponse() == null) {
            preparedStatement.setNull(2, Types.VARCHAR);
        } else {
            preparedStatement.setString(2, objectToInsert.getComplaintResponse().getObjectID().toString());
        }
        preparedStatement.execute();
        connection.close();
        return true;
    }

    @Override
    public boolean update(ComplaintAlert objectToUpdate) throws SQLException, UserIsNotThisKindOfMemberException, UserInformationException, NoConnectionException, NoPermissionException {
        return false;
    }

    @Override
    public ComplaintAlert select(String objectIdentifier, boolean  bidirectionalAssociation) throws SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException, NoPermissionException {
        return null;
    }

    @Override
    public boolean delete(String objectIdentifier) {
        return false;
    }
}
