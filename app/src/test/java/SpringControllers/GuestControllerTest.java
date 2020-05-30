package SpringControllers;

import Domain.Users.Commissioner;
import FootballExceptions.IDWasNotEnterdException;
import FootballExceptions.LeagueIDAlreadyExist;
import FootballExceptions.UserInformationException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GuestControllerTest {

    Commissioner commissioner;
    GuestController guestController;
    String name;
    String password;
    int id;


    @Before
    public void init() throws LeagueIDAlreadyExist, IDWasNotEnterdException {
        name = "Alon";
        id = 222;
        password = "223";
        commissioner = new Commissioner(name, id, password, "Tzahi");
        guestController = new GuestController();
    }

    @Test
    public void login() throws UserInformationException {
        String ans;
        ans = guestController.login(name,password);
        assertNotNull(ans); //fixme
    }
}