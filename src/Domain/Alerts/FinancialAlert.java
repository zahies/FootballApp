package Domain.Alerts;

public class FinancialAlert implements IAlert {

    int minus;

    public FinancialAlert(int minus) {
        this.minus = minus;
    }


    @Override
    public String toString() {
        return "minus=" + minus +
                '}';
    }

    @Override
    public String view() {
        return this.toString();
    }
}
