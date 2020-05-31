package DataAccess.UserInformationDAL;

import DataAccess.DAL;
import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.MySQLConnector;
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
    /**
     * T - objectToInsert - key = pair (key = fan user name , value = pageID)
     * Value = T/F - alerts on
     * E - objectIdentifier - key = pair (key = fan user name , value = pageID)
     */


    @Override
    public boolean insert(Pair<Pair<String, Integer>, Boolean> objectToInsert) throws SQLException, NoConnectionException, UserInformationException, UserIsNotThisKindOfMemberException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        Connection connection = MySQLConnector.getInstance().connect();

        String statement = "INSERT INTO fan_following_pages (MemberUserName, PersonalPageID, alerts) VALUES (?,?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1, objectToInsert.getKey().getKey());
        preparedStatement.setInt(2, objectToInsert.getKey().getValue());
        preparedStatement.setBoolean(3, objectToInsert.getValue());
        preparedStatement.execute();

        return true;
    }

    @Override
    public boolean update(Pair<Pair<String, Integer>, Boolean> objectToUpdate) throws SQLException, UserIsNotThisKindOfMemberException, UserInformationException, NoConnectionException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        Connection connection = MySQLConnector.getInstance().connect();
        String statement = "";
        if(checkExist(objectToUpdate.getKey(),"fan_following_pages","MemberUserName","PersonalPageID")){
            statement = " UPDATE fan_following_pages SET Alerts = ? WHERE memberUserName = ? AND PersonalPageID =? ";
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setBoolean(1,objectToUpdate.getValue());
            preparedStatement.setString(2,objectToUpdate.getKey().getKey());
            preparedStatement.setInt(3,(objectToUpdate.getKey().getValue()));
            preparedStatement.executeUpdate();
        }else{
            this.insert(objectToUpdate);
        }

        return true;
    }

    @Override
    public Pair<Pair<String, Integer>, Boolean> select(Pair<String, Integer> objectIdentifier, boolean  bidirectionalAssociation) throws SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException, NoPermissionException {
        return null;
    }

    @Override
    public boolean delete(Pair<String, Integer> objectIdentifier) {
        return false;
    }


}
