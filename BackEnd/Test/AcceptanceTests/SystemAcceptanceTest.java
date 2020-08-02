package AcceptanceTests;

import Domain.FootballManagmentSystem;
import Domain.PersonalPages.ProfileContent;
import Domain.Users.Coach;
import Domain.Users.CoachRole;
import Domain.Users.Guest;
import Domain.Users.PersonalInfo;
import FootballExceptions.UserInformationException;
import Service.GuestController;
import org.junit.Before;
import org.junit.Test;

import java.lang.annotation.Repeatable;

import static org.junit.Assert.*;

public class SystemAcceptanceTest {


   private FootballManagmentSystem fms;
   private Guest guest;
   private GuestController guestController;


    @Before
    public void init(){
        fms = FootballManagmentSystem.getInstance();
        guest = new Guest();
        guestController = new GuestController();
        if(fms.getMemberByUserName("itamar12") == null)
            guestController.register(guest, "itamar12", "itamar", "1234", 1, null);

    }



    @Test
    public void PositiveChecklogin() throws UserInformationException {

        guestController.login(guest,"itamar12","1234");
        assertTrue(fms.getMemberByUserName("itamar12").get(0).isActive());

    }
    @Test
    public void NegativeChecklogin() throws UserInformationException {

        guestController.login(guest,"itamar13","1234");
        assertFalse(fms.getMemberByUserName("itamar12").get(0).isActive());

    }
    @Test
    public void Positiveintialization(){
        assertEquals(1,fms.getAllInCharge().size());
        assertEquals("aviluzon",fms.getAllInCharge().get(0).getName());

    }
}
