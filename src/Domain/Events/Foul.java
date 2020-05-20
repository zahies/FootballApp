package Domain.Events;

import Domain.Users.Player;

public class Foul extends AGameEvent implements IEvent {

    public Foul(double gameMinute, Player playerWhocommit) {
        super(gameMinute, playerWhocommit);
    }

    public Foul(double gameMinute) {
        super(gameMinute);
    }
}
