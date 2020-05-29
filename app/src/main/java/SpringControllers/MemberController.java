package SpringControllers;

import DataAccess.UsersDAL.MembersDAL;
import Domain.FootballManagmentSystem;
import Domain.Users.Member;

public class MemberController {


    Member member;


    public boolean logOut(Member member) {
        boolean flag = false;
//        Member member = (Member) new MembersDAL().select(username);
        FootballManagmentSystem system = FootballManagmentSystem.getInstance();
        member.logOut();
        flag = true;
        return flag;
    }


}
