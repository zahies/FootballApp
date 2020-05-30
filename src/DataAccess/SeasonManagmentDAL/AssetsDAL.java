package DataAccess.SeasonManagmentDAL;

import DataAccess.DAL;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.MySQLConnector;
import Domain.SeasonManagment.IAsset;
import Domain.Users.Player;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

public class AssetsDAL implements DAL<IAsset, Integer> {


    @Override
    public boolean insert(IAsset objectToInsert) throws SQLException, NoConnectionException {
        Connection connection = MySQLConnector.getInstance().connect();

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

        Connection connection = MySQLConnector.getInstance().connect();

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

    public HashMap<Integer, IAsset> selectAll() throws NoConnectionException, SQLException {
        HashMap<Integer, IAsset> allAssets= new HashMap<>();
        Connection connection = MySQLConnector.getInstance().connect();
        String statement = "";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);

        return allAssets;

    }
}
