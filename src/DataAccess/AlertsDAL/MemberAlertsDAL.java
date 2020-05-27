package DataAccess.AlertsDAL;

import DataAccess.DAL;
import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.UserInformationDAL.TeamManagerPermissionsDAL;
import Domain.Alerts.*;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberAlertsDAL implements DAL<Pair<Pair<String, IAlert>,String>, Pair<String,String>>{

    /**
     * T - objectToInsert - key = pair (key = alert object ID , value = member user name)
     * Value = alert type - to know in which table the alert is
     * E - objectIdentifier - key = pair (key = alert object ID , value = member user name)
     */
    Connection connection = null;

    @Override
    public boolean insert(Pair<Pair<String, IAlert>, String> objectToInsert) throws SQLException, NoConnectionException, UserInformationException, UserIsNotThisKindOfMemberException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        connection=connect();

        /***Insert to member_alerts table**/
        String statement = "INSERT INTO member_alerts (memberUserName, alertObjectID, Type) VALUES(?,?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectToInsert.getKey().getKey());
        preparedStatement.setString(2,objectToInsert.getKey().getValue().getObjectID().toString());
        preparedStatement.setString(3,objectToInsert.getValue());
        preparedStatement.execute();

        String type =  objectToInsert.getValue();
        switch (type){
            case "Changed Game Alert":
                new ChangedGameAlertsDAL().insert((ChangedGameAlert)objectToInsert.getKey().getValue());
                break;
            case "Complaint Alert":
                new ComplaintAlertsDAL().insert((ComplaintAlert)objectToInsert.getKey().getValue());
                break;
            case "Financial Alert":
                new FinancialAlertsDAL().insert((FinancialAlert)objectToInsert.getKey().getValue());
                break;
            case "Game Event Alert":
                new GameEventAlertsDAL().insert((GameEventAlert)objectToInsert.getKey().getValue());
                break;
            case "Personal Page Alert":
                new PersonalPageAlertsDAL().insert((PersonalPageAlert)objectToInsert.getKey().getValue());
                break;
            case "Team Management Alert":
                new TeamManagementAlertsDAL().insert((TeamManagementAlert)objectToInsert.getKey().getValue());
                break;
            default:
                throw new SQLException();
        }
        connection.close();
        return true;
    }

    @Override
    public boolean update(Pair<Pair<String, IAlert>, String> objectToUpdate) throws SQLException, UserIsNotThisKindOfMemberException, UserInformationException, NoConnectionException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {

        connection=connect();
        String statement ="";
        if(checkExist(new Pair<String, String>(objectToUpdate.getKey().getKey(),objectToUpdate.getKey().getValue().getObjectID().toString()),"member_alerts","AlertObjectID","UserName")){
            statement = " UPDATE member_alerts SET type = ? WHERE memberUserName = ? AND alertObjectID =? ";
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setString(1,objectToUpdate.getValue());
            preparedStatement.setString(2,objectToUpdate.getKey().getKey());
            preparedStatement.setString(3,(objectToUpdate.getKey().getValue().getObjectID().toString()));
            preparedStatement.executeUpdate();
        }else{
            this.insert(objectToUpdate);
        }
        connection.close();
        return true;
    }

    @Override
    public Pair<Pair<String, IAlert>, String> select(Pair<String, String> objectIdentifier) throws SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException, NoPermissionException {
        return null;
    }

    @Override
    public boolean delete(Pair<String, String> objectIdentifier) {
        return false;
    }

//    @Override
//    public boolean checkExist(Pair<String, String> objectIdentifier, String tableName, String primaryKeyName,S) throws NoConnectionException, SQLException, mightBeSQLInjectionException {
//        connection = connect();
//        if (!allTablesName.contains(tableName) || !allPrimaryKeysName.contains(primaryKeyName)) {
//            throw new mightBeSQLInjectionException();
//        }
//        String statement = "SELECT * FROM " + tableName + " Where " + primaryKeyName + " = ?";
//        PreparedStatement preparedStatement = connection.prepareStatement(statement);
//
//        preparedStatement.setString(1, objectIdentifier.getValue());
//        ResultSet rs = preparedStatement.executeQuery();
//        boolean ans = rs.next();
//        connection.close();
//        return ans;
//
//    }
}
