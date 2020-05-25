package Domain.Events;

import Domain.Users.Player;

public class Goal extends AGameEvent implements IEvent {
    public Goal(double gameMinute, Player playerWhocommit) {
        super(gameMinute, playerWhocommit);
    }

    public Goal(double gameMinute) {
        super(gameMinute);
    }


    @Override
    public String toString() {
        return (getClass().getName());
    }
}
