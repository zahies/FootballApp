package DataAccess.UserInformationDAL;

import DataAccess.DAL;
import DataAccess.Exceptions.NoConnectionException;
import Domain.Users.Member;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TeamManagerPermissionsDAL implements DAL<Pair<Pair<String, Member>, Boolean>, Pair<String, Member>> {

    Connection connection = null;

    @Override
    public boolean insert(Pair<Pair<String, Member>, Boolean> objectToInsert) throws SQLException, NoConnectionException, UserInformationException, UserIsNotThisKindOfMemberException, NoPermissionException {
        this.select(objectToInsert.getKey());
        connection = connect();
        if (connection == null) {
            throw new NoConnectionException();
        }
        String permissionStatement = "INSERT INTO permissions (PermissionDescription, TeamManager,isPermitted) VALUES (?,?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(permissionStatement);
        preparedStatement.setString(1, objectToInsert.getKey().getKey());
        preparedStatement.setString(2, objectToInsert.getKey().getValue().getName());
        preparedStatement.setBoolean(3, objectToInsert.getValue());
        preparedStatement.execute();
        connection.close();
        return true;
    }

    @Override
    public boolean update(Pair<Pair<String, Member>, Boolean> objectToUpdate, Pair<String, Object> valToUpdate) throws SQLException, UserIsNotThisKindOfMemberException, UserInformationException, NoConnectionException, NoPermissionException {
        select(objectToUpdate.getKey());
        connection = connect();
        if (connection == null) {
            throw new NoConnectionException();
        }

        String statement = "UPDATE permissions SET isPermitted =  ? " +
                "WHERE PermissionDescription = ? AND TeamManager =?; ";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);

        preparedStatement.setBoolean(1, (((Boolean) ((Pair) valToUpdate.getValue()).getValue())));
        preparedStatement.setString(2, (((String) ((Pair) valToUpdate.getValue()).getKey())));
        preparedStatement.setString(3, objectToUpdate.getKey().getValue().getName());
        preparedStatement.executeUpdate();
        connection.close();
        return true;
    }

    @Override
    public Pair<Pair<String, Member>, Boolean> select(Pair<String, Member> objectIdentifier) throws SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException, NoPermissionException {
        connection = connect();
        if (connection == null) {
            throw new NoConnectionException();
        }

        String statement = "SELECT isPermitted FROM permissions WHERE TeamManager = ? AND PermissionDescription =?";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1, objectIdentifier.getValue().getName());
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
    public boolean delete(Pair<String, Member> objectIdentifier) {
        return false;
    }
}
