package DataAccess.SeasonManagmentDAL;

import DataAccess.DAL;
import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import Domain.SeasonManagment.IPlaceTeamsPolicy;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;

public class PlaceTeamsPoliciesDAL implements DAL<IPlaceTeamsPolicy,String> {
    Connection connection = null;

    @Override
    public boolean insert(IPlaceTeamsPolicy objectToInsert) throws SQLException, NoConnectionException, UserInformationException, UserIsNotThisKindOfMemberException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        connection = connect();
        String statement = "INSERT INTO place_teams_policies (ObjectID, numOfGamesWithEachTeam) VALUES (?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectToInsert.getId().toString());
        preparedStatement.setInt(2,objectToInsert.numOfGamesWithEachTeam());
        preparedStatement.execute();

        connection.close();
        return true;
    }

    @Override
    public boolean update(IPlaceTeamsPolicy objectToUpdate) throws SQLException, UserIsNotThisKindOfMemberException, UserInformationException, NoConnectionException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        connection = connect();
        String statement = "UPDATE place_teams_policies SET numOfGamesWithEachTeam = ? WHERE ObjectID= ?";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setInt(1,objectToUpdate.numOfGamesWithEachTeam());
        preparedStatement.setString(2,objectToUpdate.getId().toString());
        int ans = preparedStatement.executeUpdate();
        connection.close();

        return ans==1;
    }

    @Override
    public IPlaceTeamsPolicy select(String objectIdentifier) throws SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException, NoPermissionException {
        return null;
    }

    @Override
    public boolean delete(String objectIdentifier) {
        return false;
    }
}