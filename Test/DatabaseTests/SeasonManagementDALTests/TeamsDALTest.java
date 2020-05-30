package DatabaseTests.SeasonManagementDALTests;

import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.SeasonManagmentDAL.TeamsDAL;
import DataAccess.UsersDAL.CommissionersDAL;
import Domain.FootballManagmentSystem;
import Domain.SeasonManagment.Team;
import Domain.Users.Commissioner;
import Domain.Users.Player;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import org.junit.Test;

import java.sql.Date;
import java.sql.SQLException;

public class TeamsDALTest {

    @Test
    public void insert() throws mightBeSQLInjectionException, DuplicatedPrimaryKeyException, NoPermissionException, SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException {
        //Commissioner commissioner = new CommissionersDAL().select("Glevi23",true);
        //Player player = new Player("RB7","Robert Pires",0,"E31@%c",758881,"Central Midfielder",new Date(1973,10,29));
        FootballManagmentSystem system = FootballManagmentSystem.getInstance();
        System.out.println("AFSAF");
    }
    @Test
    public void select() throws UserIsNotThisKindOfMemberException, SQLException, UserInformationException, NoConnectionException, NoPermissionException {
        Team team = new TeamsDAL().select("8c408231-664c-4b6a-ab91-e837ac913ef7",true);
        System.out.println("DASD");
    }
}
