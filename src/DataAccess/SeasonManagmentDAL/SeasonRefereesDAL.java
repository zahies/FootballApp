package DataAccess.SeasonManagmentDAL;

import DataAccess.DAL;
import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.MySQLConnector;
import FootballExceptions.EmptyPersonalPageException;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SeasonRefereesDAL implements DAL<Pair<String,String>,Pair<String,String>> {
    /**
     * T - objectToInsert - key = pair (key = season , value = referee)
     * E - objectIdentifier - key = pair (key = season , value = referee)
     */


    @Override
    public boolean insert(Pair<String, String> objectToInsert) throws SQLException, NoConnectionException, UserInformationException, UserIsNotThisKindOfMemberException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        Connection connection = MySQLConnector.getInstance().connect();

        String statement = "INSERT INTO season_referees (Season, Referee) VALUES (?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectToInsert.getKey());
        preparedStatement.setString(2,objectToInsert.getValue());
        preparedStatement.execute();

        return true;
    }

    @Override
    public boolean update(Pair<String, String> objectToUpdate) throws SQLException, UserIsNotThisKindOfMemberException, UserInformationException, NoConnectionException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        Connection connection = MySQLConnector.getInstance().connect();
        if(checkExist(objectToUpdate,"season_referees","Season","Referee")){
        }else{
            this.insert(objectToUpdate);
        }

        return true;
    }

    @Override
    public Pair<String, String> select(Pair<String, String> objectIdentifier, boolean bidirectionalAssociation) throws SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException, NoPermissionException, EmptyPersonalPageException {
        return null;
    }

    @Override
    public boolean delete(Pair<String, String> objectIdentifier) {
        return false;
    }
}
