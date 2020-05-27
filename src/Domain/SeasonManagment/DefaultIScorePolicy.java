package Domain.SeasonManagment;

import java.util.UUID;

public class DefaultIScorePolicy implements IScorePolicy {


    UUID id;

    public DefaultIScorePolicy() {
        id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    @Override
    public int winVal() {
        return 3;
    }

    @Override
    public int looseVal() {
        return 0;
    }

    @Override
    public int drowVal() {
        return 1;
    }
}
