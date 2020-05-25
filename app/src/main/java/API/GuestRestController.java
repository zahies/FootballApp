package API;

import Domain.Users.Member;
import Domain.Users.Player;
import Domain.Users.TeamManager;
import FootballExceptions.UserInformationException;
import SpringControllers.GuestController;
import SpringControllers.MemberController;
import SpringControllers.PlayerController;
import SpringControllers.TeamManagerController;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    public Map<String, String> login(@RequestBody Map <String,String> body, final HttpServletResponse response) throws IOException {
        try {
            LinkedList <MemberController> memberControllers = new LinkedList<>();
            LinkedList<Member> membersAccounts = guestController.login(body.get("username"),body.get("password"));
            Map <String,String> returnVal = new HashMap<>();

            for (Member member : membersAccounts) {
                if(member instanceof Player){
                    returnVal.put("Player","true");
                }else if(member instanceof TeamManager){
                    returnVal.put("TeamManager","true");
                }
            }


            return returnVal;
        } catch (UserInformationException e) {
           response.sendError(HttpServletResponse.SC_CONFLICT,"Incorrect Login Details");
           return null;
        }
    }


    @GetMapping("/{instance}")
    public String getHomepageByInstance(@PathVariable Member instance){
        return "FSD";
    }

}
