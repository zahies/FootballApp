package API;


import Domain.ErrorLog;
import Domain.Users.TeamOwner;
import SpringControllers.CommissionerController;
import SpringControllers.TeamOwnerController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RequestMapping("footballapp/teamowner")
@RestController
public class TeamOwnerRestController {


    private final TeamOwnerController ownerController;


    @Autowired
    public TeamOwnerRestController() {
        this.ownerController = new TeamOwnerController();
    }


    @CrossOrigin
    @PostMapping("/signteam")
    public void signUpTeam(@RequestBody Map<String,String> body, final HttpServletResponse response) throws IOException {
        boolean succeeded;
        String ownerUsername = body.get("username");
        int leagueId = Integer.parseInt(body.get("leagueID"));
        int year = Integer.parseInt(body.get("year"));
        String teamName = (body.get("team-name"));
        succeeded = ownerController.sendRegisterRequestForNewTeam(ownerUsername,teamName,leagueId,year);
        if (succeeded){
            /**pop up success*/
            response.setStatus(HttpServletResponse.SC_ACCEPTED, "Sign Team Request Added Successfully ! ");
        }else {
            /**pop up failed*/
            response.sendError(HttpServletResponse.SC_CONFLICT,"Incorrect Details");
            ErrorLog.getInstance().UpdateLog("The error is: " + "Incorrect Details");

        }

    }



}
