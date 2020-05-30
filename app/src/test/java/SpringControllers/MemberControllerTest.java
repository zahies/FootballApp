package SpringControllers;

import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import Domain.FootballManagmentSystem;
import Domain.Users.Commissioner;
import Domain.Users.Member;
import Domain.Users.TeamOwner;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import org.junit.Before;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class MemberControllerTest {

    FootballManagmentSystem system = FootballManagmentSystem.getInstance();
    private MemberController memberController;
    TeamOwner teamowner;
    Member com;



    @Before
    public void init() throws mightBeSQLInjectionException, DuplicatedPrimaryKeyException, NoPermissionException, SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException {
        //teamowner = new TeamOwner("Moshe","DASD",123,"asd");
        com = new Commissioner("zaza",12,"123","zahi zahi");
        memberController = new MemberController();
    }

    @org.junit.Test
    public void logOut() {
        assertEquals(2,system.getMembers().size());
    }
}