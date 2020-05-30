package DataAccess.EventsDAL;

import DataAccess.DAL;
import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.MySQLConnector;
import DataAccess.UsersDAL.PlayersDAL;
import Domain.Events.*;
import Domain.Users.Player;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class IEventDAL implements DAL<IEvent,String> {



    @Override
    public boolean insert(IEvent objectToInsert) throws SQLException, NoConnectionException, UserInformationException, UserIsNotThisKindOfMemberException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        Connection connection = MySQLConnector.getInstance().connect();

        String statement = "INSERT INTO events (objectID, eventMinute, PlayerCommitted,logger, Type) VALUES(?,?,?,?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectToInsert.getObjectID().toString());
        preparedStatement.setDouble(2,objectToInsert.getGameMinute());
        preparedStatement.setString(3,objectToInsert.getEvent_logger().getObjectID().toString());
        preparedStatement.setString(4,objectToInsert.getPlayerWhocommit().getName());
        if(objectToInsert instanceof Foul){
            preparedStatement.setString(5,"Foul");
        }else if(objectToInsert instanceof Goal){
            preparedStatement.setString(5,"Goal");
        }else if(objectToInsert instanceof Injury){
            preparedStatement.setString(5,"Injury");
        }else if(objectToInsert instanceof OffSide){
            preparedStatement.setString(5,"Offside");
        }else if(objectToInsert instanceof RedCard){
            preparedStatement.setString(5,"RedCard");
        }else if(objectToInsert instanceof YellowCard){
            preparedStatement.setString(5,"YellowCard");
        }else if(objectToInsert instanceof Substitution){
            preparedStatement.setString(5,"Substitution");
            preparedStatement.execute();
            new SubstitutionDAL().insert((Substitution) objectToInsert);
            connection.close();
            return true;
        }
        preparedStatement.execute();
        connection.close();
        return true;
    }

    @Override
    public boolean update(IEvent objectToUpdate) throws SQLException, UserIsNotThisKindOfMemberException, UserInformationException, NoConnectionException, NoPermissionException {
        Connection connection = MySQLConnector.getInstance().connect();

        if(objectToUpdate instanceof Substitution){
            new SubstitutionDAL().update((Substitution) objectToUpdate);
        }
        String statement = "UPDATE events SET EventMinute=?,PlayerCommitted=?,Logger=?,Type=? WHERE ObjectID=?";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setDouble(1,objectToUpdate.getGameMinute());
        preparedStatement.setString(2,objectToUpdate.getPlayerWhocommit().getName());
        preparedStatement.setString(3,objectToUpdate.getEvent_logger().getObjectID().toString());
        preparedStatement.setString(4,objectToUpdate.getObjectID().toString());

        int ans = preparedStatement.executeUpdate();
        return ans==1;
    }

    @Override
    public IEvent select(String objectIdentifier, boolean  bidirectionalAssociation) throws SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException, NoPermissionException {
        Connection connection = MySQLConnector.getInstance().connect();

        String statement ="SELECT * FROM events WHERE ObjectID=?";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectIdentifier);
        ResultSet rs = preparedStatement.executeQuery();
        String type= rs.getString("Type");
        if(type.equals("Substitution")){
            return new SubstitutionDAL().select(rs.getString("ObjectID"),false);
        }
        double eventMin = rs.getDouble("EventMinute");
        Player playerCommitted = new PlayersDAL().select(rs.getString("PlayerCommitted"),false);
        switch (type){
            case "Foul":
                return new Foul(eventMin,playerCommitted, UUID.fromString(objectIdentifier));
            case "Goal":
                return new Goal(eventMin,playerCommitted,UUID.fromString(objectIdentifier));
            case "Injury":
                return new Injury(eventMin,playerCommitted,UUID.fromString(objectIdentifier));
            case "Offside":
                return new OffSide(eventMin,playerCommitted,UUID.fromString(objectIdentifier));
            case "RedCard":
                return new RedCard(eventMin,playerCommitted,UUID.fromString(objectIdentifier));
            case "YellowCard":
                return new YellowCard(eventMin,playerCommitted,UUID.fromString(objectIdentifier));
        }
        return null;
    }

    @Override
    public boolean delete(String objectIdentifier) {
        return false;
    }
}
