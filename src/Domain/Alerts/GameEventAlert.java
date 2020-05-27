package Domain.Alerts;

import Domain.Events.IEvent;

import java.util.UUID;

public class GameEventAlert implements IAlert {

    private UUID objectID;
    double eventMin;
    IEvent event;

    public GameEventAlert(double eventMin, IEvent event) {
        this.eventMin = eventMin;
        this.event = event;
        objectID = UUID.randomUUID();
    }

    public UUID getObjectID() {
        return objectID;
    }

    public double getEventMin() {
        return eventMin;
    }

    public IEvent getEvent() {
        return event;
    }

    @Override
    public String view() {
        return this.toString();
    }

    @Override
    public String getType() {
        return "Game Event Alert";
    }

    @Override
    public String toString() {
        return "GameEventAlert{" +
                "event minute = " + eventMin + ", event Type = " + event.getClass().getSimpleName() +
                " }";
    }
}
