package DataAccess.SeasonManagmentDAL;

import DataAccess.DAL;
import DataAccess.Exceptions.NoConnectionException;
import Domain.SeasonManagment.IAsset;
import Domain.Users.Player;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AssetsDAL implements DAL<IAsset, Integer> {
    Connection connection = null;

    @Override
    public boolean insert(IAsset objectToInsert) throws SQLException {
        connection = connect();
        if (connection == null) {
            return false;
        }
        String statement = "INSERT INTO assets (AssetID, AssetVal) VALUES (?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setInt(1, objectToInsert.getAssetID());
        preparedStatement.setInt(2, objectToInsert.getValue());
        preparedStatement.execute();
        connection.close();
        return true;
    }

    @Override
    public boolean update(IAsset objectToUpdate, Pair<String, Object> valToUpdate) throws SQLException, NoConnectionException {

        connection = connect();
        if (connection == null) {
            throw new NoConnectionException();
        }
        String statement = "UPDATE assets SET " + valToUpdate.getKey() + " =  ? " +
                "WHERE AssetID = ?; ";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setInt(1, (Integer) valToUpdate.getValue());
        preparedStatement.setInt(2, objectToUpdate.getAssetID());
        preparedStatement.executeUpdate();
        connection.close();
        return true;
    }

    @Override
    public IAsset select(Integer objectIdentifier) {
        return null;
    }


    @Override
    public boolean delete(Integer objectIdentifier) {
        return false;
    }
}
