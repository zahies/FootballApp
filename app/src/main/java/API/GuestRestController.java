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
            LinkedList<String> usersWhoAreLoggedIn = new LinkedList<>(); /** username */
            LinkedList<Member> membersAccounts = guestController.login(body.get("username"),body.get("password"));
            JSONObject json2 = new JSONObject();
            for (Member member : membersAccounts) {
                if(member instanceof Player){
                    json2.put("Player","true");
                }else if(member instanceof TeamManager){
                    json2.put("TeamManager","true");
                }else if(member instanceof Referee){
                    json2.put("Referee","true");
                }else if(member instanceof Fan){
                    json2.put("Fan","true");
                }else if(member instanceof TeamOwner){
                    json2.put("TeamOwner","true");
                }else if(member instanceof Commissioner){
                    json2.put("Commissioner","true");
                }else if(member instanceof SystemManager){
                    json2.put("SystemManager","true");
                }else if(member instanceof Coach){
                    json2.put("Coach","true");
                }
            }
            json2.put("username", body.get("username"));
            String message = json2.toString(2);
            usersWhoAreLoggedIn.add(body.get("username"));
            return message;
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

}
