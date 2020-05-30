package Domain.Events;

import Domain.Users.Player;

import java.util.UUID;

public class RedCard extends AGameEvent implements IEvent {
    public RedCard(double gameMinute, Player playerWhocommit) {
        super(gameMinute, playerWhocommit);
    }

    public RedCard(double gameMinute) {
        super(gameMinute);
    }

    /***FOR DB SELECT
     * @param gameMinute
     * @param playerWhocommit
     * @param objectID*/
    public RedCard(double gameMinute, Player playerWhocommit, UUID objectID) {
        super(gameMinute, playerWhocommit, objectID);
    }

    @Override
    public String toString() {
        return (getClass().getName());
    }
}
