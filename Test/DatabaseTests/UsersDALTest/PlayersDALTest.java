package DatabaseTests.UsersDALTest;

import DataAccess.Exceptions.NoConnectionException;
import DataAccess.UsersDAL.PlayersDAL;
import Domain.Users.Player;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

public class PlayersDALTest {

    @Test
    public void select() throws UserIsNotThisKindOfMemberException, SQLException, UserInformationException, NoConnectionException, NoPermissionException {
        Player player = new PlayersDAL().select("CR723",true);
        assertTrue(player.getName().equals("CR723"));
        assertTrue(player.getMyTeam().getName().equals("Beiter"));
    }
}
