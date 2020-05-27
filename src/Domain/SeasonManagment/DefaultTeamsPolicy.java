package Domain.SeasonManagment;

import java.util.UUID;

public class DefaultTeamsPolicy implements IPlaceTeamsPolicy {

    UUID id;

    public DefaultTeamsPolicy() {
        id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    @Override
    public int numOfGamesWithEachTeam() {  /**must be even num*/
        return 2;
    }
}
