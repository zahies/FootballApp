package API;

import DataAccess.Exceptions.NoConnectionException;
import DataAccess.SeasonManagmentDAL.GamesDAL;
import Domain.SeasonManagment.Game;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import SpringControllers.RefereeController;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.Map;

@RequestMapping("footballapp/referee")
@RestController
public class RefereeRestController {

    private final RefereeController refereeController;

    @Autowired
    public RefereeRestController() {
        refereeController = new RefereeController();
    }


    @PostMapping("/addEvent")
    public boolean addEventToGame(@RequestBody Map<String,String> body){
        int gameID = Integer.parseInt(body.get("gameID"));

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
}
