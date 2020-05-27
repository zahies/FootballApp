package Domain.Events;

import Domain.Users.Player;

public class Substitution extends AGameEvent implements IEvent {
    Player goesOut;
    Player goesIn;


    public Substitution(double gameMinute, Player in, Player out) {
        super(gameMinute);
        this.goesIn = in;
        this.goesOut = out;
    }

    public Player getGoesOut() {
        return goesOut;
    }

    public Player getGoesIn() {
        return goesIn;
    }

    public Substitution(double gameMinute) {
        super(gameMinute);
    }
}
