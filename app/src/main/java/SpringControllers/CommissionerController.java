package SpringControllers;

import DataAccess.Exceptions.NoConnectionException;
import DataAccess.SeasonManagmentDAL.GamesDAL;
import Domain.SeasonManagment.*;
import Domain.Users.Commissioner;
import Domain.Users.Referee;
import FootballExceptions.*;
import javafx.util.Pair;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Random;
import java.util.UUID;

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
    public boolean setNewScorePolicy(String username,int idLeg, int year,int winVal, int loseVal, int drawVal) {
        boolean succeeded = false;
        IScorePolicy sp = new IScorePolicy(){
            private UUID spID = UUID.randomUUID();

            @Override
            public int winVal() {
                return winVal;
            }

            @Override
            public UUID getId() {
                return spID;
            }

            @Override
            public int looseVal() {
                return loseVal;
            }

            @Override
            public int drowVal() {
                return drawVal;
            }
        };

//        try {
//            Commissioner commissioner = new CommissionerDAL().select(username);
//            commissioner.setNewScorePolicy(idLeg, year, sp);
//            succeeded = true;
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        } catch (UserInformationException e) {
//            e.printStackTrace();
//        } catch (UserIsNotThisKindOfMemberException e) {
//            e.printStackTrace();
//        } catch (NoConnectionException e) {
//            e.printStackTrace();
//        } catch (NoPermissionException e) {
//            e.printStackTrace();
//        }
        return succeeded;
    }


    /**
     * uc 9.6
     */
    public boolean setNewPlaceTeamsPolicy(String username, int idLeg, int year, int numGames) {
        boolean succeeded = false;
        IPlaceTeamsPolicy pp = new IPlaceTeamsPolicy() {

            private UUID ppID = UUID.randomUUID();

            @Override
            public int numOfGamesWithEachTeam() {
                return numGames;
            }

            @Override
            public UUID getId() {
                return ppID;
            }
        };
//        try {
//            Commissioner commissioner = new CommissionerDAL().select(username);
//            commissioner.setNewPlaceTeamsPolicy(idLeg, year, pp);
//            succeeded = true;
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        } catch (UserInformationException e) {
//            e.printStackTrace();
//        } catch (UserIsNotThisKindOfMemberException e) {
//            e.printStackTrace();
//        } catch (NoConnectionException e) {
//            e.printStackTrace();
//        } catch (NoPermissionException e) {
//            e.printStackTrace();
//        }
        return succeeded;
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
    public boolean defineBudgetControl(String username, int ruleAmount,String desc) {
        boolean succeeded = false;
        ICommissionerRule newRule = new ICommissionerRule() {
            private UUID newruleID = UUID.randomUUID();

            @Override
            public int payRule() {
                return ruleAmount;
            }

            @Override
            public UUID getObjectID() {
                return newruleID;
            }

            @Override
            public String getDescription() {
                return desc;
            }
        };

//        try {
//            Commissioner commissioner = new CommissionerDAL().select(username);
//            commissioner.defineBudgetControl(newRule);
//            succeeded = true;
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        } catch (UserInformationException e) {
//            e.printStackTrace();
//        } catch (UserIsNotThisKindOfMemberException e) {
//            e.printStackTrace();
//        } catch (NoConnectionException e) {
//            e.printStackTrace();
//        } catch (NoPermissionException e) {
//            e.printStackTrace();
//        }

        return succeeded;

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
