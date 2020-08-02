package Domain.SeasonManagment;

import DataAccess.SeasonManagmentDAL.ComplaintFormsDAL;
import Domain.FootballManagmentSystem;
import Domain.Users.Fan;

public class ComplaintForm {

    private int objectID;
    private Fan fanSubmittingForm;
    private String complaint;
    private String response;

    public ComplaintForm(String complaint) {
        this.complaint = complaint;
        this.response = null;
        objectID = FootballManagmentSystem.getInstance().idGenerator(new ComplaintFormsDAL(), "complaintforms", "objectID");
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Fan getFanSubmitingForm() {
        return fanSubmittingForm;
    }

    public String getComplaint() {
        return complaint;
    }

    public String getResponse() {
        return response;
    }

    public void setFanSubmitingForm(Fan fanSubmitingForm) {
        this.fanSubmittingForm = fanSubmitingForm;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public int getObjectID() {
        return objectID;
    }
}
