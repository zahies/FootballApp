package Domain.Alerts;

import Domain.SeasonManagment.Game;

import java.util.Date;

public class ChangedGameAlert implements IAlert {

    private Date matchDate;

    private Game game;

    public ChangedGameAlert(Date matchDate, Game game) {
        this.matchDate = matchDate;
        this.game = game;
    }

    public Date getMatchDate() {
        return matchDate;
    }

    @Override
    public String toString() {
        return "matchDate=" + matchDate +
                ", game=" + game +
                '}';
    }

    @Override
    public String view() {
        return this.toString();
    }
}
