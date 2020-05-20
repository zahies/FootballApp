package Domain.Users;

import Domain.FootballManagmentSystem;
import Domain.Searcher.SearchByName;
import FootballExceptions.UserInformationException;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.*;

public class GuestTest {
    private Guest guestTest;
    private Player p;
    private FootballManagmentSystem system;
    private PersonalInfo info;
    private SearchByName byName;

    @Before
    public void init(){
        guestTest = new Guest();
        p = new Player("Gadi33","Gadi",344,"234",3,null,null);
        p.createPersonalPage();
        system = FootballManagmentSystem.getInstance();
        info = new PersonalInfo(p);
        byName = new SearchByName();

    }

    @Test
    public void register() throws UserInformationException {
        boolean ans= guestTest.register("noale","Noa","1234",1234,null);
        assertTrue(ans);
    }

    @Test
    public void login() throws UserInformationException {
        if(!(system.getMembers().containsKey("noale"))) {
            register();
        }
        Member m;
        m = guestTest.login("noale","1234").get(0);
        assertNotNull(m);

    }

    @Test
    public void view() {
        guestTest.view(info);
    }

    @Test
    public void search() {
        HashSet<Object> result = new HashSet<>();
        result = guestTest.search("Gadi",byName);
        assertEquals(1,result.size());

    }
}