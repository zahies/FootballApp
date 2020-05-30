package DataAccess;

import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import Domain.Users.Member;
import FootballExceptions.EmptyPersonalPageException;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import com.mysql.jdbc.MySQLConnection;
import javafx.util.Pair;

import java.sql.*;
import java.util.*;

public interface DAL<T, E> {

    public static final Set<String> allPrimaryKeysName = new HashSet<>(Arrays.asList("UserName", "objectID", "gameID", "AssetID"));
    public static final Set<String> allTablesName = new HashSet<>(Arrays.asList("members", "teamowners", "controlbudget", "personalpages", "game", "fields"));

    /**
     * TO PREVENT SQL INJECTION
     */
    public static MySQLConnector mySQLConnector = MySQLConnector.getInstance();
    public boolean insert(T objectToInsert) throws SQLException, NoConnectionException, UserInformationException, UserIsNotThisKindOfMemberException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException;

    public boolean update(T objectToUpdate) throws SQLException, UserIsNotThisKindOfMemberException, UserInformationException, NoConnectionException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException;

    public T select(E objectIdentifier, boolean  bidirectionalAssociation) throws SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException, NoPermissionException, EmptyPersonalPageException;

    public boolean delete(E objectIdentifier);

    public default boolean checkExist(E objectIdentifier, String tableName, String primaryKeyName, String primaryKeyName2) throws NoConnectionException, SQLException, mightBeSQLInjectionException {

        Connection connection = mySQLConnector.connect();
//        if (!allTablesName.contains(tableName) || !allPrimaryKeysName.contains(primaryKeyName)|| !allPrimaryKeysName.contains(primaryKeyName2)) {
//            throw new mightBeSQLInjectionException();
//        }
        String statement = "SELECT * FROM " + tableName + " Where " + primaryKeyName + " = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        if (objectIdentifier instanceof String) {
            preparedStatement.setString(1, ((String) objectIdentifier));
        } else if(objectIdentifier instanceof Integer){
            preparedStatement.setInt(1, ((Integer) objectIdentifier));
        } else if(objectIdentifier instanceof Pair){
            statement = "SELECT * FROM " + tableName + " Where " + primaryKeyName + " = ? and " + primaryKeyName2 +" =?";
            preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setString(1,((Pair) objectIdentifier).getKey().toString());
            if(((Pair) objectIdentifier).getValue()instanceof Integer){
                preparedStatement.setInt(2, (Integer) ((Pair) objectIdentifier).getValue());
            }else {
                preparedStatement.setString(2, (String) ((Pair) objectIdentifier).getValue());
            }
        }
        ResultSet rs = preparedStatement.executeQuery();
        boolean ans = rs.next();
        MySQLConnector.getInstance().disconnect();
        return ans;
    }


}
