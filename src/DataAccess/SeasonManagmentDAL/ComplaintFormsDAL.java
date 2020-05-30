package DataAccess.SeasonManagmentDAL;

import DataAccess.DAL;
import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.MySQLConnector;
import Domain.SeasonManagment.ComplaintForm;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComplaintFormsDAL implements DAL<ComplaintForm, String> {


    @Override
    public boolean insert(ComplaintForm objectToInsert) throws SQLException, NoConnectionException, UserInformationException, UserIsNotThisKindOfMemberException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        Connection connection = MySQLConnector.getInstance().connect();

        String statement = "INSERT INTO complaint_forms (objectID, FanSubmittingForm, complaint, response) VALUES(?,?,?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1, objectToInsert.getObjectID().toString());
        preparedStatement.setString(2, objectToInsert.getFanSubmitingForm().getName());
        preparedStatement.setString(3, objectToInsert.getComplaint());
        preparedStatement.setString(4, objectToInsert.getResponse());
        preparedStatement.execute();
        connection.close();
        return true;

    }

    @Override
    public boolean update(ComplaintForm objectToUpdate) throws SQLException, UserIsNotThisKindOfMemberException, UserInformationException, NoConnectionException, NoPermissionException {
        Connection connection = MySQLConnector.getInstance().connect();
        String statement="UPDATE complaint_forms SET FanSubmittingForm=?, Complaint=?,Response=? WHERE ObjectID=?;";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectToUpdate.getFanSubmitingForm().getName());
        preparedStatement.setString(2,objectToUpdate.getComplaint());
        preparedStatement.setString(3,objectToUpdate.getResponse());
        preparedStatement.setString(4,objectToUpdate.getObjectID().toString());
        int ans = preparedStatement.executeUpdate();
        connection.close();
        return ans==1;
    }

    @Override
    public ComplaintForm select(String objectIdentifier, boolean  bidirectionalAssociation) throws SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException, NoPermissionException {
        return null;
    }

    @Override
    public boolean delete(String objectIdentifier) {
        return false;
    }

    public List<ComplaintForm> selectAll(){
        List<ComplaintForm> allComplaints = new ArrayList<>();
        return allComplaints;
    }
}
