package API;



import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import Domain.ErrorLog;
import Domain.Events.Error_Loger;
import Domain.FootballManagmentSystem;
import Domain.SeasonManagment.Leaugue;
import Domain.SystemLog;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import SpringControllers.CommissionerController;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@CrossOrigin
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
    @PostMapping("/applyRequest")
    public void applyRequestForRegistration(@RequestBody Map<String,String> body, final HttpServletResponse response) throws IOException{
        boolean succeeded = false;
        String alert = "";
        String commissionerDecision = body.get("apply");
        String commissionerUsername = body.get("username");
        String teamName = body.get("teamname");
        try{
            if (commissionerDecision.equals("true")){    /** the commissioner decided to confirm the registration request */
                succeeded = comController.responseToRegistrationRequest(commissionerUsername,teamName);
            }
        } catch (UserIsNotThisKindOfMemberException e) {
            e.printStackTrace();
            alert = e.getMessage();
        } catch (NoPermissionException e) {
            e.printStackTrace();
            alert = e.getMessage();
        } catch (UserInformationException e) {
            e.printStackTrace();
            alert = e.getMessage();
        } catch (NoConnectionException e) {
            e.printStackTrace();
            alert = e.getMessage();
        } catch (mightBeSQLInjectionException e) {
            e.printStackTrace();
            alert = e.getMessage();
        } catch (DuplicatedPrimaryKeyException e) {
            e.printStackTrace();
            alert = e.getMessage();
        }
        if (succeeded){
            /**pop up success*/
            response.setStatus(HttpServletResponse.SC_ACCEPTED, "Score Policy Added Successfully ! ");
        }else {
            /**pop up failed*/
            response.sendError(HttpServletResponse.SC_CONFLICT,alert);
            ErrorLog.getInstance().UpdateLog("The error is: " + alert);

        }
    }



    @CrossOrigin
    @PostMapping("/addScorePolicy")
    public void addScorePolicy(@RequestBody Map<String,String> body, final HttpServletResponse response) throws IOException, UserIsNotThisKindOfMemberException {
        boolean succeeded = false;
        String commissionerUsername = body.get("username");
        int leagueId = Integer.parseInt(body.get("leagueID"));
        int year = Integer.parseInt(body.get("year"));
        int winVal = Integer.parseInt(body.get("winval"));
        int loseVal = Integer.parseInt(body.get("loseval"));
        int drawVal = Integer.parseInt(body.get("drawval"));
        String alert = "";
        try {
            succeeded = comController.setNewScorePolicy(commissionerUsername,leagueId,year,winVal,loseVal,drawVal);
        } catch (NoPermissionException e) {
            e.printStackTrace();
            alert = e.getMessage();
        } catch (UserInformationException e) {
            e.printStackTrace();
            alert = e.getMessage();
        } catch (NoConnectionException e) {
            e.printStackTrace();
            alert = e.getMessage();
        }
        if (succeeded){
            /**pop up success*/
            response.setStatus(HttpServletResponse.SC_ACCEPTED, "Score Policy Added Successfully ! ");
        }else {
            /**pop up failed*/
            response.sendError(HttpServletResponse.SC_CONFLICT,alert);
            ErrorLog.getInstance().UpdateLog("The error is: " + alert);
        }
    }

    @CrossOrigin
    @PostMapping("/addTeamsPolicy")
    public void addPlaceTeamsPolicy(@RequestBody Map<String,String> body, final HttpServletResponse response) throws IOException {
        boolean succeeded = false;
        String commissionerUsername = body.get("username");
        int leagueId = Integer.parseInt(body.get("leagueID"));
        int year = Integer.parseInt(body.get("year"));
        int numGames = Integer.parseInt(body.get("numgames"));
        String alert = "";
        try {
            succeeded = comController.setNewPlaceTeamsPolicy(commissionerUsername, leagueId, year, numGames);
        } catch (UserIsNotThisKindOfMemberException e) {
            e.printStackTrace();
            alert = e.getMessage();
        } catch (NoPermissionException e) {
            e.printStackTrace();
            alert = e.getMessage();
        } catch (UserInformationException e) {
            e.printStackTrace();
            alert = e.getMessage();
        } catch (NoConnectionException e) {
            e.printStackTrace();
            alert = e.getMessage();
        }
        if (succeeded) {
            /**pop up success*/
            response.setStatus(HttpServletResponse.SC_ACCEPTED, "Place Teams Policy Added Successfully ! ");
        } else {
            /**pop up failed*/
            response.sendError(HttpServletResponse.SC_CONFLICT, alert);
            ErrorLog.getInstance().UpdateLog("The error is: " + alert);
        }

    }



    /**
     * UC 9.8 - Define rules about BUDGET CONTROL
     */
    @CrossOrigin
    @PostMapping("/addCommissionerRule")
    public void addCommissionerRule(@RequestBody Map<String,String> body, final HttpServletResponse response) throws IOException {
        boolean succeeded=false;
        String commissionerUsername = body.get("username");
        String description = body.get("description");
        int ruleAmount = Integer.parseInt(body.get("ruleAmount"));
        String alert = "";
        try {
            succeeded =  comController.defineBudgetControl(commissionerUsername,ruleAmount,description);
        } catch (UserIsNotThisKindOfMemberException e) {
            e.printStackTrace();
            alert = e.getMessage();
        } catch (NoPermissionException e) {
            e.printStackTrace();
            alert = e.getMessage();
        } catch (UserInformationException e) {
            e.printStackTrace();
            alert = e.getMessage();
        } catch (NoConnectionException e) {
            e.printStackTrace();
            alert = e.getMessage();
        }
        if (succeeded){
            /**pop up success*/
            response.setStatus(HttpServletResponse.SC_ACCEPTED, "Your Rule Added Successfully ! ");
        }else {
            /**pop up failed*/
            response.sendError(HttpServletResponse.SC_CONFLICT,"Incorrect Details");
            ErrorLog.getInstance().UpdateLog("The error is: " + alert);
        }
    }

    @CrossOrigin
    @GetMapping("/leagues")
    public List<Integer> getLeagues(){
        FootballManagmentSystem system = FootballManagmentSystem.getInstance();
        List<Leaugue> leaugues = system.getAllLeagus();
        List<Integer> leauguesID = new LinkedList<>();
        Leaugue leaugue = new Leaugue();
        leaugue.setId(2);
        Leaugue leaugue1 = new Leaugue();
        leaugue1.setId(22);
        leauguesID.add(leaugue.getID());
        leauguesID.add(leaugue1.getID());
        leaugues.add(leaugue1);

        String message = "[";
        int i = 0;
        for (Leaugue league:leaugues) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("leagueID", league.getID());
            message+= jsonObject.toString(2);
            i++;
            if (i<leaugues.size()){
                message+=",";
            }
        }
        message += "]";

        return leauguesID;
    }



    @CrossOrigin
    @GetMapping("/proveTeam/{user}")
    public Map<String,String> getReplyForRegistration(@PathVariable String user, final HttpServletResponse response) throws IOException{
        Map<String,String> map = new HashMap<>();
        boolean succeeded=false;
        try {
            map = comController.getReplyForRegistration(user);
            succeeded = true;
        } catch (UserIsNotThisKindOfMemberException e) {
            e.printStackTrace();
        } catch (NoPermissionException e) {
            e.printStackTrace();
        } catch (UserInformationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoConnectionException | mightBeSQLInjectionException | DuplicatedPrimaryKeyException e) {
            e.printStackTrace();
        }
        if (!succeeded){
            response.sendError(HttpServletResponse.SC_CONFLICT,"Incorrect Details");
        }
        return map;
    }

}
