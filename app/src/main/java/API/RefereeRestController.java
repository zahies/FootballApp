package API;

import DataAccess.Exceptions.NoConnectionException;
import DataAccess.SeasonManagmentDAL.GamesDAL;
import DataAccess.UsersDAL.PlayersDAL;
import Domain.SeasonManagment.Game;
import Domain.SeasonManagment.Team;
import Domain.Users.Player;
import Domain.Users.Referee;
import Domain.Users.RefereeType;
import Domain.Users.TeamOwner;
import FootballExceptions.NoPermissionException;
import FootballExceptions.PersonalPageYetToBeCreatedException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import SpringControllers.RefereeController;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("footballapp/referee")
@RestController
public class RefereeRestController {

    private final RefereeController refereeController;
    Referee ref = new Referee("John", "Snow", 555, "Halisi", RefereeType.Secondary);
    Player player = new Player("Jamie", "Lanister", 666 , "Sarsei", 222, "bla", new Date());
    RefereeType refereeType = RefereeType.Main;
    RefereeType refereeType2 = RefereeType.Secondary;
    Referee refereeTest = new Referee("Yossi43", "Yossi", 1234, "0101", refereeType);
    Referee refereeTest2 = new Referee("Paul33","Paul",1235,"0102",refereeType2);
    TeamOwner ownerHome = new TeamOwner("yakir", "yaki", 35, "3535");
    TeamOwner ownerOut = new TeamOwner("yam", "yami", 32, "3536");
    Team teamHome = new Team("Tel Aviv", ownerHome);
    Team teamOut = new Team("Haifa", ownerOut);
    Date  date1 =  new Date("31/03/2020");
    Game game = new Game(teamOut, teamHome, date1, ref, refereeTest2, null);



    @Autowired
    public RefereeRestController() {
        refereeController = new RefereeController();
    }

    @CrossOrigin
    @PostMapping("/addEvent")
    public boolean addEventToGame(@RequestBody Map<String,String> body ,final HttpServletResponse response) throws IOException, PersonalPageYetToBeCreatedException {
        boolean flag = false;
        String refereeUserName = body.get("username");
        String eventType = body.get("eventType");
        double minute = Double.parseDouble(body.get("minute"));
        int gameID = Integer.parseInt(body.get("gameID"));
        String playerUserName = body.get("playerusername");
        flag = refereeController.addEventToGame(refereeUserName, eventType, minute, gameID, playerUserName);
        if (flag){ //todo pop up success

        }
        else{
            response.sendError(HttpServletResponse.SC_CONFLICT, "Incorrect Login Details");

        }
        return flag;
    }


    @GetMapping ("/games")
    public Map<String,String> getGames(final HttpServletResponse response) {
        HashMap<String, String> ans = new HashMap<String,String>();
        ans.put("id", game.toString());
        return ans;
    }


    @CrossOrigin
    @PostMapping("/addreport")
    public boolean addReportToGame(@RequestBody Map<String,String> body, final HttpServletResponse response) throws PersonalPageYetToBeCreatedException, IOException {
        boolean flag = false;
        String refereeUserName = body.get("username");
        int gameID = Integer.parseInt(body.get("gameID"));
        flag = refereeController.addReportForGame(refereeUserName, gameID);
        if (flag){ //todo

        }
        else{
            response.sendError(HttpServletResponse.SC_CONFLICT, "Incorrect Login Details");

        }
        return flag;    }



/*
    @PostMapping("/editEvent")
    public boolean editEventToGame(@RequestBody Map<String,String> body) throws PersonalPageYetToBeCreatedException {
        String refereeUserName = body.get("username");
        String eventType = body.get("eventType");
        double minute = Double.parseDouble(body.get("minute"));
        int gameID = Integer.parseInt(body.get("gameID"));
        String playerUserName = body.get("playerusername");
        //Player player = new PlayersDAL().select()
        refereeController.editEventsAfterGame(refereeUserName, , gameID, playerUserName);

        try {
            Game game = new GamesDAL().select(gameID);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (UserInformationException e) {
            e.printStackTrace();
        } catch (UserIsNotThisKindOfMemberException e) {
            e.printStackTrace();
        } catch (NoConnectionException e) {
            e.printStackTrace();
        } catch (NoPermissionException e) {
            e.printStackTrace();
        }
        return true;
    }
    */
}
