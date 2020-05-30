package DataAccess.SeasonManagmentDAL;

import DataAccess.DAL;
import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.MySQLConnector;
import Domain.SeasonManagment.Budget;
import Domain.SeasonManagment.ControlBudget;
import Domain.SeasonManagment.DefaultCommissionerRule;
import Domain.SeasonManagment.ICommissionerRule;
import FootballExceptions.EmptyPersonalPageException;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import javafx.util.Pair;

import java.lang.reflect.Type;
import java.sql.*;
import java.util.UUID;

public class ControlBudgetDAL implements DAL<ControlBudget, String> {

    @Override
    public boolean insert(ControlBudget objectToInsert) throws SQLException, NoConnectionException, UserInformationException, UserIsNotThisKindOfMemberException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        Connection connection = MySQLConnector.getInstance().connect();

        String statement = "INSERT INTO control_budgets (ObjectID, CommissionerRule) VALUES (?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1, objectToInsert.getObjectID().toString());
        if (objectToInsert.getCommissionerRule() == null) {
            preparedStatement.setNull(2, Types.VARCHAR);
        } else {
            preparedStatement.setString(2, (objectToInsert.getCommissionerRule().getObjectID().toString()));
        }
        preparedStatement.execute();
        new BudgetsDAL().insert(objectToInsert.getBudget_quarter_1());
        new BudgetsDAL().insert(objectToInsert.getBudget_quarter_2());
        new BudgetsDAL().insert(objectToInsert.getBudget_quarter_3());
        new BudgetsDAL().insert(objectToInsert.getBudget_quarter_4());
        connection.close();
        return true;
    }

    @Override
    public boolean update(ControlBudget objectToUpdate) throws SQLException, UserIsNotThisKindOfMemberException, UserInformationException, NoConnectionException, NoPermissionException {
        Connection connection = MySQLConnector.getInstance().connect();

        String statement = "UPDATE control_budgets SET CommissionerRule = ? WHERE ObjectID=?;";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        if (objectToUpdate.getCommissionerRule() == null) {
            preparedStatement.setNull(1, Types.VARCHAR);
        } else {
            preparedStatement.setString(1, (objectToUpdate.getCommissionerRule().getObjectID().toString()));
        } preparedStatement.setString(2, objectToUpdate.getObjectID().toString());
        int ans = preparedStatement.executeUpdate();
        connection.close();

        return ans==1;
    }

    @Override
    public ControlBudget select(String objectIdentifier, boolean  bidirectionalAssociation) throws SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException, NoPermissionException {
        Connection connection = MySQLConnector.getInstance().connect();

        /***CONTROL BUDGET DETAILS*/
        String statement = "SELECT * FROM control_budgets WHERE ObjectID =?";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectIdentifier);
        ResultSet rs = preparedStatement.executeQuery();
        rs.next();
        ICommissionerRule defaultCommissionerRule = new ICommissionerRulesDAL().select(rs.getString("CommissionerRule"),false);
        statement = "SELECT * FROM budgets WHERE ControlBudget=?";
        preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectIdentifier);
        rs = preparedStatement.executeQuery();
        rs.next();
        Budget budget1 = new BudgetsDAL().select(rs.getString("ObjectID"),false);
        Budget budget2 = new BudgetsDAL().select(rs.getString("ObjectID"),false);
        Budget budget3 = new BudgetsDAL().select(rs.getString("ObjectID"),false);
        Budget budget4 = new BudgetsDAL().select(rs.getString("ObjectID"),false);

        return new ControlBudget(UUID.fromString(objectIdentifier),budget1,budget2,budget3,budget4,defaultCommissionerRule);
    }

    @Override
    public boolean delete(String objectIdentifier) {
        return false;
    }
}
