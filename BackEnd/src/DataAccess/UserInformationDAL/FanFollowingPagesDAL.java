package DataAccess.UserInformationDAL;

import DataAccess.DAL;
import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import Domain.Users.PersonalInfo;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

public class FanFollowingPagesDAL implements DAL<Pair<Pair<String, Integer>, Boolean>, Pair<String, Integer>> {
    Connection connection = null;

    @Override
    public boolean insert(Pair<Pair<String, Integer>, Boolean> objectToInsert) throws SQLException, NoConnectionException, UserInformationException, UserIsNotThisKindOfMemberException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        connection = connect();
        if (connection == null) {
            throw new NoConnectionException();
        }
        String statement = "INSERT INTO fan_following_pages (fan, page, alerts) VALUES (?,?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1, objectToInsert.getKey().getKey());
        preparedStatement.setInt(2, objectToInsert.getKey().getValue());
        preparedStatement.setBoolean(3, objectToInsert.getValue());
        preparedStatement.execute();
        connection.close();
        return true;
    }

    @Override
    public boolean update(Pair<Pair<String, Integer>, Boolean> objectToUpdate, Pair<String, Object> valToUpdate) throws SQLException, UserIsNotThisKindOfMemberException, UserInformationException, NoConnectionException, NoPermissionException {
        return false;
    }

    @Override
    public Pair<Pair<String, Integer>, Boolean> select(Pair<String, Integer> objectIdentifier) throws SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException, NoPermissionException {
        return null;
    }

    @Override
    public boolean delete(Pair<String, Integer> objectIdentifier) {
        return false;
    }
}
