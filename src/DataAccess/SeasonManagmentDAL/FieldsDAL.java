package DataAccess.SeasonManagmentDAL;

import DataAccess.DAL;
import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import Domain.SeasonManagment.Field;
import Domain.SeasonManagment.IAsset;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FieldsDAL implements DAL<Field, Integer> {
    Connection connection = null;


    @Override
    public boolean insert(Field objectToInsert) throws SQLException, NoConnectionException, UserInformationException, UserIsNotThisKindOfMemberException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        if (checkExist(objectToInsert.getAssetID(), "fields", "AssetID","")) {
            throw new DuplicatedPrimaryKeyException();
        }
        connection = connect();
        new AssetsDAL().insert((IAsset) objectToInsert);
        String statement = "INSERT INTO fields (AssetID, teamID) VALUES (?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setInt(1, objectToInsert.getAssetID());
        preparedStatement.setString(2, objectToInsert.getMyTeam().getId().toString());
        preparedStatement.execute();
        connection.close();
        return true;
    }

    @Override
    public boolean update(Field objectToUpdate) throws SQLException, UserIsNotThisKindOfMemberException, UserInformationException, NoConnectionException, NoPermissionException {
        connection = connect();
        new AssetsDAL().update(objectToUpdate);

        String statement = "UPDATE fields SET teamID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectToUpdate.getMyTeam().getId().toString());
        int ans = preparedStatement.executeUpdate();
        return ans==1;
    }

    @Override
    public Field select(Integer objectIdentifier) throws SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException, NoPermissionException {
        return null;
    }

    @Override
    public boolean delete(Integer objectIdentifier) {
        return false;
    }
}
