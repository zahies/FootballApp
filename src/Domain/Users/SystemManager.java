package Domain.Users;

import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.UsersDAL.SystemManagerDAL;
import Domain.Alerts.ComplaintAlert;
import Domain.Alerts.IAlert;
import Domain.Alerts.TeamManagementAlert;
import Domain.FootballManagmentSystem;
import Domain.SeasonManagment.ComplaintForm;
import Domain.SeasonManagment.Team;
import Domain.SystemLog;
import FootballExceptions.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import static Domain.SeasonManagment.TeamStatus.Close;

public class SystemManager extends Member {
    public SystemManager(String name, String realname, int id, String password) throws mightBeSQLInjectionException, DuplicatedPrimaryKeyException, NoPermissionException, SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException {
        super(name, id, password, realname);
        new SystemManagerDAL().insert(this);
    }

    public SystemManager(String name, String password, String real_Name, Queue<IAlert> alertsList, boolean isActive, boolean alertViaMail, String mailAddress) {
        super(name, password, real_Name, alertsList, isActive, alertViaMail, mailAddress);
    }

    /**
     * close a team , dosent delete it from memory send notes to owners and managers
     *
     * @param team          to delete
     * @param causeOfCloser why did the team got closed?
     */
    public void closeTeam(Team team, String causeOfCloser) throws InactiveTeamException, UnableToRemoveException {
        HashMap<UUID, Team> map = FootballManagmentSystem.getInstance().getAllTeams();
        List<Team> teams = new LinkedList<>();
        for (UUID id : map.keySet()) {
            teams.add(map.get(id));
        }
        for (Team t :
                teams) {
            if (t.getId() == team.getId()) {
                long time = new Date(System.currentTimeMillis()).getTime();
                Calendar cal = Calendar.getInstance();
                if (t.getUpcomingGames().size() != 0) {
                    cal.setTime(t.getUpcomingGames().get(0).getDateGame());

                    cal.add(Calendar.HOUR_OF_DAY, -2); // remove 2 hours
                    time = cal.getTimeInMillis() - time;
                    if (time < 0) {
                        SystemLog.getInstance().UpdateLog("Deletion of " + t.getName() + " was Unsuccessful.");
                        throw new UnableToRemoveException("The team has a game in the next 2 hours");

                    }
                }
                if (t.getStatus() == Close) {
                    SystemLog.getInstance().UpdateLog("Deletion of " + t.getName() + " was Unsuccessful. it's closed already.");
                    throw new InactiveTeamException();//team allready closed
                }
                for (TeamOwner to : t.getAllTeamOwners()
                ) {
                    to.handleAlert(new TeamManagementAlert("You're team " + t.getName() + "has been " +
                            "closed by " + this.getName() + " permanently duo to " + causeOfCloser,t.getStatus()));

                }
                for (TeamManager to : t.getAllTeamManaers()
                ) {
                    to.handleAlert(new TeamManagementAlert("You're team " + t.getName() + "has been " +
                            "closed by " + this.getName() + " permanently duo to " + causeOfCloser,t.getStatus()));

                }
                t.setStatus(Close);
                t.setClosed(true);
                SystemLog.getInstance().UpdateLog("Deletion of " + team.getName() + " was successful.");


            }
        }
    }

    /**
     * remove member  , cant remove team owner if he is the only one.
     *
     * @param member to remove
     */
    public void deleteMember(Member member) throws UnableToRemoveException, NoPermissionException {
        if (FootballManagmentSystem.getInstance().getMembers().containsKey(member.getName())) {
            if (member instanceof SystemManager) {
                throw new NoPermissionException("No premission to delete a system manager");
            }
            if (!(member instanceof TeamOwner))
                FootballManagmentSystem.getInstance().RemoveMember(FootballManagmentSystem.getInstance().getMemberByUserName(member.getName()));
            else {
                if (((TeamOwner) member).getTeam().getAllTeamOwners().size() < 1) {
                    SystemLog.getInstance().UpdateLog("Deletion of " + member.getName() + " was unsuccessful duo to the fact he is the team only owner");
                    throw new UnableToRemoveException();
                } else {
                    FootballManagmentSystem.getInstance().RemoveMember(FootballManagmentSystem.getInstance().getMemberByUserName(member.getName()));
                }
            }
        }

    }

    public List<ComplaintForm> checkComplaints() {
        return FootballManagmentSystem.getInstance().getAllcomplaints();

    }

    public void CommentOnComplaint(ComplaintForm comp, String response) throws ShortCommentException {

        if (response.length() < 2) {
            throw new ShortCommentException("messege must consist at least 2 characters");
        }
        comp.setResponse(response);

        comp.getFanSubmitingForm().handleAlert((IAlert) new ComplaintAlert(comp));
        SystemLog.getInstance().UpdateLog("complaint by " + comp.getFanSubmitingForm().getName() +
                "was implied");
        FootballManagmentSystem.getInstance().removeComplaint(comp);

    }

    public String getLog() throws IOException {
        return SystemLog.getInstance().showLog();
    }

}



