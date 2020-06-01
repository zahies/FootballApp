package Domain.Alerts;

import DataAccess.AlertsDAL.GameEventAlertsDAL;
import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import Domain.Events.IEvent;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;

import java.sql.SQLException;
import java.util.UUID;

public class GameEventAlert implements IAlert {

    private UUID objectID;
    double eventMin;
    IEvent event;
    private boolean hadSent;

    public GameEventAlert(UUID objectID, double eventMin, IEvent event, boolean hadSent) {
        this.objectID = objectID;
        this.eventMin = eventMin;
        this.event = event;
        this.hadSent = hadSent;
    }

    public GameEventAlert(double eventMin, IEvent event) throws mightBeSQLInjectionException, DuplicatedPrimaryKeyException, NoPermissionException, SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException {
        this.eventMin = eventMin;
        this.event = event;
        objectID = UUID.randomUUID();
        hadSent = false;
    }

    public UUID getObjectID() {
        return objectID;
    }

    public double getEventMin() {
        return eventMin;
    }

    public IEvent getEvent() {
        return event;
    }

    @Override
    public String view() {
        return this.toString();
    }

    @Override
    public String getType() {
        return "Game Event Alert";
    }

    @Override
    public String toString() {
        return "event minute = " + eventMin + ", event Type = " + event.getClass().getSimpleName();
    }
    @Override
    public void setNewID() {
        objectID = UUID.randomUUID();
    }

    public boolean isHadSent() {
        return hadSent;
    }

    public void setHadSent(boolean hadSent) {
        this.hadSent = hadSent;
    }
}
