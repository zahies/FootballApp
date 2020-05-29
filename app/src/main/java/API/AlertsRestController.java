package API;


import SpringControllers.AlertsController;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

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
    public Map<String, List<String>> sendAlerts(@PathVariable String username){
        Map<String, List<String>> alertsJson= alertsController.showAlerts(username);
        return alertsJson;
    }






    @RequestMapping("/{p}")
    public String getHomepageByInstance(@PathVariable String p){
        return p + " Is Wrong Path";
    }
}
