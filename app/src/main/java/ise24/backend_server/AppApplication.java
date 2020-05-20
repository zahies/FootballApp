package ise24.backend_server;

import API.GuestRestController;
import Domain.FootballManagmentSystem;
import Domain.SeasonManagment.Team;
import Domain.Users.Member;
import Domain.Users.Player;
import Domain.Users.TeamOwner;
import FootballExceptions.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = GuestRestController.class)
public class AppApplication {

    public static void main(String[] args) {
        Member member = new Player("Ohana","FSAF",416,"123",214,"GSDG",null);
        Member teamowner = new TeamOwner("Moshe","DASD",123,"asd");
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
        SpringApplication.run(AppApplication.class, args);
    }

}
