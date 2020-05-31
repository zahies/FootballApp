package DataAccess.EventsDAL;

import DataAccess.DAL;
import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.MySQLConnector;
import Domain.Events.Event_Logger;
import Domain.Events.IEvent;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class EventLoggersDAL implements DAL<Event_Logger,String> {


    @Override
    public boolean insert(Event_Logger objectToInsert) throws SQLException, NoConnectionException, UserInformationException, UserIsNotThisKindOfMemberException, NoPermissionException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException {
        Connection connection = MySQLConnector.getInstance().connect();

        String statement = "INSERT INTO events_logger (objectID) VALUES (?);";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectToInsert.getObjectID().toString());
        preparedStatement.execute();
        return true;
    }

    @Override
    public boolean update(Event_Logger objectToUpdate) throws SQLException, UserIsNotThisKindOfMemberException, UserInformationException, NoConnectionException, NoPermissionException {

        return true;
    }

    @Override
    public Event_Logger select(String objectIdentifier, boolean  bidirectionalAssociation) throws SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException, NoPermissionException {
        Connection connection = MySQLConnector.getInstance().connect();

        String statement = "SELECT  ObjectID FROM events WHERE Logger =?";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1,objectIdentifier);
        ResultSet rs = preparedStatement.executeQuery();
        List<IEvent> events = new LinkedList<>();

        while (rs.next()){
            events.add(new IEventDAL().select(rs.getString("ObjectID"),false));
        }

        return new Event_Logger(UUID.fromString(objectIdentifier),events);
    }

    @Override
    public boolean delete(String objectIdentifier) {
        return false;
    }
}
