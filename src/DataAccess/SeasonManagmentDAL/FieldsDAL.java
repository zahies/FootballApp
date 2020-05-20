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
    AssetsDAL assetsDAL = new AssetsDAL();

    @Override
    public boolean insert(Field objectToInsert) throws SQLException, NoConnectionException, UserInformationException, UserIsNotThisKindOfMemberException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        if (checkExist(objectToInsert.getAssetID(), "fields", "AssetID")) {
            throw new DuplicatedPrimaryKeyException();
        }
        connection = connect();
        assetsDAL.insert((IAsset) objectToInsert);
        String statement = "INSERT INTO fields (AssetsID, team) VALUES (?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setInt(1, objectToInsert.getAssetID());
        preparedStatement.setInt(2, objectToInsert.getMyTeam().getId());
        preparedStatement.execute();
        connection.close();
        return true;
    }

    @Override
    public boolean update(Field objectToUpdate, Pair<String, Object> valToUpdate) throws SQLException, UserIsNotThisKindOfMemberException, UserInformationException, NoConnectionException, NoPermissionException {
        return false;
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
