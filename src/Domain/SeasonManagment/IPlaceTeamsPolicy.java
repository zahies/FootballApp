package Domain.SeasonManagment;

import java.util.UUID;

public interface IPlaceTeamsPolicy {

    public int numOfGamesWithEachTeam();        /**must be even num*/
    public UUID getId();
}
