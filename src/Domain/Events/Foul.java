package Domain.Events;

import Domain.Users.Player;

import java.util.UUID;

public class Foul extends AGameEvent implements IEvent {

    public Foul(double gameMinute, Player playerWhocommit) {
        super(gameMinute, playerWhocommit);
    }

    public Foul(double gameMinute) {
        super(gameMinute);
    }

    /***FOR DB SELECT
     * @param gameMinute
     * @param playerWhocommit
     * @param objectID*/
    public Foul(double gameMinute, Player playerWhocommit, UUID objectID,Event_Logger event_logger) {
        super(gameMinute, playerWhocommit, objectID,event_logger);

    }

    @Override
    public String toString() {
        return (getClass().getName());
    }
}
