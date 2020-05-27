package Domain.Events;

import Domain.Users.Player;

import java.util.Date;
import java.util.UUID;

public interface IEvent {

    public Player getPlayerWhocommit();
    public UUID getObjectID();
    public Event_Logger getEvent_logger();
    public double getGameMinute();

}
