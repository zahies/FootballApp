package Domain.Users;

import Domain.PersonalPages.ProfileContent;
import Domain.SeasonManagment.Team;
import FootballExceptions.PersonalPageYetToBeCreatedException;
import FootballExceptions.UnauthorizedPageOwnerException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CoachTest {
    private Coach coach;
    private Coach coach2;;
    private PersonalInfo info;
    private ProfileContent profileContent;
    @Before
    public void init(){
        coach = new Coach("noa12","noa",312427404,"noanoa123",1,null, CoachRole.HeadCoach);
        info = new PersonalInfo(coach);
        coach.setInfo(info);
        coach2 = new Coach("shira33","shira",313546448,"noanoa123",2,null,CoachRole.FirstAssistantCoach);
        profileContent = new ProfileContent();
    }
    @Test
    public void createPersonalPage() {
            int id= coach.getInfo().getPageID();
            coach.createPersonalPage();
            int id2= coach.getInfo().getPageID();
            assertNotEquals(id2,id);

    }
    @Test
    public void addContentToPersonalPage() {
        boolean ans= false;
        try {
            ans = coach.addContentToPersonalPage(profileContent);
        } catch (UnauthorizedPageOwnerException e) {
            e.printStackTrace();
        } catch (PersonalPageYetToBeCreatedException e) {
            e.printStackTrace();
        }
        assertTrue(ans);

    }

    @Test(expected = PersonalPageYetToBeCreatedException.class)
    public void addContentToPersonalPage2() throws UnauthorizedPageOwnerException, PersonalPageYetToBeCreatedException {
        coach2.addContentToPersonalPage(profileContent);

    }


    @Test
    public void editProfile() throws UnauthorizedPageOwnerException, PersonalPageYetToBeCreatedException {
        coach.addContentToPersonalPage(profileContent);
        boolean ans = coach.editProfile("height","1.80");
        assertTrue(ans);
    }

    @Test(expected =PersonalPageYetToBeCreatedException.class )
    public void editProfile2() throws UnauthorizedPageOwnerException, PersonalPageYetToBeCreatedException {
        boolean ans = false;
        ans = coach2.editProfile("height","1.80");
        assertFalse(ans);
    }


    @Test
    //+getTeam
    public void setMyTeam() {
        Team t = coach.getMyTeam();
        coach.setMyTeam(t);
        Team realTeam = coach.getMyTeam();
        assertEquals(t,realTeam);

    }

    @Test
    //+getTraninig
    public void setTraining() {
        coach.setTraining("diploma");
        assertEquals("diploma",coach.getTraining());
    }

    @Test
    //+getRole
    public void setRole() {
        coach.setRole(CoachRole.FitnessCoach);
        assertEquals(CoachRole.FitnessCoach,coach.getRole());
    }

    @Test
    //+getInfo
    public void setInfo() {
        PersonalInfo info2 = new PersonalInfo(coach);
        coach.setInfo(info2);
        assertEquals(info2.getPageID(),coach.getInfo().getPageID());
    }

    @Test
    //+getValue
    public void edit() {
        coach.edit(5);
        assertEquals(5, coach.getValue());
    }
}