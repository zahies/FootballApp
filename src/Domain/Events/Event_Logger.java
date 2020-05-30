package Domain.Events;

import DataAccess.EventsDAL.EventLoggersDAL;
import DataAccess.Exceptions.NoConnectionException;
import Domain.SeasonManagment.Game;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Event_Logger {

    private UUID objectID;
    public List<IEvent> events;

    /**FOR DB SELECT**/
    public Event_Logger(UUID objectID, List<IEvent> events) {
        this.objectID = objectID;
        this.events = events;
    }

    public Event_Logger() {
        events = new LinkedList<>();
        objectID = UUID.randomUUID();
    }

    public UUID getObjectID() {
        return objectID;
    }

    public void addEvent(IEvent event) throws UserIsNotThisKindOfMemberException, SQLException, UserInformationException, NoConnectionException, NoPermissionException {
        events.add(event);
        new EventLoggersDAL().update(this);
    }

    public List<IEvent> getEvents() {
        return events;
    }


    @Override
    public String toString() {
        return "Event_Logger{" +
                "events=" + events +
//                ", game=" + game +
                '}';
    }
}
