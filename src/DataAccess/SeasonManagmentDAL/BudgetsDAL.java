package DataAccess.SeasonManagmentDAL;

import DataAccess.DAL;
import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import Domain.SeasonManagment.Budget;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BudgetsDAL implements DAL<Budget,String> {
    Connection connection = null;

    @Override
    public boolean insert(Budget objectToInsert) throws SQLException, NoConnectionException, UserInformationException, UserIsNotThisKindOfMemberException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        connection = connect();

        String statement ="INSERT INTO budgets (ObjectID, Team, ControlBudget) values (?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectToInsert.getObjectID().toString());
        preparedStatement.setString(2,objectToInsert.getTeamID().toString());
        preparedStatement.setString(3,objectToInsert.getControlBudgetID().toString());
        preparedStatement.execute();

        connection.close();
        return true;
    }

    @Override
    public boolean update(Budget objectToUpdate) throws SQLException, UserIsNotThisKindOfMemberException, UserInformationException, NoConnectionException, NoPermissionException {
        connection = connect();

        String statement ="UPDATE budgets SET Team =?,ControlBudget=? WHERE ObjectID=?";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(3,objectToUpdate.getObjectID().toString());
        preparedStatement.setString(1,objectToUpdate.getTeamID().toString());
        preparedStatement.setString(2,objectToUpdate.getControlBudgetID().toString());
        int ans = preparedStatement.executeUpdate();

        connection.close();
        return ans ==1;
    }

    @Override
    public Budget select(String objectIdentifier) throws SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException, NoPermissionException {
        return null;
    }

    @Override
    public boolean delete(String objectIdentifier) {
        return false;
    }
}
