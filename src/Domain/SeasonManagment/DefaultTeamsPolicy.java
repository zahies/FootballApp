package Domain.SeasonManagment;

public class DefaultTeamsPolicy implements IPlaceTeamsPolicy {

    @Override
    public int numOfGamesWithEachTeam() {  /**must be even num*/
        return 2;
    }
}
