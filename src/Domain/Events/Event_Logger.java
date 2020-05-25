package Domain.Events;

import Domain.SeasonManagment.Game;

import java.util.LinkedList;
import java.util.List;

public class Event_Logger {

    public List<IEvent> events;
    Game game;


    public Event_Logger() {
        events = new LinkedList<>();

    }

    public void addEvent(IEvent event) {
        events.add(event);
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
