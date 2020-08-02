package DataAccess.SeasonManagmentDAL;

import DataAccess.DAL;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.UsersDAL.TeamOwnersDAL;
import Domain.SeasonManagment.Team;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TeamsDAL implements DAL<Team, Integer> {
    Connection connection = null;

    @Override
    public boolean insert(Team objectToInsert) throws SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException {
        connection = connect();
        if (connection == null) {
            return false;
        }
        String statement = "INSERT INTO teams(TeamID,Name,PersonalPage,Owner,TeamStatus,ControlBudget,isClosed,playersFootballRate,systemManagerClosed) VALUES (?,?,?,?,?,?,?,?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setInt(1, objectToInsert.getId());
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
            preparedStatement.setInt(6, 0);
        } else {
            preparedStatement.setInt(6, 0);
        }
        preparedStatement.setBoolean(7, objectToInsert.isActive());
        preparedStatement.setDouble(8, objectToInsert.getPlayersFootballRate());
        preparedStatement.setBoolean(9, objectToInsert.isClosed());
        preparedStatement.execute();
        TeamOwnersDAL teamOwnersDAL = new TeamOwnersDAL();
        teamOwnersDAL.update(objectToInsert.getOwner(), new Pair<>("Team", objectToInsert.getId()));

        connection.close();

        return true;
    }

    @Override
    public boolean update(Team objectToUpdate, Pair<String, Object> valToUpdate) throws SQLException {
        return false;
    }

    @Override
    public Team select(Integer objectIdentifier) {
        return null;
    }

    @Override
    public boolean delete(Integer objectIdentifier) {
        return false;
    }
}
