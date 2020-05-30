package Domain.Users;

import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.UsersDAL.CoachesDAL;
import DataAccess.UsersDAL.CommissionersDAL;
import Domain.Alerts.IAlert;
import Domain.Alerts.RegistrationRequestAlert;
import Domain.FootballManagmentSystem;
import Domain.SeasonManagment.*;
import FootballExceptions.*;
import javafx.util.Pair;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.Queue;

public class Commissioner extends Member {


    LinkedList<Pair<String, Integer>> financeAssociationActivity;

    /**DB - SELECT*/
    public Commissioner(String name, String password, String real_Name, Queue<IAlert> alertsList, boolean isActive, boolean alertViaMail, String mailAddress, LinkedList<Pair<String, Integer>> financeAssociationActivity) {
        super(name, password, real_Name, alertsList, isActive, alertViaMail, mailAddress);
        this.financeAssociationActivity = financeAssociationActivity;
    }

    /**
     * UC 9.9
     * <p>
     * /**
     * constructor
     *
     * @param name
     * @param id
     * @param password
     */

    public Commissioner(String name, int id, String password, String realName) throws mightBeSQLInjectionException, DuplicatedPrimaryKeyException, NoPermissionException, SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException {
        super(name, id, password, realName);
        financeAssociationActivity = new LinkedList<>();
        new CommissionersDAL().insert(this);
    }


    /**
     * UC 9.1 - Define league
     */
    public void defineLeague(int id) throws LeagueIDAlreadyExist, IDWasNotEnterdException {
        Leaugue leaugue = new Leaugue();
        leaugue.setId(id);
        leaugue.setLeagueIntoSystem();
    }


    /**
     * UC 9.2 - Adding season to league by year
     */
    public void addSeasonToLeague(int year, Leaugue leaugue) throws SeasonYearAlreadyExist {
        leaugue.addSeasonToLeagueByYear(year);
    }


    /**
     * UC 9.3 - Define Referee to system
     */
    public void defineReferee(Referee ref) throws RefereeEmailWasNotEntered, UnknownHostException {
        // todo UC 9.3.1 - need to send invitation to the referee
        if (ref.getEmail() == null) {
            throw new RefereeEmailWasNotEntered("set the email for the referee first & try again");
        }
        FootballManagmentSystem system = FootballManagmentSystem.getInstance();
      //  system.sendInvitationByMail(ref.getEmail(), "Invitation For FootballApp", "Hello " + ref.getName() + "\nWe're excited to invite you to use our FootballApp.\nCome and join us :)");
        system.sendInvitationByMail("shira.wert@gmail.com","HII" , "HEYY");
        system.addReferee(ref);
    }

    /**
     * UC 9.3 - Del Referee from system by name
     */
    public void defineReferee(String ref) {
        FootballManagmentSystem system = FootballManagmentSystem.getInstance();
        try {
            system.delReferee(ref);
        } catch (UserInformationException ue) {
            System.out.println(ue.getMessage());
        }
    }

    /**
     * UC 9.4 - Define Referee to specific season
     */
    public void addRefereeToSeason(int idLeg, int year, Referee ref) throws LeagueNotFoundException {
        FootballManagmentSystem system = FootballManagmentSystem.getInstance();
        List<Leaugue> legs = system.getAllLeagus();
        Leaugue leaugue = new Leaugue();
        boolean found = false;
        for (int i = 0; i < legs.size(); i++) {
            if (legs.get(i).getID() == idLeg) {
                leaugue = legs.get(i);
                found = true;
                break;
            }
        }
        if (found) {
            Season season = leaugue.getSeasonByYear(year);
            season.addRefereeToSeason(ref);
        } else {
            throw new LeagueNotFoundException("there is not league with this id " + idLeg);
        }
    }


