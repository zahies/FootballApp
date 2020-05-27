package Domain.SeasonManagment;

import java.util.UUID;

public class DefaultCommissionerRule implements ICommissionerRule {

    /**
     * when team is open there is a tax of 100$
     */
    private UUID id;

    public DefaultCommissionerRule() {
        id = UUID.randomUUID();
    }

    public UUID getObjectID() {
        return id;
    }

    @Override
    public int payRule() {
        return 100;
    }

    @Override
    public String toString() {
        return "Default Commissioner Rule - Opening Tax";
    }
}
