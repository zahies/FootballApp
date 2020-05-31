package DataAccess.SeasonManagmentDAL;

import DataAccess.DAL;
import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.MySQLConnector;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LeagueSeasonsDAL implements DAL<Pair<Pair<String,Integer>,String>,Pair<String,Integer>> {

    /**
     * T - objectToInsert - key = pair (key = League , value = year)
     *                      Value = season ID
     * E - objectIdentifier - key = pair (key = League , value = year)
     */

    @Override
    public boolean insert(Pair<Pair<String, Integer>, String> objectToInsert) throws SQLException, NoConnectionException, UserInformationException, UserIsNotThisKindOfMemberException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        Connection connection = MySQLConnector.getInstance().connect();

        String statement ="INSERT INTO leagues_seasons (League, Season, Year) VALUES (?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectToInsert.getKey().getKey());
        preparedStatement.setString(2,objectToInsert.getValue());
        preparedStatement.setInt(3, objectToInsert.getKey().getValue());
        preparedStatement.execute();

        return true;
    }

    @Override
    public boolean update(Pair<Pair<String, Integer>, String> objectToUpdate) throws SQLException, UserIsNotThisKindOfMemberException, UserInformationException, NoConnectionException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        Connection connection = MySQLConnector.getInstance().connect();

        String statement = "";
        int ans=0;
        if(checkExist(objectToUpdate.getKey(),"leagues_seasons","League","Year")){
            statement = " UPDATE leagues_seasons SET Season = ? WHERE League = ? AND Year =? ";
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setString(1,objectToUpdate.getValue());
            preparedStatement.setString(2,objectToUpdate.getKey().getKey());
            preparedStatement.setInt(3,(objectToUpdate.getKey().getValue()));
            ans=preparedStatement.executeUpdate();
        }else{
            this.insert(objectToUpdate);
        }


        return ans==1;
    }

    @Override
    public Pair<Pair<String, Integer>, String> select(Pair<String, Integer> objectIdentifier, boolean  bidirectionalAssociation) throws SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException, NoPermissionException {
        return null;
    }

    @Override
    public boolean delete(Pair<String, Integer> objectIdentifier) {
        return false;
    }
}
