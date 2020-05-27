package AcceptanceTests;

import Domain.Alerts.ComplaintAlert;
import Domain.Alerts.TeamManagementAlert;
import Domain.FootballManagmentSystem;
import Domain.SeasonManagment.ComplaintForm;
import Domain.SeasonManagment.Game;
import Domain.SeasonManagment.Team;
import Domain.SeasonManagment.TeamStatus;
import Domain.Users.*;
import FootballExceptions.UserInformationException;
import Service.GuestController;
import Service.SystemManagerController;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class SystemManagerAcceptanceTest {

    private FootballManagmentSystem fms;
    private Guest guest;
    private Team team;
    private TeamManager teamManager;
    private TeamOwner teamOwner;
    private GuestController guestController;
    SystemManager systemManager ;
    Referee ref2;
    Referee ref;
    SystemManagerController systemManagerC ;

    @Before
    public void init() throws UserInformationException {

        fms = FootballManagmentSystem.getInstance();
        teamOwner = new TeamOwner("gabi120","avishai",2,"12345");
        team = new Team("Hapoel Haifa",teamOwner);
        fms.addTeam(team);
        //fms.addMember(teamOwner);
        teamOwner.setTeam(team);
        guest = new Guest();
        ref = new Referee("ref1","theref",4,"3232",RefereeType.Main);
        ref2 = new Referee("ref2","theref2",5,"3232",RefereeType.Secondary);
        systemManager = (SystemManager) fms.getMemberByUserName("aviluzon").get(0);
        guestController = new GuestController();
        if(fms.getMemberByUserName("itamar1200") == null)
            guestController.register(guest, "itamar1200", "itamar", "1234", 1, null);
        systemManagerC = new SystemManagerController();

    }
    @Test
    public void PositiveCloseTeam(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(System.currentTimeMillis()));
        cal.add(Calendar.HOUR_OF_DAY, 6); // adds 6 hours
        long time = ((new Date(System.currentTimeMillis()))).getTime();

        Game game = new Game(null,team,cal.getTime(),ref,ref2,null);
        team.addGameToUpcomingGames(game);
        systemManagerC.closeTeam(systemManager,team,"signed kids from Uganda");
        TeamManagementAlert alert=(TeamManagementAlert) teamOwner.getAlertsList().peek();
        assertFalse(team.isActive());
        assertTrue(team.getStatus().equals(TeamStatus.Close));

    }
    @Test
    public void NegativeClostTeam(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(System.currentTimeMillis()));
        cal.add(Calendar.HOUR_OF_DAY, 1); // adds 6 hours
        long time = ((new Date(System.currentTimeMillis()))).getTime();

        Game game = new Game(null,team,cal.getTime(),ref,ref2,null);
        team.addGameToUpcomingGames(game);
        systemManagerC.closeTeam(systemManager,team,"signed kids from Uganda");
        TeamManagementAlert alert=(TeamManagementAlert) teamOwner.getAlertsList().peek();
        assertTrue(team.isActive());
        assertFalse(team.getStatus().equals(TeamStatus.Close));

    }
    @Test
    public void PositiveDeleteMember(){
        assertNotNull(fms.getMemberByUserName("itamar1200"));
        systemManager = (SystemManager) fms.getMemberByUserName("aviluzon").get(0);
        systemManagerC.deleteMember(systemManager,fms.getMemberByUserName("itamar1200").get(0));
        assertNull(fms.getMemberByUserName("itamar1200"));
    }
    @Test
    public void NegativeDeleteMember(){
        assertNotNull(fms.getMemberByUserName("aviluzon"));

        systemManagerC.deleteMember(systemManager,fms.getMemberByUserName("aviluzon").get(0));
        assertNotNull(fms.getMemberByUserName("aviluzon"));

    }
    @Test
    public void PositiveComplaintComment(){
        Fan fan =(Fan)fms.getMemberByUserName("itamar1200").get(0);
        fan.submitComplaintForm(new ComplaintForm("how is it possible for a player to be a teamOwner?"));
        systemManagerC.CommentOnComplaint(systemManager,systemManager.checkComplaints().get(0),"I have no idea");
        ComplaintAlert ca = (ComplaintAlert) fan.getAlertsList().peek();
        assertEquals("I have no idea",ca.getComplaintResponse().getResponse());
    }
    @Test
    public void NegativeComplaintComment(){
        Fan fan =(Fan)fms.getMemberByUserName("itamar1200").get(0);
        fan.submitComplaintForm(new ComplaintForm("how is it possible for a player to be a teamOwner?"));
        systemManagerC.CommentOnComplaint(systemManager,systemManager.checkComplaints().get(0),"4");

        assertFalse(fan.getAlertsList().size()>0);

    }
}
