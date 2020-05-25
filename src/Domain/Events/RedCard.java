package Domain.Events;

import Domain.Users.Player;

public class RedCard extends AGameEvent implements IEvent {
    public RedCard(double gameMinute, Player playerWhocommit) {
        super(gameMinute, playerWhocommit);
    }

    public RedCard(double gameMinute) {
        super(gameMinute);
    }

    @Override
    public String toString() {
        return (getClass().getName());
    }
}
