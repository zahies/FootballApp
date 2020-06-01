package Domain.Events;

import Domain.Users.Player;

import java.util.UUID;

public class Substitution extends AGameEvent implements IEvent {
    Player goesOut;
    Player goesIn;


    public Substitution(double gameMinute, Player in, Player out) {
        super(gameMinute);
        this.goesIn = in;
        this.goesOut = out;
    }

    /***FOR DB SELECT
     * @param gameMinute
     * @param playerWhocommit
     * @param objectID*/
    public Substitution(double gameMinute, Player playerWhocommit, UUID objectID, Player goesOut, Player goesIn, Event_Logger ev) {
        super(gameMinute, playerWhocommit, objectID,ev);
        this.goesOut = goesOut;
        this.goesIn = goesIn;
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

    @Override
    public String toString() {
        return (getClass().getName());
    }
}
