package Domain.Events;

import Domain.Users.Player;

import java.util.Date;

public abstract class AGameEvent implements IEvent {

    double gameMinute;
    Player playerWhocommit;


    public AGameEvent(double gameMinute, Player playerWhocommit) {
        this.gameMinute = gameMinute;
        this.playerWhocommit = playerWhocommit;
    }

    public AGameEvent(double gameMinute) {
        this.gameMinute = gameMinute;
    }

    public double getGameMinute() {
        return gameMinute;
    }

    public Player getPlayerWhocommit() {
        return playerWhocommit;
    }
}
