package Domain.Events;

import Domain.SeasonManagment.Game;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Event_Logger {

    private UUID objectID;
    public List<IEvent> events;



    public Event_Logger() {
        events = new LinkedList<>();
        objectID = UUID.randomUUID();
    }

    public UUID getObjectID() {
        return objectID;
    }

    public void addEvent(IEvent event) {
        events.add(event);
    }

    public List<IEvent> getEvents() {
        return events;
    }


}
