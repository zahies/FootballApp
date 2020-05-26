package Domain.Alerts;

import Domain.SeasonManagment.ComplaintForm;

public class ComplaintAlert implements IAlert {

    ComplaintForm response;

    public ComplaintAlert(ComplaintForm response) {
        this.response = response;
    }

    public ComplaintForm getComplaintResponse() {
        return response;
    }


    @Override
    public String view() {
        return this.toString();
    }

    @Override
    public String toString() {
        return "response = " + response +
                " }";
    }
}