    /**
     * UC 9.5 - Define new SCORE policy to specific season
     */
    public void setNewScorePolicy(int idLeg, int year, IScorePolicy sp) {
        FootballManagmentSystem system = FootballManagmentSystem.getInstance();
        List<Leaugue> legs = system.getAllLeagus();
        Leaugue leaugue = new Leaugue();
        boolean found = false;
        for (int i = 0; i < legs.size(); i++) {
            if (legs.get(i).getID() == idLeg) {
                leaugue = legs.get(i);
                found = true;
                break;
            }
        }
        if (found) {
            Season season = leaugue.getSeasonByYear(year);
            season.setNewScorePolicy(sp);
        }
    }


    /**
     * UC 9.6 - Define new PLACING policy to specific season
     */
    public void setNewPlaceTeamsPolicy(int idLeg, int year, IPlaceTeamsPolicy pp) {
        FootballManagmentSystem system = FootballManagmentSystem.getInstance();
        List<Leaugue> legs = system.getAllLeagus();
        Leaugue leaugue = new Leaugue();
        boolean found = false;
        for (int i = 0; i < legs.size(); i++) {
            if (legs.get(i).getID() == idLeg) {
                leaugue = legs.get(i);
                found = true;
                break;
            }
        }
        if (found) {
            Season season = leaugue.getSeasonByYear(year);
            season.setNewTeamsPolicy(pp);
        }
    }


    /**
     * UC 9.7 - Define new PLACING policy to specific season
     */
    public void runPlacingAlgo(int idLeg, int year) throws NotEnoughTeamsInLeague, mightBeSQLInjectionException, DuplicatedPrimaryKeyException, NoPermissionException, SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException {
        FootballManagmentSystem system = FootballManagmentSystem.getInstance();
        List<Leaugue> legs = system.getAllLeagus();
        Leaugue leaugue = new Leaugue();
        boolean found = false;
        for (int i = 0; i < legs.size(); i++) {
            if (legs.get(i).getID() == idLeg) {
                leaugue = legs.get(i);
                found = true;
                break;
            }
        }
        if (found) {
            Season season = leaugue.getSeasonByYear(year);
            season.runPlacingTeamsAlgorithm();
        }
    }


    /**
     * UC 9.8 - Define rules about BUDGET CONTROL
     */
    public void defineBudgetControl(ICommissionerRule newRule) {

        FootballManagmentSystem system = FootballManagmentSystem.getInstance();
        HashMap<UUID, Team> teams = system.getAllTeams();
        for (UUID i : teams.keySet()) {
            teams.get(i).getControlBudget().setCommissionerRule(newRule);
        }

    }


    /**
     * UC 9.9  manage finance Association activity
     */
    public void addToFinanceAssociationActivity(String info, int amount) {
        Pair pair = new Pair(info, amount);
        financeAssociationActivity.add(pair);
    }

    public void delFromFinanceAssociationActivity(Pair<String, Integer> pair) throws FinanceAssActivityNotFound {
        if (financeAssociationActivity.remove(pair)) {

        } else {
            throw new FinanceAssActivityNotFound("there is not activity such that !");
        }

    }

    public LinkedList<Pair<String, Integer>> getFinanceAssociationActivity() {
        return financeAssociationActivity;
    }

    public boolean responseToRegisterTeamByAlert(String teamName) throws mightBeSQLInjectionException, DuplicatedPrimaryKeyException, NoPermissionException, SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException {
        Queue<IAlert> alerts = getAlertsList();
        FootballManagmentSystem system = FootballManagmentSystem.getInstance();
        for (IAlert alert : alerts) {
            if (alert instanceof RegistrationRequestAlert){
                if (teamName.equals(((RegistrationRequestAlert) alert).getTeamName())){
                    Team newTeam = new Team(((RegistrationRequestAlert) alert).getTeamName(),((RegistrationRequestAlert) alert).getOwner());
                    alertsList.remove(alert);
                    return true;
                }
            }
        }
        return false;
    }
}
