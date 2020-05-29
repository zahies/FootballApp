package DataAccess.UserInformationDAL;

import DataAccess.DAL;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import Domain.Users.Member;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TeamManagerPermissionsDAL implements DAL<Pair<Pair<String, String>, Boolean>, Pair<String, String>> {

    /**
     * T - objectToInsert - key = pair (key = Permissions , value = team manager user name)
     * Value = T/F - permission on/off
     * E - objectIdentifier - key = pair (key = Permissions , value = team manager user name)
     */

    Connection connection = null;

    @Override
    public boolean insert(Pair<Pair<String, String>, Boolean> objectToInsert) throws SQLException, NoConnectionException, UserInformationException, UserIsNotThisKindOfMemberException, NoPermissionException {
        connection = connect();
        String permissionStatement = "INSERT INTO permissions (Description, TeamManager,isPermitted) VALUES (?,?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(permissionStatement);
        preparedStatement.setString(1, objectToInsert.getKey().getKey());
        preparedStatement.setString(2, objectToInsert.getKey().getValue());
        preparedStatement.setBoolean(3, objectToInsert.getValue());
        preparedStatement.execute();
        connection.close();
        return true;
    }

    @Override
    public boolean update(Pair<Pair<String, String>, Boolean> objectToUpdate) throws SQLException, UserIsNotThisKindOfMemberException, UserInformationException, NoConnectionException, NoPermissionException, mightBeSQLInjectionException {
        connection = connect();
        String statement = "";
        if(checkExist(objectToUpdate.getKey(),"permissions","TeamManager","Description")){
            statement = " UPDATE permissions SET isPermitted = ? WHERE TeamManager = ? AND Description =? ";
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setBoolean(1,objectToUpdate.getValue());
            preparedStatement.setString(2,objectToUpdate.getKey().getKey());
            preparedStatement.setString(3,(objectToUpdate.getKey().getValue()));
            preparedStatement.executeUpdate();
        }else{
            this.insert(objectToUpdate);
        }
        connection.close();
        return true;
    }

    @Override
    public Pair<Pair<String, String>, Boolean> select(Pair<String, String> objectIdentifier, boolean  bidirectionalAssociation) throws SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException, NoPermissionException {
        connection = connect();

        String statement = "SELECT isPermitted FROM permissions WHERE TeamManager = ? AND Description=?";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1, objectIdentifier.getValue());
        preparedStatement.setString(2, objectIdentifier.getKey());
        ResultSet rs = preparedStatement.executeQuery();
        connection.close();
        if (!rs.next()) {
            throw new NoPermissionException("No such Permission for this Team Manager");
        }

        boolean bool = rs.getBoolean(1);
        return new Pair<>(objectIdentifier, bool);

    }

    @Override
    public boolean delete(Pair<String, String> objectIdentifier) {
        return false;
    }
}
