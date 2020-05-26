package API;

import Domain.Users.*;
import FootballExceptions.UserInformationException;
import SpringControllers.GuestController;
import SpringControllers.MemberController;
import SpringControllers.PlayerController;
import SpringControllers.TeamManagerController;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@RequestMapping("footballapp/guest")
@RestController
public class GuestRestController {

    private final GuestController guestController;
    private LinkedList<String> usersWhoAreLoggedIn;

    @Autowired
    public GuestRestController() {
        guestController = new GuestController();
    }

    @GetMapping
    public String get(){
        System.out.println("DSDDASD");
        return "ASDF";
    }

    @CrossOrigin
    @PostMapping("/login")
    @JsonIgnore
    public String login(@RequestBody Map <String,String> body, final HttpServletResponse response) throws IOException {
        try {
            usersWhoAreLoggedIn = new LinkedList<>(); /** username */
            String json = guestController.login(body.get("username"),body.get("password"));
            usersWhoAreLoggedIn.add(body.get("username"));
            return json;
        } catch (UserInformationException e) {
           response.sendError(HttpServletResponse.SC_CONFLICT,"Incorrect Login Details");
           return null;
        }
    }


    @GetMapping("/{user}")
    public String getHomepageByInstance(@PathVariable String user){
        return user;
    }



    @RequestMapping
    public String wrongPath(){
        return "Wrong Request !! ";
    }

    public LinkedList<String> getUsersWhoAreLoggedIn() {
        return usersWhoAreLoggedIn;
    }




}
