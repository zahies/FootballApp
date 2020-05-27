package DataAccess.SeasonManagmentDAL;

import DataAccess.DAL;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import Domain.SeasonManagment.ControlBudget;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ControlBudgetDAL implements DAL<ControlBudget, String> {
    Connection connection = null;

    @Override
    public boolean insert(ControlBudget objectToInsert) throws SQLException, NoConnectionException, UserInformationException, UserIsNotThisKindOfMemberException, NoPermissionException, mightBeSQLInjectionException {
        connection = connect();

        String statement = "INSERT INTO control_budgets (ObjectID, CommissionerRule) VALUES (?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1, objectToInsert.getObjectID().toString());
        preparedStatement.setString(2,(objectToInsert.getCommissionerRule().getObjectID().toString()));
        preparedStatement.execute();
        connection.close();
        return true;
    }

    @Override
    public boolean update(ControlBudget objectToUpdate) throws SQLException, UserIsNotThisKindOfMemberException, UserInformationException, NoConnectionException, NoPermissionException {
        connection = connect();

        String statement = "UPDATE control_budgets SET CommissionerRule = ? WHERE ObjectID=?;";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,(objectToUpdate.getCommissionerRule().getObjectID().toString()));
        preparedStatement.setString(2, objectToUpdate.getObjectID().toString());
        int ans = preparedStatement.executeUpdate();
        connection.close();

        return ans==1;
    }

    @Override
    public ControlBudget select(String objectIdentifier) throws SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException, NoPermissionException {
        return null;
    }

    @Override
    public boolean delete(String objectIdentifier) {
        return false;
    }
}
