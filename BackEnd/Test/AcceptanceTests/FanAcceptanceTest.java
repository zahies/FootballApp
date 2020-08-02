package AcceptanceTests;

import Domain.FootballManagmentSystem;
import Domain.Searcher.SearchByName;
import Domain.Searcher.Searcher;
import Domain.Users.Fan;
import Domain.Users.Guest;
import Domain.Users.PersonalInfo;
import Domain.Users.Player;
import FootballExceptions.UserInformationException;
import Service.FanController;
import Service.GuestController;
import org.junit.Test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class FanAcceptanceTest {
    private GuestController guestController;
    Guest guest;
    private GuestController guestController2;
    Guest guest2;
    private FanController fansC = new FanController();

    @Test
    public void positiveSearchTest() throws UserInformationException {
        FootballManagmentSystem fms = FootballManagmentSystem.getInstance();
        guest = new Guest();

        Player pla = new Player("Beckham222","David Beckham",4,"dff33",670,"attack",null);
        Player pla2 = new Player("Beckham4444","Asi Beckham",7,"dff33",670,"attack",null);
        guestController = new GuestController();
        guestController.register(guest ,"itamar15","itamar","1234",1,null);
        Searcher searcher = new SearchByName();
        Fan fan =(Fan)guest.login("itamar15","1234").get(0);
        HashSet<Object> p =fansC.search(fan,"David Beckham",searcher );
        Player player2check = (Player) p.iterator().next();
        assertEquals("David Beckham",player2check.getReal_Name());

    }
    @Test
    public void negativeSearchTest() throws UserInformationException {
        FootballManagmentSystem fms = FootballManagmentSystem.getInstance();
        guest = new Guest();

        Player pla = new Player("Beckham21","David Beckham",4,"dff33",670,"attack",null);
        Player pla2 = new Player("Beckham41","Asi Beckham",7,"dff33",670,"attack",null);
        guestController = new GuestController();
        guestController.register(guest ,"itamar1","itamar","1234",1,null);
        Searcher searcher = new SearchByName();
        Fan fan =(Fan)guest.login("itamar1","1234").get(0);
        assertNull(fansC.viewSearchHistory(fan));

    }
    @Test
    public void positiveFollowPage() throws UserInformationException {
        FootballManagmentSystem fms = FootballManagmentSystem.getInstance();
        guest = new Guest();

        Player pla = new Player("Beckham2","David Beckham",4,"dff33",670,"attack",null);
        Player pla2 = new Player("Beckham4","Asi Beckham",7,"dff33",670,"attack",null);
        guestController = new GuestController();
        guestController.register(guest ,"itamar120","itamar","1234",1,null);
        Searcher searcher = new SearchByName();
        Fan fan =(Fan)guest.login("itamar120","1234").get(0);
        pla.createPersonalPage();
        fansC.addPersonalPagesToFollow(fan, fms.getPersonalPages());
        PersonalInfo pi = fan.getPersonalPagesFollowed().keySet().iterator().next();

        System.out.println(fan.getPersonalPagesFollowed().size());
//        assertTrue(pi.isPageOwner(pla));
        assertTrue(fan.getPersonalPagesFollowed().size()==3);

    }
    @Test
    public void negativeFollowPage() throws UserInformationException {

        Player pla = new Player("Beckham2213","David Beckham",4,"dff33",670,"attack",null);
        pla.createPersonalPage();
        FanController fanController = new FanController();
        Fan fan412 = new Fan ("Wenger", "arsne wneger", 2314, "#!@$124");
        List<PersonalInfo> pagesToFooolw = new LinkedList<>();
        pagesToFooolw.add(pla.getInfo());

        fanController.addPersonalPagesToFollow(fan412,pagesToFooolw);
        assertFalse(fanController.addPersonalPagesToFollow(fan412,pagesToFooolw));
//        FootballManagmentSystem fms = FootballManagmentSystem.getInstance();
//        guest = new Guest();
//
//        Player pla = new Player("Beckham20","David Beckham",4,"dff33",670,"attack",null);
//        Player pla2 = new Player("Beckham40","Asi Beckham",7,"dff33",670,"attack",null);
//        guestController = new GuestController();
//        guestController.register(guest ,"itamar1201","itamar","1234",1,null);
//        Searcher searcher = new SearchByName();
//        Fan fan =(Fan)guest.login("itamar1201","1234").get(0);
//        pla.createPersonalPage();
//        fansC.addPersonalPagesToFollow(fan, fms.getPersonalPages());
//        fansC.addPersonalPagesToFollow(fan, fms.getPersonalPages());

    }
}
