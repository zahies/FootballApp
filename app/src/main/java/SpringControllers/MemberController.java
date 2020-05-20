package SpringControllers;

import Domain.FootballManagmentSystem;
import Domain.Users.Member;

public class MemberController {


    Member member;


    public void logOut(Member member) {
        FootballManagmentSystem system = FootballManagmentSystem.getInstance();
        member.logOut();
    }


}
