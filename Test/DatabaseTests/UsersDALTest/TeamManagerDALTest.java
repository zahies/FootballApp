package DatabaseTests.UsersDALTest;

import DataAccess.Exceptions.NoConnectionException;
import DataAccess.UsersDAL.TeamManagerDAL;
import Domain.Users.TeamManager;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import org.junit.Test;

import java.sql.SQLException;

public class TeamManagerDALTest {

    @Test
    public void select() throws UserIsNotThisKindOfMemberException, SQLException, UserInformationException, NoConnectionException, NoPermissionException {
        TeamManager teamManager = new TeamManagerDAL().select("CR723",true);
        System.out.println("FSAF");
    }
}
