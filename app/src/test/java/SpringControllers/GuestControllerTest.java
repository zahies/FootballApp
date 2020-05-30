package SpringControllers;

import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import Domain.Users.Commissioner;
import FootballExceptions.*;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class GuestControllerTest {

    Commissioner commissioner;
    GuestController guestController;
    String name;
    String password;
    int id;


    @Before
    public void init() throws LeagueIDAlreadyExist, IDWasNotEnterdException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException, NoPermissionException, SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException {
        name = "Alon";
        id = 222;
        password = "223";
        commissioner = new Commissioner(name, id, password, "Tzahi");
        guestController = new GuestController();
    }

    @Test
    public void login() throws UserInformationException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException, NoPermissionException, SQLException, UserIsNotThisKindOfMemberException, NoConnectionException {
        String ans;
        ans = guestController.login(name,password);
        assertNotNull(ans); //fixme
    }
}