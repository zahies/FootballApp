package Domain.Users;

import Domain.Alerts.ComplaintAlert;
import Domain.FootballManagmentSystem;
import Domain.SeasonManagment.ComplaintForm;
import Domain.SeasonManagment.Game;
import Domain.SeasonManagment.Team;
import Domain.SeasonManagment.TeamStatus;
import FootballExceptions.InactiveTeamException;
import FootballExceptions.NoPermissionException;
import FootballExceptions.ShortCommentException;
import FootballExceptions.UnableToRemoveException;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class SystemManagerTest {
    private SystemManager managerTest;
    private Team team ;
    private TeamOwner owner;
    private Player player;
    private ComplaintForm form;
    private Fan fan;
    @Before
    public void init(){
        managerTest = new SystemManager("NOA","Noa",314,"124214");
        owner = new TeamOwner("Adi","adi",242,"21423");
        team = new Team("Haifa",owner);
        player = new Player("David","david",243,"2342",4,null,null);
        form = new ComplaintForm("not a good game");

    }

    @Test
    public void closeTeam() throws InactiveTeamException, UnableToRemoveException {
   
        managerTest.closeTeam(team,"not a good team");
        assertEquals(TeamStatus.Close,team.getStatus());
    }

    @Test
    public void deleteMember() throws UnableToRemoveException, NoPermissionException {
        FootballManagmentSystem system = FootballManagmentSystem.getInstance();
        int beforeDelete=system.getMembers().size();
        managerTest.deleteMember(player);
        assertEquals(beforeDelete-1,system.getMembers().size());
    }

    @Test
    public void checkComplaints() {
        managerTest.checkComplaints();
    }

    @Test
    public void commentOnComplaint() throws ShortCommentException {

        Fan fanPolani= new Fan("Itamar","itamar",32432,"235");
        ComplaintForm tempComplain = new ComplaintForm("how is it possible for a player to be a teamOwner?");
        fanPolani.submitComplaintForm(tempComplain);
        List<ComplaintForm> complaintFormList = new LinkedList<>();
        complaintFormList = managerTest.checkComplaints();
        for (int i = 0; i < complaintFormList.size(); i++){
            if ( complaintFormList.get(i).equals(tempComplain) ){
                managerTest.CommentOnComplaint(complaintFormList.get(i),"I have no idea");
            }
        }
        ComplaintAlert ca = (ComplaintAlert) fanPolani.getAlertsList().peek();
        assertEquals("I have no idea",ca.getComplaintResponse().getResponse());
    }

    @Test
    public void closeTeamAlert() throws InactiveTeamException, UnableToRemoveException {
        TeamOwner teamOwnerToAlert = new TeamOwner("Shlomo32523","Shlo sadmo",5235,"$124");
        Team teamToClose = new Team("ninja turtle",teamOwnerToAlert);
        SystemManager sysMan = new SystemManager("Eyal2","Eyal", 1234, "0000");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(System.currentTimeMillis()));
        cal.add(Calendar.HOUR_OF_DAY, 6); // adds 6 hours
        long time = ((new Date(System.currentTimeMillis()))).getTime();
        Referee ref = new Referee("ref500","theref",4,"3232",RefereeType.Main);
        Referee ref2 = new Referee("ref5600","theref",4,"3232",RefereeType.Main);
        Game game = new Game(null,team,cal.getTime(),ref,ref2,null);
        teamToClose.addGameToUpcomingGames(game);
        teamOwnerToAlert.logOut();
        sysMan.closeTeam(teamToClose,"kaha");
        assertEquals(1,teamOwnerToAlert.getAlertsList().size());
    }
    @Test
    public void getLog() {
    }
}