package Domain.Alerts;

import java.util.UUID;

public class FinancialAlert implements IAlert {

    int minus;
    private UUID objectID;
    private boolean hadSent;

    public FinancialAlert(int minus) {

        objectID = UUID.randomUUID();
        this.minus = minus;
        hadSent = false;
    }

    public UUID getObjectID() {
        return objectID;
    }

    @Override
    public String toString() {
        return "minus=" + minus;
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


    public boolean isHadSent() {
        return hadSent;
    }

    public void setHadSent(boolean hadSent) {
        this.hadSent = hadSent;
    }
}
