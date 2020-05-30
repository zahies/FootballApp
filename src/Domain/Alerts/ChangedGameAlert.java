package Domain.Alerts;

import Domain.SeasonManagment.Game;

import java.util.Date;
import java.util.UUID;

public class ChangedGameAlert implements IAlert {

    private UUID objectID;
    private Date matchDate;
    private Game game;
    private boolean hadSent;

    public ChangedGameAlert(UUID objectID, Date matchDate, Game game) {
        /**DB -SELECT*/

        this.objectID = objectID;
        this.matchDate = matchDate;
        this.game = game;
    }

    public ChangedGameAlert(Date matchDate, Game game) {
        this.matchDate = matchDate;
        this.game = game;
        objectID = UUID.randomUUID();
        hadSent = false;
    }

    public Date getMatchDate() {
        return matchDate;
    }

    public UUID getObjectID() {
        return objectID;
    }

    public Game getGame() {
        return game;
    }

    @Override
    public String toString() {
        return "matchDate=" + matchDate +
                ", game=" + game;
    }

    @Override
    public String view() {
        return this.toString();
    }

    @Override
    public String getType() {
        return  "Changed Game Alert";
    }

    public boolean isHadSent() {
        return hadSent;
    }

    public void setHadSent(boolean hadSent) {
        this.hadSent = hadSent;
    }
}
