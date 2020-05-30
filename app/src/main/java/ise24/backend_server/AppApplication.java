package ise24.backend_server;

import API.GuestRestController;
import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.UsersDAL.MembersDAL;
import Domain.Alerts.ChangedGameAlert;
import Domain.Alerts.IAlert;
import Domain.Alerts.RegistrationRequestAlert;
import Domain.FootballManagmentSystem;
import Domain.SeasonManagment.Leaugue;
import Domain.SeasonManagment.Team;
import Domain.Users.Commissioner;
import Domain.Users.Member;
import Domain.Users.Player;
import Domain.Users.TeamOwner;
import FootballExceptions.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;

@SpringBootApplication
@ComponentScan(basePackageClasses = GuestRestController.class)
public class AppApplication {

    public static void main(String[] args) throws UserInformationException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException, NoPermissionException, SQLException, UserIsNotThisKindOfMemberException, NoConnectionException {
        Member mem = new MembersDAL().select("Roni23",true);
        Member member = new Player("Ohana","FSAF",416,"123",214,"GSDG",null);
        TeamOwner teamowner = new TeamOwner("Moshe","DASD",123,"asd");
        Member com = new Commissioner("zaza",12,"123","zahi zahi");
        FootballManagmentSystem system = FootballManagmentSystem.getInstance();
        Leaugue leaugue = new Leaugue();
        IAlert regAlert = new RegistrationRequestAlert("hpoel",leaugue,1900,teamowner);
        com.addAlert(regAlert);
        //system.addMember(com);
        Team team = new Team("Bet",((TeamOwner)teamowner));
        try {
            ((TeamOwner)teamowner).assignNewTeamManager(member,12431);
        } catch (MemberIsAlreadyTeamOwnerException e) {
            e.printStackTrace();
        } catch (MemberIsAlreadyTeamManagerException e) {
            e.printStackTrace();
        } catch (TeamOwnerWithNoTeamException e) {
            e.printStackTrace();
        } catch (UnauthorizedTeamOwnerException e) {
            e.printStackTrace();
        } catch (UserInformationException e) {
            e.printStackTrace();
        } catch (InactiveTeamException e) {
            e.printStackTrace();
        }
        SpringApplication app = new SpringApplication(AppApplication.class);
        app.setDefaultProperties(Collections
                .singletonMap("server.port", "8080"));
        app.run(args);
    }

}
