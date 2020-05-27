package Domain.Events;

import Domain.Users.Player;

import java.util.Date;
import java.util.UUID;

public abstract class AGameEvent implements IEvent {

    double gameMinute;
    Player playerWhocommit;
    private UUID objectID;
    private Event_Logger event_logger;

    public Event_Logger getEvent_logger() {
        return event_logger;
    }

    public void setEvent_logger(Event_Logger event_logger) {
        this.event_logger = event_logger;
    }

    public AGameEvent(double gameMinute, Player playerWhocommit) {
        this.gameMinute = gameMinute;
        this.playerWhocommit = playerWhocommit;
        objectID = UUID.randomUUID();
    }

    public UUID getObjectID() {
        return objectID;
    }

    public AGameEvent(double gameMinute) {
        this.gameMinute = gameMinute;
    }

    public double getGameMinute() {
        return gameMinute;
    }

    public Player getPlayerWhocommit() {
        return playerWhocommit;
    }
}
