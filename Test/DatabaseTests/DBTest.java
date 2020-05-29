package DatabaseTests;

import DataAccess.Exceptions.NoConnectionException;
import DataAccess.SeasonManagmentDAL.TeamsDAL;
import DataAccess.UsersDAL.PlayersDAL;
import DataAccess.UsersDAL.TeamOwnersDAL;
import Domain.SeasonManagment.Team;
import Domain.Users.Player;
import Domain.Users.TeamOwner;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import org.junit.Test;

import java.sql.SQLException;

public class DBTest {

    @Test
    public void select() throws UserIsNotThisKindOfMemberException, SQLException, UserInformationException, NoConnectionException, NoPermissionException {

    }
}
