package DataAccess.SeasonManagmentDAL;

import DataAccess.DAL;
import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.MySQLConnector;
import Domain.SeasonManagment.Field;
import Domain.SeasonManagment.IAsset;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class FieldsDAL implements DAL<Field, Integer> {



    @Override
    public boolean insert(Field objectToInsert) throws SQLException, NoConnectionException, UserInformationException, UserIsNotThisKindOfMemberException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        if (checkExist(objectToInsert.getAssetID(), "fields", "AssetID","")) {
            throw new DuplicatedPrimaryKeyException();
        }
        Connection connection = MySQLConnector.getInstance().connect();
        new AssetsDAL().insert((IAsset) objectToInsert);
        String statement = "INSERT INTO fields (AssetID, teamID) VALUES (?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setInt(1, objectToInsert.getAssetID());
        if (objectToInsert.getMyTeam() == null) {
            preparedStatement.setNull(2, Types.VARCHAR);
        } else {
            preparedStatement.setString(2, objectToInsert.getMyTeam().getId().toString());
        }
        preparedStatement.execute();
        connection.close();
        return true;
    }

    @Override
    public boolean update(Field objectToUpdate) throws SQLException, UserIsNotThisKindOfMemberException, UserInformationException, NoConnectionException, NoPermissionException {
        Connection connection = MySQLConnector.getInstance().connect();
        new AssetsDAL().update(objectToUpdate);

        String statement = "UPDATE fields SET teamID = ? WHERE AssetID=?";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setInt(2, objectToUpdate.getAssetID());
        if (objectToUpdate.getMyTeam() == null) {
            preparedStatement.setNull(1, Types.VARCHAR);
        } else {
            preparedStatement.setString(1, objectToUpdate.getMyTeam().getId().toString());
        }
        int ans = preparedStatement.executeUpdate();
        return ans==1;
    }

    @Override
    public Field select(Integer objectIdentifier, boolean  bidirectionalAssociation) throws SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException, NoPermissionException {
        return null;
    }

    @Override
    public boolean delete(Integer objectIdentifier) {
        return false;
    }
}
