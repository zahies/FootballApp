package API;


import DataAccess.Exceptions.NoConnectionException;
import FootballExceptions.EmptyPersonalPageException;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import SpringControllers.AlertsController;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@CrossOrigin
@RequestMapping(value = {"footballapp/alerts","footballapp/referee/alerts"})
@RestController
public class AlertsRestController {


    private final AlertsController alertsController;
    private final Map<String,SseEmitter> sses = new ConcurrentHashMap<>();


    public AlertsRestController() {
        this.alertsController = new AlertsController();
    }


    @GetMapping("/hi")
    public String test(){
        return "HI";
    }


    /** alert for online user */
    @GetMapping("/myalerts/{username}")
    public Map<String, List<String>> sendAlerts(@PathVariable String username,final HttpServletResponse response) throws IOException {
        Map<String, List<String>> alertsJson = null;
        try{
           alertsJson = alertsController.showAlerts(username);
        } catch (UserIsNotThisKindOfMemberException e) {
            e.printStackTrace();
        } catch (NoPermissionException e) {
            e.printStackTrace();
        } catch (UserInformationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (EmptyPersonalPageException e) {
            e.printStackTrace();
        } catch (NoConnectionException e) {
            e.printStackTrace();
        }
        if (alertsJson.get("num").size() == 0){
            response.sendError(HttpServletResponse.SC_CONFLICT,"No more alerts");
        }
        return alertsJson;
    }






    @RequestMapping("/{p}")
    public String getHomepageByInstance(@PathVariable String p){
        return p + " Is Wrong Path";
    }
}
