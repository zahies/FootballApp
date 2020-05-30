package SpringControllers;

import DataAccess.Exceptions.NoConnectionException;
import DataAccess.SeasonManagmentDAL.GamesDAL;
import DataAccess.SeasonManagmentDAL.LeaguesDAL;
import DataAccess.UsersDAL.CoachesDAL;
import DataAccess.UsersDAL.CommissionersDAL;
import DataAccess.UsersDAL.RefereesDAL;
import Domain.Alerts.ChangedGameAlert;
import Domain.Alerts.IAlert;
import Domain.Alerts.RegistrationRequestAlert;
import Domain.FootballManagmentSystem;
import Domain.SeasonManagment.*;
import Domain.Users.*;
import FootballExceptions.*;
import javafx.util.Pair;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.*;

public class CommissionerController extends MemberController {


    /**
     * uc 9.1
     */
    public boolean defineLeague(String username, int id) {
        boolean flag = false;
        try {
            //test
            Commissioner commissioner = (Commissioner) new CommissionersDAL().select(username);
            commissioner.defineLeague(id);
            flag = true;
        } catch (LeagueIDAlreadyExist | IDWasNotEnterdException le) {
            System.out.println(le.getMessage());
        } catch (UserIsNotThisKindOfMemberException e) {
            e.printStackTrace();
        } catch (NoPermissionException e) {
            e.printStackTrace();
        } catch (UserInformationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoConnectionException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * uc 9.2
     */
    public boolean addSeasonToLeague(String username, int year, String leaugueString) {
        boolean flag = false;
        try {
            Commissioner commissioner = (Commissioner) new CommissionersDAL().select(username);
            Leaugue leaugue = (Leaugue) new LeaguesDAL().select(leaugueString);
            commissioner.addSeasonToLeague(year, leaugue);
            flag = true;
        } catch (SeasonYearAlreadyExist se) {
            System.out.println(se.getMessage());
        } catch (UserIsNotThisKindOfMemberException e) {
            e.printStackTrace();
        } catch (NoPermissionException e) {
            e.printStackTrace();
        } catch (UserInformationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoConnectionException e) {
            e.printStackTrace();
        }
        return flag;
    }


    /**
     * uc 9.3
     */
    public boolean defineReferee(String comstring, String refstring) {
        boolean flag = false;
        try {
            Commissioner commissioner = (Commissioner) new CommissionersDAL().select(comstring);
            Referee ref = (Referee) new RefereesDAL().select(comstring);
            commissioner.defineReferee(ref);
            flag = true;
        } catch (RefereeEmailWasNotEntered | UnknownHostException re) {
            System.out.println(re.getMessage());
        } catch (UserIsNotThisKindOfMemberException e) {
            e.printStackTrace();
        } catch (NoPermissionException e) {
            e.printStackTrace();
        } catch (UserInformationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoConnectionException e) {
            e.printStackTrace();
        }
        return flag;
    }

//
//    /**
//     * uc 9.3
//     */
//    public void defineReferee(String comstring, String ref) {
//        commissioner.defineReferee(ref);
//    }

    /**
     * uc 9.4
     */
    public boolean addRefereeToSeason(String comstring, int idLeg, int year, String refstring) {
        boolean flag = false;
        try {
            Commissioner commissioner = (Commissioner) new CommissionersDAL().select(comstring);
            Referee ref = (Referee) new RefereesDAL().select(comstring);
            commissioner.addRefereeToSeason(idLeg, year, ref);
            flag = true;
        } catch (LeagueNotFoundException le) {
            System.out.println(le.getMessage());
        } catch (UserIsNotThisKindOfMemberException e) {
            e.printStackTrace();
        } catch (NoPermissionException e) {
            e.printStackTrace();
        } catch (UserInformationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoConnectionException e) {
            e.printStackTrace();
        }

        return flag;
    }

    /**
     * uc 9.5
     */
    public boolean setNewScorePolicy(String username,int idLeg, int year,int winVal, int loseVal, int drawVal) throws UserIsNotThisKindOfMemberException, NoPermissionException, UserInformationException, NoConnectionException {
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

        try {
            Commissioner commissioner = new CommissionersDAL().select(username);
            commissioner.setNewScorePolicy(idLeg, year, sp);
            succeeded = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
    public boolean setNewPlaceTeamsPolicy(String username, int idLeg, int year, int numGames) throws UserIsNotThisKindOfMemberException, NoPermissionException, UserInformationException, NoConnectionException {
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
        try {
            Commissioner commissioner = new CommissionersDAL().select(username);
            commissioner.setNewPlaceTeamsPolicy(idLeg, year, pp);
            succeeded = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
//        catch (UserInformationException e) {
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
    public boolean runPlacingAlgo(String username, int idLeg, int year) {
        boolean flag = false;
        try {
            Commissioner commissioner = (Commissioner) new CommissionersDAL().select(username);
            commissioner.runPlacingAlgo(idLeg, year);
            flag = true;
        } catch (NotEnoughTeamsInLeague ne) {
            System.out.println(ne.getMessage());
        } catch (UserIsNotThisKindOfMemberException e) {
            e.printStackTrace();
        } catch (NoPermissionException e) {
            e.printStackTrace();
        } catch (UserInformationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoConnectionException e) {
            e.printStackTrace();
        }
        return flag;
    }


    /**
     * UC 9.8 - Define rules about BUDGET CONTROL
     */
    public boolean defineBudgetControl(String username, int ruleAmount,String desc) throws UserIsNotThisKindOfMemberException, NoPermissionException, UserInformationException, NoConnectionException {
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

        try {
            Commissioner commissioner = new CommissionersDAL().select(username);
            commissioner.defineBudgetControl(newRule);
            succeeded = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
//        catch (UserInformationException e) {
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
    public boolean addToFinanceAssociationActivity(String username, String info, int amount) {
        boolean flag = false;
        try {
            Commissioner commissioner = (Commissioner) new CommissionersDAL().select(username);
            commissioner.addToFinanceAssociationActivity(info, amount);
            flag = true;
        } catch (UserIsNotThisKindOfMemberException e) {
            e.printStackTrace();
        } catch (NoPermissionException e) {
            e.printStackTrace();
        } catch (UserInformationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoConnectionException e) {
            e.printStackTrace();
        }
        return flag;
    }


        /**
     * 9.9
     */
    public boolean delFromFinanceAssociationActivity(String username, Pair<String, Integer> pair) {
        boolean flag = false;
        try {
            Commissioner commissioner = (Commissioner) new CommissionersDAL().select(username);
            commissioner.delFromFinanceAssociationActivity(pair);
            flag = true;
        } catch (FinanceAssActivityNotFound fe) {
            System.out.println(fe.getMessage());
        } catch (UserIsNotThisKindOfMemberException e) {
            e.printStackTrace();
        } catch (NoPermissionException e) {
            e.printStackTrace();
        } catch (UserInformationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoConnectionException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean responseToRegistrationRequest(String username,String teamName) throws UserIsNotThisKindOfMemberException, NoPermissionException, UserInformationException, NoConnectionException {
        boolean flag = false;
        try {
            Commissioner commissioner = (Commissioner) new CommissionersDAL().select(username);
            flag = commissioner.responseToRegisterTeamByAlert(teamName);
        }catch (SQLException e) {
        e.printStackTrace();
        }


        return flag;
    }

    public Map<String, String> getReplyForRegistration(String user) throws UserIsNotThisKindOfMemberException, SQLException, UserInformationException, NoConnectionException, NoPermissionException {
        Commissioner commissioner = (Commissioner) new CommissionersDAL().select(user);

        TeamOwner teamowner = new TeamOwner("Moshe","DASD",123,"asd");
        Member com = new Commissioner("zaza",12,"123","zahi zahi");
        FootballManagmentSystem system = FootballManagmentSystem.getInstance();
        Leaugue leaugue = new Leaugue();
        IAlert regAlert = new RegistrationRequestAlert("hpoel",leaugue,1900,teamowner);
        com.addAlert(regAlert);
        system.addMember(com);

        Queue<IAlert> alerts = com.getAlertsList();   /** change to commissioner */
        Map<String,String> map = new HashMap<>();
        for (IAlert alert: alerts) {
            if (alert instanceof RegistrationRequestAlert){
                map.put("teamname",((RegistrationRequestAlert) alert).getTeamName());
                map.put("username", ((RegistrationRequestAlert) alert).getOwner().getName());
                break;
            }
        }
        return map;
    }
}
