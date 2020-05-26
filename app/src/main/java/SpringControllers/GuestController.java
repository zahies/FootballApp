package SpringControllers;

import Domain.Searcher.Searcher;
import Domain.Users.Fan;
import Domain.Users.Guest;
import Domain.Users.Member;
import FootballExceptions.UserInformationException;

import java.util.HashSet;
import java.util.LinkedList;


public class GuestController {
    public LinkedList<Member> login(String username, String password) throws UserInformationException {

        return new Guest().login(username, password);

    }

    public void register(Guest guest, String userName, String realname, String pass, int id, String type) {
        try {
            guest.register(userName, realname, pass, id, null);
        } catch (UserInformationException e) {
            System.out.println("user name allready taken");
        }
    }

    public HashSet<Object> search(Guest guest, String str, Searcher searcher) {
        return guest.search(str, searcher);

    }


}
