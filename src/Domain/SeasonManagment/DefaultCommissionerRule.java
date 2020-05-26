package Domain.SeasonManagment;

public class DefaultCommissionerRule implements ICommissionerRule {

    /**
     * when team is open there is a tax of 100$
     */


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
