package API;

import Domain.FootballManagmentSystem;
import Domain.Users.Member;
import SpringControllers.MemberController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RequestMapping("footballapp/logout")
@RestController
public class MemberRestController {

    private final MemberController memberController;

    @Autowired
    public MemberRestController() {
        this.memberController = new MemberController();
    }

    @PutMapping("/{userName}")
    public List<Member> logOut(@PathVariable String userName){
        List<Member> members =FootballManagmentSystem.getInstance().getMemberByUserName(userName);
        for (Member member: members) {
            memberController.logOut(member.getName());
        }
        return members;
    }
}
