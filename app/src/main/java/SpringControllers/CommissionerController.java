package SpringControllers;

import Domain.SeasonManagment.ICommissionerRule;
import Domain.SeasonManagment.IPlaceTeamsPolicy;
import Domain.SeasonManagment.IScorePolicy;
import Domain.SeasonManagment.Leaugue;
import Domain.Users.Commissioner;
import Domain.Users.Referee;
import FootballExceptions.*;
import javafx.util.Pair;

import java.net.UnknownHostException;

public class CommissionerController extends MemberController {


    /**
     * uc 9.1
     */
    public void defineLeague(Commissioner commissioner, int id) {
        try {
            //test
            commissioner.defineLeague(id);
        } catch (LeagueIDAlreadyExist | IDWasNotEnterdException le) {
            System.out.println(le.getMessage());
        }
    }

    /**
     * uc 9.2
     */
    public void addSeasonToLeague(Commissioner commissioner, int year, Leaugue leaugue) {
        try {
            commissioner.addSeasonToLeague(year, leaugue);
        } catch (SeasonYearAlreadyExist se) {
            System.out.println(se.getMessage());
        }
    }


    /**
     * uc 9.3
     */
    public void defineReferee(Commissioner commissioner, Referee ref) {
        try {
            commissioner.defineReferee(ref);
        } catch (RefereeEmailWasNotEntered | UnknownHostException re) {
            System.out.println(re.getMessage());
        }
    }


    /**
     * uc 9.3
     */
    public void defineReferee(Commissioner commissioner, String ref) {
        commissioner.defineReferee(ref);
    }

    /**
     * uc 9.4
     */
    public void addRefereeToSeason(Commissioner commissioner, int idLeg, int year, Referee ref) {
        try {
            commissioner.addRefereeToSeason(idLeg, year, ref);
        } catch (LeagueNotFoundException le) {
            System.out.println(le.getMessage());
        }
    }

    /**
     * uc 9.5
     */
    public void setNewScorePolicy(Commissioner commissioner, int idLeg, int year, IScorePolicy sp) {
        commissioner.setNewScorePolicy(idLeg, year, sp);
    }


    /**
     * uc 9.6
     */
    public void setNewPlaceTeamsPolicy(Commissioner commissioner, int idLeg, int year, IPlaceTeamsPolicy pp) {
        commissioner.setNewPlaceTeamsPolicy(idLeg, year, pp);
    }


    /**
     * uc 9.7
     */
    public void runPlacingAlgo(Commissioner commissioner, int idLeg, int year) {
        try {
            commissioner.runPlacingAlgo(idLeg, year);
        } catch (NotEnoughTeamsInLeague ne) {
            System.out.println(ne.getMessage());
        }

    }


    /**
     * UC 9.8 - Define rules about BUDGET CONTROL
     */
    public void defineBudgetControl(Commissioner commissioner, ICommissionerRule newRule) {
        commissioner.defineBudgetControl(newRule);
    }


    /**
     * UC 9.9  manage finance Association activity
     */
    public void addToFinanceAssociationActivity(Commissioner commissioner, String info, int amount) {
        commissioner.addToFinanceAssociationActivity(info, amount);
    }


    /**
     * 9.9
     */
    public void delFromFinanceAssociationActivity(Commissioner commissioner, Pair<String, Integer> pair) {
        try {
            commissioner.delFromFinanceAssociationActivity(pair);
        } catch (FinanceAssActivityNotFound fe) {
            System.out.println(fe.getMessage());
        }
    }

}
