package Domain.SeasonManagment;

import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.SeasonManagmentDAL.PlaceTeamsPoliciesDAL;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;

import java.sql.SQLException;
import java.util.UUID;

public class DefaultTeamsPolicy implements IPlaceTeamsPolicy {

    UUID id;
    int numOfGamesWithEachTeam =2;

    public DefaultTeamsPolicy(UUID id, int numOfGamesWithEachTeam) {
        this.id = id;
        this.numOfGamesWithEachTeam = numOfGamesWithEachTeam;
    }

    public DefaultTeamsPolicy() throws mightBeSQLInjectionException, DuplicatedPrimaryKeyException, NoPermissionException, SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException {
        id = UUID.randomUUID();
        new PlaceTeamsPoliciesDAL().insert(this);
    }

    public UUID getId() {
        return id;
    }

    @Override
    public int numOfGamesWithEachTeam() {  /**must be even num*/
        return numOfGamesWithEachTeam;
    }
}
