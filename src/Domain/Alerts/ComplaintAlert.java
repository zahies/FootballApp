package Domain.Alerts;

import Domain.SeasonManagment.ComplaintForm;

import java.util.UUID;

public class ComplaintAlert implements IAlert {

    UUID objectID;
    ComplaintForm response;


    public ComplaintAlert(ComplaintForm response) {
        objectID = UUID.randomUUID();
        this.response = response;
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
        return "response = " + response +
                " }";
    }
}
