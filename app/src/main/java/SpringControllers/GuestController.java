package SpringControllers;

import Domain.Searcher.Searcher;
import Domain.Users.*;
import FootballExceptions.UserInformationException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.LinkedList;


public class GuestController {
    public String login(String username, String password) throws UserInformationException {
        LinkedList<Member> membersAccounts = new Guest().login(username, password);
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
            member.setActive(true);
        }
        json2.put("username", username);
        String message = json2.toString(2);
        return message;

    }

    public boolean register(Guest guest, String userName, String realname, String pass, int id, String type) { //fixme ??
        boolean flag = false;
        try {
            guest.register(userName, realname, pass, id, null);
            flag = true;
        } catch (UserInformationException e) {
            System.out.println("user name allready taken");
        }
        return flag;
    }

    public HashSet<Object> search(Guest guest, String str, Searcher searcher) {
        return guest.search(str, searcher);

    }


}
