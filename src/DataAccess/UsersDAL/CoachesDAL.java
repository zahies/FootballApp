package DataAccess.UsersDAL;

import DataAccess.DAL;
import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.SeasonManagmentDAL.AssetsDAL;
import Domain.Alerts.IAlert;
import Domain.SeasonManagment.IAsset;
import Domain.Users.Coach;
import Domain.Users.Member;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import javafx.util.Pair;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class CoachesDAL implements DAL<Member, String> {
    Connection connection = null;

    @Override
    public boolean insert(Member objectToInsert) throws SQLException, NoConnectionException, mightBeSQLInjectionException, UserInformationException, NoPermissionException, UserIsNotThisKindOfMemberException, DuplicatedPrimaryKeyException {
        connection = connect();
        if (connection == null) {
            throw new NoConnectionException();
        }
        new MembersDAL().insert(objectToInsert);
        new AssetsDAL().insert((IAsset) objectToInsert);

        if (checkExist(objectToInsert.getName(), "coaches", "UserName","")) {
            throw new UserInformationException();
        }
        String statement = "INSERT INTO coaches (UserName, Team, PersonalPage, AssetID, training) VALUES(?,?,?,?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1, objectToInsert.getName());
        if(((Coach) objectToInsert).getMyTeam()==null){
            preparedStatement.setNull(2, Types.VARCHAR);
        }else {
            preparedStatement.setString(2, ((Coach) objectToInsert).getMyTeam().getId().toString());
        }
        if(((Coach) objectToInsert).getInfo()==null){
            preparedStatement.setNull(3, Types.VARCHAR);
        }else {
            preparedStatement.setInt(3, ((Coach) objectToInsert).getInfo().getPageID());
        }
        preparedStatement.setInt(4, ((Coach) objectToInsert).getAssetID());
        preparedStatement.setString(5, ((Coach) objectToInsert).getTraining());
        preparedStatement.execute();
        connection.close();
        return true;
    }

    @Override
    public boolean update(Member objectToUpdate) throws SQLException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException, NoPermissionException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException {
        connection = connect();
        new MembersDAL().update(objectToUpdate);

        String statement = "UPDATE coaches SET Team =? , PersonalPage=?, AssetID=?, Training=? WHERE UserName=?";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(5, objectToUpdate.getName());
        preparedStatement.setString(1, ((Coach) objectToUpdate).getMyTeam().getId().toString());
        preparedStatement.setInt(2, ((Coach) objectToUpdate).getInfo().getPageID());
        preparedStatement.setInt(3, ((Coach) objectToUpdate).getAssetID());
        preparedStatement.setString(4, ((Coach) objectToUpdate).getTraining());

        int ans = preparedStatement.executeUpdate();
        connection.close();
        new AssetsDAL().update((IAsset)objectToUpdate);
        return ans ==1;
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
