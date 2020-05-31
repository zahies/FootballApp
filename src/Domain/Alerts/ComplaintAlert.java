package Domain.Alerts;

import Domain.SeasonManagment.ComplaintForm;

import java.util.UUID;

public class ComplaintAlert implements IAlert {

    UUID objectID;
    ComplaintForm response;
    private boolean hadSent;


    public ComplaintAlert(ComplaintForm response) {
        objectID = UUID.randomUUID();
        this.response = response;
        hadSent = false;
    }

    public ComplaintForm getComplaintResponse() {
        return response;
    }

    public UUID getObjectID() {
        return objectID;
    }

    @Override
    public String view() {
        return this.toString();
    }

    @Override
    public String getType() {
        return "Complaint Alert";
    }

    @Override
    public String toString() {
        return "response = " + response;
    }


    public boolean isHadSent() {
        return hadSent;
    }

    @Override
    public void setNewID() {
        objectID = UUID.randomUUID();
    }
    public void setHadSent(boolean hadSent) {
        this.hadSent = hadSent;
    }
}
