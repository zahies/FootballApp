package Domain.Events;

import Domain.Users.Player;

import java.util.UUID;

public class Goal extends AGameEvent implements IEvent {
    public Goal(double gameMinute, Player playerWhocommit) {
        super(gameMinute, playerWhocommit);
    }

    public Goal(double gameMinute) {
        super(gameMinute);
    }

    /***FOR DB SELECT
     * @param gameMinute
     * @param playerWhocommit
     * @param objectID*/
    public Goal(double gameMinute, Player playerWhocommit, UUID objectID, Event_Logger ev) {
        super(gameMinute, playerWhocommit, objectID,ev);
    }

    @Override
    public String toString() {
        return (getClass().getName());
    }
}
