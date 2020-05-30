package SpringControllers;

import Domain.FootballManagmentSystem;
import Domain.Users.Commissioner;
import Domain.Users.Member;
import Domain.Users.TeamOwner;
import org.junit.Before;

import static org.junit.Assert.*;

public class MemberControllerTest {

    FootballManagmentSystem system = FootballManagmentSystem.getInstance();
    private MemberController memberController;
    TeamOwner teamowner;
    Member com;



    @Before
    public void init(){
        //teamowner = new TeamOwner("Moshe","DASD",123,"asd");
        com = new Commissioner("zaza",12,"123","zahi zahi");
        memberController = new MemberController();
    }

    @org.junit.Test
    public void logOut() {
        assertEquals(2,system.getMembers().size());
    }
}