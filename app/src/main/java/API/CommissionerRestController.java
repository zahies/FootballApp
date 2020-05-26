package API;



import SpringControllers.CommissionerController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RequestMapping("footballapp/commissioner")
@RestController
public class CommissionerRestController {

    private final CommissionerController comController;


    @Autowired
    public CommissionerRestController() {
        this.comController = new CommissionerController();
    }



    @GetMapping
    public String get(){
        System.out.println("DSDDASD");
        return "commissssiionner";
    }



    @CrossOrigin
    @PostMapping("/addScorePolicy")
    public void addScorePolicy(@RequestBody Map<String,String> body, final HttpServletResponse response) throws IOException{
        boolean succeeded;
        String commissionerUsername = body.get("username");
        int leagueId = Integer.parseInt(body.get("leagueID"));
        int year = Integer.parseInt(body.get("year"));
        int winVal = Integer.parseInt(body.get("winval"));
        int loseVal = Integer.parseInt(body.get("loseval"));
        int drawVal = Integer.parseInt(body.get("drawval"));
        succeeded = comController.setNewScorePolicy(commissionerUsername,leagueId,year,winVal,loseVal,drawVal);
        if (succeeded){
            /**pop up success*/
            response.setStatus(HttpServletResponse.SC_ACCEPTED, "Score Policy Added Successfully ! ");
        }else {
            /**pop up failed*/
            response.sendError(HttpServletResponse.SC_CONFLICT,"Incorrect Details");
        }
    }

    @CrossOrigin
    @PostMapping("/addTeamsPolicy")
    public void addPlaceTeamsPolicy(@RequestBody Map<String,String> body, final HttpServletResponse response) throws IOException{
        boolean succeeded;
        String commissionerUsername = body.get("username");
        int leagueId = Integer.parseInt(body.get("leagueID"));
        int year = Integer.parseInt(body.get("year"));
        int numGames = Integer.parseInt(body.get("numgames"));
        succeeded = comController.setNewPlaceTeamsPolicy(commissionerUsername,leagueId,year,numGames);
        if (succeeded){
            /**pop up success*/
            response.setStatus(HttpServletResponse.SC_ACCEPTED, "Place Teams Policy Added Successfully ! ");
        }else {
            /**pop up failed*/
            response.sendError(HttpServletResponse.SC_CONFLICT,"Incorrect Details");
        }
    }



    /**
     * UC 9.8 - Define rules about BUDGET CONTROL
     */
    @CrossOrigin
    @PostMapping("/addCommissionerRule")
    public void addCommissionerRule(@RequestBody Map<String,String> body, final HttpServletResponse response) throws IOException {
        boolean succeeded;
        String commissionerUsername = body.get("username");
        int ruleAmount = Integer.parseInt(body.get("ruleAmount"));
        succeeded =  comController.defineBudgetControl(commissionerUsername,ruleAmount);
        if (succeeded){
            /**pop up success*/
            response.setStatus(HttpServletResponse.SC_ACCEPTED, "Your Rule Added Successfully ! ");
        }else {
            /**pop up failed*/
            response.sendError(HttpServletResponse.SC_CONFLICT,"Incorrect Details");
        }
    }



}
