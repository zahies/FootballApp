package DataAccess.SeasonManagmentDAL;

import DataAccess.DAL;
import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.MySQLConnector;
import Domain.SeasonManagment.DefaultIScorePolicy;
import Domain.SeasonManagment.DefaultTeamsPolicy;
import Domain.SeasonManagment.IPlaceTeamsPolicy;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.UUID;

public class PlaceTeamsPoliciesDAL implements DAL<IPlaceTeamsPolicy,String> {
    Connection connection = null;

    @Override
    public boolean insert(IPlaceTeamsPolicy objectToInsert) throws SQLException, NoConnectionException, UserInformationException, UserIsNotThisKindOfMemberException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        Connection connection = MySQLConnector.getInstance().connect();
        String statement = "INSERT INTO place_teams_policies (ObjectID, numOfGamesWithEachTeam) VALUES (?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectToInsert.getId().toString());
        preparedStatement.setInt(2,objectToInsert.numOfGamesWithEachTeam());
        preparedStatement.execute();

        return true;
    }

    @Override
    public boolean update(IPlaceTeamsPolicy objectToUpdate) throws SQLException, UserIsNotThisKindOfMemberException, UserInformationException, NoConnectionException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        Connection connection = MySQLConnector.getInstance().connect();
        String statement = "UPDATE place_teams_policies SET numOfGamesWithEachTeam = ? WHERE ObjectID= ?";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setInt(1,objectToUpdate.numOfGamesWithEachTeam());
        preparedStatement.setString(2,objectToUpdate.getId().toString());
        int ans = preparedStatement.executeUpdate();


        return ans==1;
    }

    @Override
    public IPlaceTeamsPolicy select(String objectIdentifier, boolean  bidirectionalAssociation) throws SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException, NoPermissionException {
        Connection connection = MySQLConnector.getInstance().connect();

        String statement="SELECT * FROM place_teams_policies WHERE ObjectID=?";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectIdentifier);
        ResultSet rs = preparedStatement.executeQuery();
        rs.next();

        return new DefaultTeamsPolicy(UUID.fromString(objectIdentifier),rs.getInt("numOfGamesWithEachTeam"));
    }

    @Override
    public boolean delete(String objectIdentifier) {
        return false;
    }
}
