package API;

import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import Domain.FootballManagmentSystem;
import Domain.Users.Member;
import FootballExceptions.NoPermissionException;
import FootballExceptions.UserInformationException;
import FootballExceptions.UserIsNotThisKindOfMemberException;
import SpringControllers.MemberController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
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
    public List<Member> logOut(@PathVariable String userName) throws mightBeSQLInjectionException, DuplicatedPrimaryKeyException, NoPermissionException, SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException {
        List<Member> members =FootballManagmentSystem.getInstance().getMemberByUserName(userName);
        for (Member member: members) {
            memberController.logOut(member.getName());
        }
        return members;
    }
}
