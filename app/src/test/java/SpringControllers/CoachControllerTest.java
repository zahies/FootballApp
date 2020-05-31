package SpringControllers;

import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.UserInformationDAL.PersonalPageContentDAL;
import DataAccess.UsersDAL.CoachesDAL;
import Domain.PersonalPages.APersonalPageContent;
import Domain.PersonalPages.ProfileContent;
import Domain.Users.Coach;
import Domain.Users.CoachRole;
import Domain.Users.Member;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;


public class CoachControllerTest {

    CoachController coachController;
    Coach coach;
    ProfileContent profileContent;


    @Before
    public void init() throws mightBeSQLInjectionException, DuplicatedPrimaryKeyException, NoPermissionException, SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException {
        String username = "Shlomi";
        //Member coachToInsert = new Coach(username, "noa", 312427404, "noanoa123", 1, null, CoachRole.HeadCoach);
        //new CoachesDAL().insert((Coach) coachToInsert);
        //coach = (Coach) new CoachesDAL().select(username,true);
        profileContent = new ProfileContent();


      //  cont = (APersonalPageContent)new PersonalPageContentDAL().select(contString);

    }



    @Test
    public void addContentToPage() {
    }

    @Test
    public void editPage() {
    }

    @Test
    public void changeUserName() {
    }

    @Test
    public void createPersonalPage() {
    }
}