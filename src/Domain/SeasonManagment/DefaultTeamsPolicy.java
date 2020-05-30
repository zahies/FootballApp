package Domain.SeasonManagment;

import java.util.UUID;

public class DefaultTeamsPolicy implements IPlaceTeamsPolicy {

    UUID id;
    int numOfGamesWithEachTeam =2;

    public DefaultTeamsPolicy(UUID id, int numOfGamesWithEachTeam) {
        this.id = id;
        this.numOfGamesWithEachTeam = numOfGamesWithEachTeam;
    }

    public DefaultTeamsPolicy() {
        id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    @Override
    public int numOfGamesWithEachTeam() {  /**must be even num*/
        return numOfGamesWithEachTeam;
    }
}
