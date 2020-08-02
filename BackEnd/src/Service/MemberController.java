package Service;

import Domain.FootballManagmentSystem;
import Domain.Users.Member;

public abstract class MemberController {


    public void logOut(Member member) {
        FootballManagmentSystem system = FootballManagmentSystem.getInstance();
        member.logOut();
    }


}
