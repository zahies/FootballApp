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
import java.sql.ResultSet;
import java.sql.SQLException;

public class BudgetActivitiesDAL implements DAL<Pair<Pair<String,String>,Integer>,Pair<String,String>> {
    /**
     * T - objectToInsert - key = pair (key = budgetActivity , value = budgetID)
     *                      Value = Amount
     * E - objectIdentifier - key = pair (key = budgetActivity , value = budgetID)
     */


    @Override
    public boolean insert(Pair<Pair<String, String>, Integer> objectToInsert) throws SQLException, NoConnectionException, UserInformationException, UserIsNotThisKindOfMemberException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        Connection connection = MySQLConnector.getInstance().connect();

        String statement = "INSERT INTO budget_finance_activity (BudgetActivity, Budget, Amount) VALUES (?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectToInsert.getKey().getKey());
        preparedStatement.setString(2,objectToInsert.getKey().getValue());
        preparedStatement.setInt(3,objectToInsert.getValue());
        preparedStatement.execute();


        return true;
    }

    @Override
    public boolean update(Pair<Pair<String, String>, Integer> objectToUpdate) throws SQLException, UserIsNotThisKindOfMemberException, UserInformationException, NoConnectionException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        Connection connection = MySQLConnector.getInstance().connect();

        String statement = "UPDATE budget_finance_activity SET Amount =? WHERE BudgetActivity=? and Budget=?";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(3,objectToUpdate.getKey().getKey());
        preparedStatement.setString(2,objectToUpdate.getKey().getValue());
        preparedStatement.setInt(1,objectToUpdate.getValue());
        preparedStatement.execute();


        return true;
    }

    @Override
    public Pair<Pair<String, String>, Integer> select(Pair<String, String> objectIdentifier, boolean  bidirectionalAssociation) throws SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException, NoPermissionException {
        Connection connection = MySQLConnector.getInstance().connect();

        String statement = "SELECT * FROM budget_finance_activity WHERE BudgetActivity=? and Budget=?";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectIdentifier.getKey());
        preparedStatement.setString(2,objectIdentifier.getValue());
        ResultSet rs = preparedStatement.executeQuery();
        rs.next();

        return new Pair<>(objectIdentifier,rs.getInt("Amount"));

    }

    @Override
    public boolean delete(Pair<String, String> objectIdentifier) {
        return false;
    }
}
