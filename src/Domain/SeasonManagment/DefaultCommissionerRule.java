package Domain.SeasonManagment;

import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.SeasonManagmentDAL.ICommissionerRulesDAL;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;

import java.sql.SQLException;
import java.util.UUID;

public class DefaultCommissionerRule implements ICommissionerRule {

    /**
     * when team is open there is a tax of 100$
     */
    private UUID id;
    private String Description;

    public DefaultCommissionerRule() {
        id = UUID.randomUUID();
    }

    public DefaultCommissionerRule(UUID id, String description) throws NoPermissionException, SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException {
        this.id = id;
        Description = description;
        new ICommissionerRulesDAL().insert(this);
    }

    public UUID getObjectID() {
        return id;
    }

    @Override
    public int payRule() {
        return 100;
    }

    @Override
    public String getDescription() {
        return toString();
    }

    @Override
    public String toString() {
        return "Default Commissioner Rule - Opening Tax";
    }
}
