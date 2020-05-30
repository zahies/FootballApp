package Domain.SeasonManagment;

import java.util.UUID;

public class DefaultIScorePolicy implements IScorePolicy {


    UUID id;

    int win=3;
    int lose=0;
    int draw=1;

    public DefaultIScorePolicy(UUID id, int win, int lose, int draw) {
        this.id = id;
        this.win = win;
        this.lose = lose;
        this.draw = draw;
    }

    public DefaultIScorePolicy() {
        id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    @Override
    public int winVal() {
        return win;
    }

    @Override
    public int looseVal() {

        return lose;
    }

    @Override
    public int drowVal() {
        return draw;
    }
}
