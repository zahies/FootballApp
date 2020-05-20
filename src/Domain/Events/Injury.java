package Domain.Events;

import Domain.Users.Player;

public class Injury extends AGameEvent implements IEvent {
    public Injury(double gameMinute, Player playerWhocommit) {
        super(gameMinute, playerWhocommit);
    }

    public Injury(double gameMinute) {
        super(gameMinute);
    }
}
