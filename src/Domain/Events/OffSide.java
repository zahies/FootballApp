package Domain.Events;

import Domain.Users.Player;

public class OffSide extends AGameEvent implements IEvent {
    public OffSide(double gameMinute, Player playerWhocommit) {
        super(gameMinute, playerWhocommit);
    }

    public OffSide(double gameMinute) {
        super(gameMinute);
    }

    @Override
    public String toString() {
        return (getClass().getName());
    }
}
