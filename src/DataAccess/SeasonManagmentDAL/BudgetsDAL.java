package DataAccess.SeasonManagmentDAL;

import DataAccess.DAL;
import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.MySQLConnector;
import Domain.SeasonManagment.Budget;
import Domain.SeasonManagment.BudgetActivity;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import javafx.util.Pair;

import java.sql.*;
import java.util.LinkedList;
import java.util.UUID;

public class BudgetsDAL implements DAL<Budget,String> {


    @Override
    public boolean insert(Budget objectToInsert) throws SQLException, NoConnectionException, UserInformationException, UserIsNotThisKindOfMemberException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        Connection connection = MySQLConnector.getInstance().connect();

        String statement ="INSERT INTO budgets (ObjectID, Team, ControlBudget) values (?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectToInsert.getObjectID().toString());
        preparedStatement.setString(2,objectToInsert.getTeamID().toString());
        if (objectToInsert.getControlBudgetID() == null) {
            preparedStatement.setNull(3, Types.VARCHAR);
        } else {
            preparedStatement.setString(3, objectToInsert.getControlBudgetID().toString());
        }
        preparedStatement.execute();

        return true;
    }

    @Override
    public boolean update(Budget objectToUpdate) throws SQLException, UserIsNotThisKindOfMemberException, UserInformationException, NoConnectionException, NoPermissionException {
        Connection connection = MySQLConnector.getInstance().connect();

        String statement ="UPDATE budgets SET Team =?,ControlBudget=? WHERE ObjectID=?";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(3,objectToUpdate.getObjectID().toString());
        preparedStatement.setString(1,objectToUpdate.getTeamID().toString());
        if (objectToUpdate.getControlBudgetID() == null) {
            preparedStatement.setNull(2, Types.VARCHAR);
        } else {
            preparedStatement.setString(2, objectToUpdate.getControlBudgetID().toString());
        }
        int ans = preparedStatement.executeUpdate();

        return ans ==1;
    }

    @Override
    public Budget select(String objectIdentifier, boolean  bidirectionalAssociation) throws SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException, NoPermissionException {
        Connection connection = MySQLConnector.getInstance().connect();

        String statement = "SELECT * FROM budgets WHERE ObjectID=?";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectIdentifier);
        ResultSet rs =preparedStatement.executeQuery();
        rs.next();
        UUID teamID = UUID.fromString(rs.getString("Team"));
        UUID controlBudgetID = UUID.fromString(rs.getString("ControlBudget"));

        LinkedList<Pair<BudgetActivity, Integer>> financeActivity = new LinkedList<>();
        statement = "SELECT * FROM budget_finance_activity WHERE Budget=?";
        preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectIdentifier);
        rs = preparedStatement.executeQuery();
        while (rs.next()){
            financeActivity.add(new Pair<>(BudgetActivity.valueOf(rs.getString("BudgetActivity")),new BudgetActivitiesDAL().select(new Pair<>(rs.getString("BudgetActivity"),objectIdentifier),false).getValue()));
        }
        return new Budget(financeActivity,teamID,UUID.fromString(objectIdentifier),controlBudgetID);
    }

    @Override
    public boolean delete(String objectIdentifier) {
        return false;
    }
}
