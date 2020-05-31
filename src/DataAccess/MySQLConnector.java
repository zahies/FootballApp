package DataAccess;

import DataAccess.Exceptions.NoConnectionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnector{

    public Connection connection = null;
    private static MySQLConnector single_instance = null;
    private MySQLConnector(){

    }

    public static MySQLConnector getInstance()
    {
        if (single_instance == null)
            single_instance = new MySQLConnector();

        return single_instance;
    }
    public Connection connect() throws NoConnectionException {
        if (connection != null) {
            return connection;
        }
        String url = "jdbc:mysql://132.72.65.125:3306/footballappdb?useSSL=false";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, "root", "ISE2424!");
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            throw new NoConnectionException();
        }
    }

    public void disconnect() throws SQLException {
        if(connection!=null) {
            connection.close();
            connection = null;
        }
    }
}
