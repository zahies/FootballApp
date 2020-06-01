package API;



import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import Domain.ErrorLog;
import Domain.Events.Error_Loger;
import Domain.FootballManagmentSystem;
import Domain.SeasonManagment.Leaugue;
import Domain.SystemLog;
import FootballExceptions.*;
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
                succeeded = comController.responseToRegistrationRequest(commissionerUsername,teamName,true);
            }else{
                succeeded = comController.responseToRegistrationRequest(commissionerUsername,teamName,false);
                response.setStatus(HttpServletResponse.SC_OK, "Score Policy Added Successfully ! ");
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
            response.setStatus(HttpServletResponse.SC_OK, "dhdf ! ");
        }
    }


    @CrossOrigin
    @GetMapping("/leagues/{leagueID}")
    public Map<Integer,LinkedList<String>> getleagueViaId(@PathVariable String leagueID, final HttpServletResponse response) throws IOException {
        boolean succeeded = false;
        String alert = "";
        Map<Integer,LinkedList<String>> league = null;
        try{
            league = comController.getLeague(leagueID);
            succeeded = true;
        } catch (NoPermissionException e) {
            e.printStackTrace();
            alert = e.getMessage();
        } catch (UserInformationException e) {
            e.printStackTrace();
            alert = e.getMessage();
        } catch (UserIsNotThisKindOfMemberException e) {
            e.printStackTrace();
            alert = e.getMessage();
        } catch (SQLException e) {
            e.printStackTrace();
            alert = e.getMessage();
        } catch (NoConnectionException e) {
            e.printStackTrace();
            alert = e.getMessage();
        } catch (LeagueIDAlreadyExist leagueIDAlreadyExist) {
            leagueIDAlreadyExist.printStackTrace();
            alert = leagueIDAlreadyExist.getMessage();
        } catch (SeasonYearAlreadyExist seasonYearAlreadyExist) {
            seasonYearAlreadyExist.printStackTrace();
            alert = seasonYearAlreadyExist.getMessage();
        }
        if (!succeeded){
            response.sendError(HttpServletResponse.SC_CONFLICT,alert);
            ErrorLog.getInstance().UpdateLog("The error is: " + alert);
        }
        return league;
    }


    @CrossOrigin
    @PostMapping("/addScorePolicy")
    public void addScorePolicy(@RequestBody Map<String,String> body, final HttpServletResponse response) throws IOException, UserIsNotThisKindOfMemberException {
        boolean succeeded = false;
        String commissionerUsername = body.get("username");
        String leagueId = (body.get("leagueID"));
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
        } catch (NoConnectionException | mightBeSQLInjectionException | DuplicatedPrimaryKeyException e) {
            e.printStackTrace();
            alert = e.getMessage();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (succeeded){
            /**pop up success*/
            response.setStatus(HttpServletResponse.SC_OK, "Score Policy Added Successfully ! ");
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
        String leagueId = body.get("leagueID");
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
        } catch (NoConnectionException | mightBeSQLInjectionException | DuplicatedPrimaryKeyException e) {
            e.printStackTrace();
            alert = e.getMessage();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (succeeded) {
            /**pop up success*/
            response.setStatus(HttpServletResponse.SC_OK, "Place Teams Policy Added Successfully ! ");
        } else {
            /**pop up failed*/
            response.sendError(HttpServletResponse.SC_CONFLICT, alert);
            ErrorLog.getInstance().UpdateLog("The error is: " + alert);
        }

    }


    @CrossOrigin
    @PostMapping("/setViaMail")
    public void setViaMail(@RequestBody Map<String,String> body, final HttpServletResponse response) throws IOException {
        boolean succeeded=false;
        String alert="";
        String commissionerUsername = body.get("username");
        String mail = body.get("mail");
        try {
            comController.setViaMail(commissionerUsername,mail);
            succeeded = true;
        } catch (UserIsNotThisKindOfMemberException e) {
            e.printStackTrace();
            alert = e.getMessage();
        } catch (NoPermissionException e) {
            e.printStackTrace();
            alert = e.getMessage();
        } catch (UserInformationException e) {
            e.printStackTrace();
            alert = e.getMessage();
        } catch (SQLException e) {
            e.printStackTrace();
            alert = e.getMessage();
        } catch (EmptyPersonalPageException e) {
            e.printStackTrace();
            alert = e.getMessage();
        } catch (NoConnectionException e) {
            e.printStackTrace();
            alert = e.getMessage();
        } catch (mightBeSQLInjectionException e) {
            e.printStackTrace();
        } catch (DuplicatedPrimaryKeyException e) {
            e.printStackTrace();
        }
        if (succeeded) {
            /**pop up success*/
            response.setStatus(HttpServletResponse.SC_OK, "RECORDED");
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (succeeded){
            /**pop up success*/
            response.setStatus(HttpServletResponse.SC_OK, "Your Rule Added Successfully ! ");
        }else {
            /**pop up failed*/
            response.sendError(HttpServletResponse.SC_CONFLICT,"Incorrect Details");
            ErrorLog.getInstance().UpdateLog("The error is: " + alert);
        }
    }

    @CrossOrigin
    @GetMapping("/leagues")
    public List<String> getLeagues(){
        FootballManagmentSystem system = FootballManagmentSystem.getInstance();
        List<Leaugue> leaugues = system.getAllLeagus();
        List<String> leauguesID = new LinkedList<>();
        String message = "[";
        int i = 0;
        for (Leaugue league:leaugues) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("leagueID", league.getObjectID());
            leauguesID.add(league.getObjectID().toString());
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
    @GetMapping("/proveTeam/{commUserName}")
    public LinkedList<Map<String,String>> getReplyForRegistration(@PathVariable String commUserName, final HttpServletResponse response) throws IOException{
        LinkedList<Map<String,String>> map = new LinkedList<>();
        boolean succeeded=false;
        try {
            map = comController.getReplyForRegistration(commUserName);
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
        if (map.size() == 0){
            response.sendError(HttpServletResponse.SC_CONFLICT,"Incorrect Details");
        }
        return map;
    }

}
