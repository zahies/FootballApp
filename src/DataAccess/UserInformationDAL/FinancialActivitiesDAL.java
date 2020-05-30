package DataAccess.UserInformationDAL;

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

public class FinancialActivitiesDAL implements DAL<Pair<Pair<String,String>,Integer>,Pair<String,String>> {

        /**
     * T - objectToInsert - key = pair (key = Info , value = commissioner user name)
     * Value = amount
     * E - objectIdentifier - key = pair (key = Info , value = commissioner user name)
     */


    @Override
    public boolean insert(Pair<Pair<String, String>, Integer> objectToInsert) throws SQLException, NoConnectionException, UserInformationException, UserIsNotThisKindOfMemberException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        Connection connection = MySQLConnector.getInstance().connect();
        String permissionStatement = "INSERT INTO association_financial_activities (Info,Commissioner,Amount) VALUES (?,?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(permissionStatement);
        preparedStatement.setString(1, objectToInsert.getKey().getKey());
        preparedStatement.setString(2, objectToInsert.getKey().getValue());
        preparedStatement.setInt(3, objectToInsert.getValue());
        preparedStatement.execute();
        connection.close();
        return true;
    }

    @Override
    public boolean update(Pair<Pair<String, String>, Integer> objectToUpdate) throws SQLException, UserIsNotThisKindOfMemberException, UserInformationException, NoConnectionException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        Connection connection = MySQLConnector.getInstance().connect();
        String statement = "";
        if(checkExist(objectToUpdate.getKey(),"association_financial_activities","Info","Commissioner")){
            statement = " UPDATE association_financial_activities SET Amount = ? WHERE Info = ? AND Commissioner =? ";
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1,objectToUpdate.getValue());
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
    public Pair<Pair<String, String>, Integer> select(Pair<String, String> objectIdentifier, boolean  bidirectionalAssociation) throws SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException, NoPermissionException {
        return null;
    }

    @Override
    public boolean delete(Pair<String, String> objectIdentifier) {
        return false;
    }
}
