package API;



import SpringControllers.CommissionerController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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




    @PostMapping("/addScorePolicy")
    public void addScorePolicy(@RequestBody Map<String,String> body){
        String commissionerUsername = body.get("username");
        int leagueId = Integer.parseInt(body.get("leagueID"));
        int year = Integer.parseInt(body.get("year"));
        int winVal = Integer.parseInt(body.get("winval"));
        int loseVal = Integer.parseInt(body.get("loseval"));
        int drawVal = Integer.parseInt(body.get("drawval"));
        comController.setNewScorePolicy(commissionerUsername,leagueId,year,winVal,loseVal,drawVal);
    }


    @PostMapping("/addTeamsPolicy")
    public void addPlaceTeamsPolicy(@RequestBody Map<String,String> body){
        String commissionerUsername = body.get("username");
        int leagueId = Integer.parseInt(body.get("leagueID"));
        int year = Integer.parseInt(body.get("year"));
        int numGames = Integer.parseInt(body.get("numgames"));
        comController.setNewPlaceTeamsPolicy(commissionerUsername,leagueId,year,numGames);
    }



    /**
     * UC 9.8 - Define rules about BUDGET CONTROL
     */
    @PostMapping("/addCommissionerRule")
    public void addCommissionerRule(@RequestBody Map<String,String> body){
        String commissionerUsername = body.get("username");
        int ruleAmount = Integer.parseInt(body.get("ruleAmount"));
        
        comController.defineBudgetControl(commissionerUsername,ruleAmount);
    }



}
