package AcceptanceTests;
import Domain.FootballManagmentSystem;
import Domain.Users.Guest;
import Service.GuestController;
import org.junit.Test;

import static org.junit.Assert.*;
public class GuestAcceptanceTest {
    private GuestController guestController;
    Guest guest;
    private GuestController guestController2;
    Guest guest2;
    @Test
    public void positiveRegistarionTest(){
        guest = new Guest();
        guestController = new GuestController();
        guestController.register(guest ,"itamar123","itamar","1234",1,null);
        assertEquals("itamar123",FootballManagmentSystem.getInstance().getMemberByUserName("itamar123").get(0).getName());

    }
    @Test
    public void negativeRegistrationTest(){
        guest = new Guest();
        guestController = new GuestController();
        guestController.register(guest ,"itamar123","itamar","1234",1,null);
        guest = new Guest();
        guestController2 = new GuestController();
        guestController.register(guest ,"itamar123","itamar","7234",5,null);
    }
}
