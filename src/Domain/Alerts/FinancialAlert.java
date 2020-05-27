package Domain.Alerts;

import java.util.UUID;

public class FinancialAlert implements IAlert {

    int minus;
    private UUID objectID;

    public FinancialAlert(int minus) {

        objectID = UUID.randomUUID();
        this.minus = minus;
    }

    public UUID getObjectID() {
        return objectID;
    }

    @Override
    public String toString() {
        return "FinancialAlert{" +
                "minus=" + minus +
                '}';
    }

    public int getMinus() {
        return minus;
    }

    @Override
    public String view() {
        return this.toString();
    }

    @Override
    public String getType() {
        return "FinancialAlert";
    }
}
