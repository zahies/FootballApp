package DataAccess.AlertsDAL;

import DataAccess.DAL;
import DataAccess.EventsDAL.IEventDAL;
import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.MySQLConnector;
import DataAccess.SeasonManagmentDAL.GamesDAL;
import DataAccess.UserInformationDAL.TeamManagerPermissionsDAL;
import Domain.Alerts.*;
import Domain.Events.IEvent;
import Domain.SeasonManagment.Game;
import Domain.SeasonManagment.TeamStatus;
import FootballExceptions.EmptyPersonalPageException;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class MemberAlertsDAL implements DAL<Pair<Pair<String, IAlert>,String>, Pair<String,String>>{

    /**
     * T - objectToInsert - key = pair (key = alert object ID , value = member user name)
     * Value = alert type - to know in which table the alert is
     * E - objectIdentifier - key = pair (key = alert object ID , value = member user name)
     */


    @Override
    public boolean insert(Pair<Pair<String, IAlert>, String> objectToInsert) throws SQLException, NoConnectionException, UserInformationException, UserIsNotThisKindOfMemberException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        Connection connection = MySQLConnector.getInstance().connect();

        /***Insert to member_alerts table**/
        String statement = "INSERT INTO member_alerts (memberUserName, alertObjectID, Type, Sent) VALUES(?,?,?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectToInsert.getKey().getKey());
        preparedStatement.setString(2,objectToInsert.getKey().getValue().getObjectID().toString());
        preparedStatement.setString(3,objectToInsert.getValue());
        preparedStatement.setBoolean(4,objectToInsert.getKey().getValue().isHadSent());

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
            case "Registration Request":
                new RegisterAlertDAL().insert((RegistrationRequestAlert)objectToInsert.getKey().getValue());
                break;
            default:
                throw new SQLException();
        }
        //connection.close();
        return true;
    }

    @Override
    public boolean update(Pair<Pair<String, IAlert>, String> objectToUpdate) throws SQLException, UserIsNotThisKindOfMemberException, UserInformationException, NoConnectionException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {

        Connection connection = MySQLConnector.getInstance().connect();
        String statement ="";
        if(checkExist(objectToUpdate.getKey().getValue().getObjectID().toString())){
            statement = " UPDATE member_alerts SET type = ?, Sent=? WHERE memberUserName = ? AND alertObjectID =? ";
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setString(1,objectToUpdate.getValue());
            preparedStatement.setBoolean(2,objectToUpdate.getKey().getValue().isHadSent());
            preparedStatement.setString(3,objectToUpdate.getKey().getKey());
            preparedStatement.setString(4,(objectToUpdate.getKey().getValue().getObjectID().toString()));

            preparedStatement.executeUpdate();
        }else{
            this.insert(objectToUpdate);
        }
       // connection.close();
        return true;
    }

    @Override
    public Pair<Pair<String, IAlert>, String> select(Pair<String, String> objectIdentifier, boolean  bidirectionalAssociation) throws SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException, NoPermissionException {
        Connection connection = MySQLConnector.getInstance().connect();

        String statement ="SELECT * FROM member_alerts WHERE AlertObjectID=? and MemberUserName=?";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectIdentifier.getValue());
        preparedStatement.setString(2,objectIdentifier.getKey());
        ResultSet rs = preparedStatement.executeQuery();
        rs.next();
        String type = rs.getString("Type");
        Boolean sent = rs.getBoolean("Sent");
        IAlert alert = null;
        switch (type){
            case "Changed Game Alert":
                statement = "SELECT * FROM member_alerts_changed_game WHERE ObjectID=?";
                preparedStatement = connection.prepareStatement(statement);
                preparedStatement.setString(1,objectIdentifier.getValue());
                rs = preparedStatement.executeQuery();
                rs.next();
                Game game = new GamesDAL().select(rs.getString("Game"),false);
                alert = new ChangedGameAlert(UUID.fromString(objectIdentifier.getValue()),rs.getDate("Date"),game,sent);
                break;
            case "Complaint Alert":
                break;
            case "Registration Request":
                try {
                    alert =  new RegisterAlertDAL().select(objectIdentifier.getValue(),false);
                } catch (EmptyPersonalPageException e) {
                    e.printStackTrace();
                }
                break;
            case "Financial Alert":
                statement = "SELECT * FROM member_alerts_financial WHERE ObjectID=?";
                preparedStatement = connection.prepareStatement(statement);
                preparedStatement.setString(1,objectIdentifier.getValue());
                rs = preparedStatement.executeQuery();
                rs.next();
                alert = new FinancialAlert(rs.getInt("Minus"),UUID.fromString(objectIdentifier.getValue()),sent);
                break;
            case "Team Management Alert":
                statement = "SELECT * FROM member_alerts_management WHERE ObjectID=?";
                preparedStatement = connection.prepareStatement(statement);
                preparedStatement.setString(1,objectIdentifier.getValue());
                rs = preparedStatement.executeQuery();
                rs.next();
                alert = new TeamManagementAlert(UUID.fromString(objectIdentifier.getValue()), TeamStatus.valueOf(rs.getString("TeamStatus")),rs.getString("Message"),sent);
                break;
            case "Game Event Alert":
                statement = "SELECT * FROM member_alerts_game_alert WHERE ObjectID=?";
                preparedStatement = connection.prepareStatement(statement);
                preparedStatement.setString(1,objectIdentifier.getValue());
                rs = preparedStatement.executeQuery();
                rs.next();
                IEvent event= new IEventDAL().select(rs.getString("EventID"),false);
                alert = new GameEventAlert(UUID.fromString(objectIdentifier.getValue()),rs.getDouble("EventMinute"),event,sent);
        }
        return  new Pair<>(new Pair<>("",alert),"");
    }

    @Override
    public boolean delete(Pair<String, String> objectIdentifier) {
        return false;
    }


    public boolean checkExist(String alertID) throws NoConnectionException, SQLException, mightBeSQLInjectionException {
        Connection connection = MySQLConnector.getInstance().connect();
        String statement = "SELECT * FROM member_alerts Where AlertObjectID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);

        preparedStatement.setString(1, alertID);
        ResultSet rs = preparedStatement.executeQuery();
        boolean ans = rs.next();

        return ans;

    }
}
