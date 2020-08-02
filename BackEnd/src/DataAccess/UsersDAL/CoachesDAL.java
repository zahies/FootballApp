package DataAccess.UsersDAL;

import DataAccess.DAL;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.SeasonManagmentDAL.AssetsDAL;
import Domain.SeasonManagment.IAsset;
import Domain.Users.Coach;
import Domain.Users.Member;
import FootballExceptions.UserInformationException;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CoachesDAL implements DAL<Member, String> {
    Connection connection = null;
    MembersDAL membersDAL = new MembersDAL();
    AssetsDAL assetsDAL = new AssetsDAL();

    @Override
    public boolean insert(Member objectToInsert) throws SQLException, NoConnectionException, mightBeSQLInjectionException, UserInformationException {
        connection = connect();
        if (connection == null) {
            throw new NoConnectionException();
        }
        membersDAL.insert(objectToInsert);
        assetsDAL.insert((IAsset) objectToInsert);
        objectToInsert = ((Coach) objectToInsert);

        if (checkExist(objectToInsert.getName(), "coaches", "coachName")) {
            throw new UserInformationException();
        }
        String statement = "INSERT INTO coaches (coachName, coachTeam, coachPersonalPage, coachAssetID, training) VALUES(?,?,?,?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1, objectToInsert.getName());
        preparedStatement.setInt(2, ((Coach) objectToInsert).getMyTeam().getId());
        preparedStatement.setInt(3, ((Coach) objectToInsert).getInfo().getPageID());
        preparedStatement.setInt(4, ((Coach) objectToInsert).getAssetID());
        preparedStatement.setString(5, ((Coach) objectToInsert).getTraining());
        preparedStatement.execute();
        connection.close();
        return true;
    }

    @Override
    public boolean update(Member objectToUpdate, Pair<String, Object> valToUpdate) throws SQLException {
        return false;
    }

    @Override
    public Member select(String objectIdentifier) {
        return null;
    }

    @Override
    public boolean delete(String objectIdentifier) {
        return false;
    }
}
