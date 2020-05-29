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
    public boolean insert(IAsset objectToInsert) throws SQLException, NoConnectionException {
        connection = connect();

        String statement = "INSERT INTO assets (AssetID, Value) VALUES (?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setInt(1, objectToInsert.getAssetID());
        preparedStatement.setInt(2, objectToInsert.getValue());
        preparedStatement.execute();
        connection.close();
        return true;
    }

    @Override
    public boolean update(IAsset objectToUpdate) throws SQLException, NoConnectionException {

        connection = connect();

        String statement = "UPDATE assets SET Value =  ? WHERE AssetID = ?; ";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setInt(1, objectToUpdate.getValue());
        preparedStatement.setInt(2, objectToUpdate.getAssetID());
        int ans = preparedStatement.executeUpdate();

        connection.close();
        return ans ==1;
    }

    @Override
    public IAsset select(Integer objectIdentifier, boolean  bidirectionalAssociation) {
        return null;
    }


    @Override
    public boolean delete(Integer objectIdentifier) {
        return false;
    }
}
