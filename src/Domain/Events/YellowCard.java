package Domain.Events;

import Domain.Users.Player;

public class YellowCard extends AGameEvent implements IEvent {
    public YellowCard(double gameMinute, Player playerWhocommit) {
        super(gameMinute, playerWhocommit);
    }

    public YellowCard(double gameMinute) {
        super(gameMinute);
    }


    @Override
    public String toString() {
        return (getClass().getName());
    }
}
