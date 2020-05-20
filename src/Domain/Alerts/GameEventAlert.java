package Domain.Alerts;

import Domain.Events.IEvent;

public class GameEventAlert implements IAlert {

    double eventMin;
    IEvent event;

    public GameEventAlert(double eventMin, IEvent event) {
        this.eventMin = eventMin;
        this.event = event;
    }

    @Override
    public String view() {
        return this.toString();
    }

    @Override
    public String toString() {
        return "GameEventAlert{" +
                "event minute = " + eventMin + ", event Type = " + event.getClass().getSimpleName() +
                " }";
    }
}
