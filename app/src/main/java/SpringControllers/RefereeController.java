package SpringControllers;

import DataAccess.Exceptions.NoConnectionException;
import DataAccess.SeasonManagmentDAL.GamesDAL;
import DataAccess.UsersDAL.PlayersDAL;
import DataAccess.UsersDAL.RefereesDAL;
import Domain.Events.AGameEvent;
import Domain.SeasonManagment.Game;
import Domain.Users.Member;
import Domain.Users.Player;
import Domain.Users.Referee;
import Domain.Users.RefereeType;
import FootballExceptions.*;

import java.sql.SQLException;

public class RefereeController extends MemberController {



    /**
     * 10.1
     */
    public boolean changeName(String username, String name) {
        boolean flag = false;
//        try {
//            Member referee = new RefereeDAL().select(username);
//            ((Referee)referee).changeName(name);
//            flag = true;
//        } catch (UserInformationException ue) {
//            System.out.println(ue.getMessage());
//        } catch (NoPermissionException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (UserIsNotThisKindOfMemberException e) {
//            e.printStackTrace();
//        } catch (NoConnectionException e) {
//            e.printStackTrace();
//        }
        return flag;
    }

    /**
     * 10.1
     */
    public boolean changeTraining(String username, RefereeType type) {
        boolean flag = false;
//        try {
//            Member referee = new RefereeDAL().select(username);
//            ((Referee)referee).changeTraining(type); //todo change refType to String?
//            flag = true;
//        } catch (UserInformationException ue) {
//            System.out.println(ue.getMessage());
//        } catch (NoPermissionException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (UserIsNotThisKindOfMemberException e) {
//            e.printStackTrace();
//        } catch (NoConnectionException e) {
//            e.printStackTrace();
//        }
        return flag;
    }


    /**
     * 10.2
     */
    public boolean watchGame(String username, Integer gameID) {
        boolean flag = false;
//        try {
//            Member referee = new RefereeDAL().select(username);
//            Game game = new GamesDAL().select(gameID);
//            ((Referee)referee).watchGame(game);
//            flag = true;
//        } catch (RefereeNotPlacedException re) {
//            System.out.println(re.getMessage());
//        } catch (UserIsNotThisKindOfMemberException e) {
//            e.printStackTrace();
//        } catch (NoPermissionException e) {
//            e.printStackTrace();
//        } catch (UserInformationException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (NoConnectionException e) {
//            e.printStackTrace();
//        }
        return flag;
    }

    /**
     * 10.3
     */
    public boolean addEventToGame(String username, String eventType, double minute, Integer gameID, String playerusername) throws PersonalPageYetToBeCreatedException, UserIsNotThisKindOfMemberException, SQLException, UserInformationException, NoConnectionException, NoPermissionException, EventNotMatchedException {
        boolean flag = false;
        try {
            Member referee = new RefereesDAL().select(username);
            Game game = new GamesDAL().select(gameID.toString());
            Member playerWhoCommit = new PlayersDAL().select(playerusername);
            ((Referee) referee).addEventToGame(eventType, minute, game, (Player) playerWhoCommit);
            flag = true;
        }catch (SQLException e) {
            e.printStackTrace();
        }
//        } catch (EventNotMatchedException ee) {
//            System.out.println(ee.getMessage());
//        } catch (UserIsNotThisKindOfMemberException e) {
//            e.printStackTrace();
//        } catch (NoPermissionException e) {
//            e.printStackTrace();
//        } catch (UserInformationException e) {
//            e.printStackTrace();
//        } catch (NoConnectionException e) {
//            e.printStackTrace();
//        }

        return flag;
    }


    /**
     * 10.4
     */
        public boolean editEventsAfterGame(String username, Integer gameID, AGameEvent oldEvent, AGameEvent newEvent) {
            boolean flag = false;
//            try {
//            Member referee = new RefereeDAL().select(username);
//            Game game = new GamesDAL().select(gameID);
//            ((Referee)referee).editEventsAfterGame(game, oldEvent, newEvent); //todo change AGameEvent to String?
//            flag = true;
//            } catch (NoPermissionException ne) {
//            System.out.println(ne.getMessage());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (UserInformationException e) {
//            e.printStackTrace();
//        } catch (UserIsNotThisKindOfMemberException e) {
//            e.printStackTrace();
//        } catch (NoConnectionException e) {
//            e.printStackTrace();
//        }
            return flag;
        }


    /**
     * 10.4
     */
    public boolean addReportForGame(String username, Integer gameID) {
        boolean flag = false;
//        try {
//            Member referee = new RefereeDAL().select(username);
//            Game game = new GamesDAL().select(gameID);
//            ((Referee)referee).addReportForGame(game);
//            flag = true;
//        } catch (NoPermissionException ne) {
//            System.out.println(ne.getMessage());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (UserInformationException e) {
//            e.printStackTrace();
//        } catch (UserIsNotThisKindOfMemberException e) {
//            e.printStackTrace();
//        } catch (NoConnectionException e) {
//            e.printStackTrace();
//        }
        return flag;
    }

}
