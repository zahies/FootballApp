package DataAccess.SeasonManagmentDAL;

import DataAccess.DAL;
import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.UsersDAL.TeamOwnersDAL;
import Domain.SeasonManagment.Team;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TeamsDAL implements DAL<Team, String> {
    Connection connection = null;

    @Override
    public boolean insert(Team objectToInsert) throws SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        connection = connect();

        String statement = "INSERT INTO teams(TeamID,Name,PersonalPage,Owner,TeamStatus,ControlBudget,isClosed,playersFootballRate,SystemManagerCloser) VALUES (?,?,?,?,?,?,?,?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1, objectToInsert.getId().toString());
        preparedStatement.setString(2, objectToInsert.getName());
        if (objectToInsert.getInfo() != null) {
            preparedStatement.setInt(3, objectToInsert.getInfo().getPageID());
        } else {
            preparedStatement.setInt(3, 0);
        }
        preparedStatement.setString(4, objectToInsert.getOwner().getName());
        preparedStatement.setString(5, objectToInsert.getStatus().toString());
        if (objectToInsert.getControlBudget() != null) {
            //preparedStatement.setInt(6,objectToInsert.getControlBudget().getID());
            preparedStatement.setString(6, objectToInsert.getId().toString());
        } else {
            preparedStatement.setInt(6, 0);
        }
        preparedStatement.setBoolean(7, objectToInsert.isActive());
        preparedStatement.setDouble(8, objectToInsert.getPlayersFootballRate());
        preparedStatement.setBoolean(9, objectToInsert.isClosed());
        preparedStatement.execute();


        new TeamOwnersDAL().update(objectToInsert.getOwner());

        connection.close();

        return true;
    }

    @Override
    public boolean update(Team objectToUpdate) throws SQLException, NoConnectionException {
        connection = connect();

        String statement ="UPDATE teams SET Name=?,PersonalPage=?,Owner=?,TeamStatus=?,ControlBudget=?,isClosed=?,PlayersFootballRate=?,SystemManagerCloser=? WHERE TeamID=?";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(9, objectToUpdate.getId().toString());
        preparedStatement.setString(1, objectToUpdate.getName());
        if (objectToUpdate.getInfo() != null) {
            preparedStatement.setInt(2, objectToUpdate.getInfo().getPageID());
        } else {
            preparedStatement.setInt(2, 0);
        }
        preparedStatement.setString(3, objectToUpdate.getOwner().getName());
        preparedStatement.setString(4, objectToUpdate.getStatus().toString());
        if (objectToUpdate.getControlBudget() != null) {
            //preparedStatement.setInt(6,objectToInsert.getControlBudget().getID());
            preparedStatement.setString(5, objectToUpdate.getId().toString());
        } else {
            preparedStatement.setInt(5, 0);
        }
        preparedStatement.setBoolean(6, objectToUpdate.isActive());
        preparedStatement.setDouble(7, objectToUpdate.getPlayersFootballRate());
        preparedStatement.setBoolean(8, objectToUpdate.isClosed());
        int ans = preparedStatement.executeUpdate();
        connection.close();

        return ans ==1 ;
    }

    @Override
    public Team select(String objectIdentifier) {
        return null;
    }

    @Override
    public boolean delete(String objectIdentifier) {
        return false;
    }
}
