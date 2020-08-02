package Domain.SeasonManagment;

import Domain.Users.Fan;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ComplaintFormTest {

    ComplaintForm complaintForm;
    Fan fan;

    @Before
    public void init(){
        complaintForm = new ComplaintForm("Foul");
        fan = new Fan("Noale", "Noa", 7676, "N7O6");

    }


    @Test
    public void setResponse() {
        String response="Apolo";
        complaintForm.setResponse(response);
        String ans = complaintForm.getResponse();
        assertEquals(response,ans);
    }

    @Test
    public void getFanSubmitingForm() {
        complaintForm.setFanSubmitingForm(fan);
        boolean flag = false;
        if (complaintForm.getFanSubmitingForm().equals(fan))
            flag = true;
        assertTrue(flag);
    }

    @Test
    public void getComplaint() {
    }

    @Test
    public void getResponse() {
    }

    @Test
    public void setFanSubmitingForm() {
    }

    @Test
    public void setComplaint() {
        String complaint="Apolo";
        complaintForm.setComplaint(complaint);
        String ans = complaintForm.getComplaint();
        assertEquals(complaint,ans);
    }
}