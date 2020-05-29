package DatabaseTests.SeasonManagementDALTests;

import DataAccess.Exceptions.NoConnectionException;
import DataAccess.SeasonManagmentDAL.TeamsDAL;
import Domain.SeasonManagment.Team;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import org.junit.Test;

import java.sql.SQLException;

public class TeamsDALTest {

    @Test
    public void select() throws UserIsNotThisKindOfMemberException, SQLException, UserInformationException, NoConnectionException, NoPermissionException {
        Team team = new TeamsDAL().select("a56598cc-07c3-49f8-a8d1-9b56682c647a",true);
        System.out.println("DASD");
    }
}
