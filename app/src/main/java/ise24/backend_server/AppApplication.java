package ise24.backend_server;

import API.GuestRestController;
import Domain.Alerts.ChangedGameAlert;
import Domain.Alerts.IAlert;
import Domain.FootballManagmentSystem;
import Domain.SeasonManagment.Game;
import Domain.SeasonManagment.Team;
import Domain.Users.*;
import FootballExceptions.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.Date;

@SpringBootApplication
@ComponentScan(basePackageClasses = GuestRestController.class)
public class AppApplication {

    public static void main(String[] args) {



        RefereeType refereeType = RefereeType.Main;
        RefereeType refereeType2 = RefereeType.Secondary;
        Referee refereeTest = new Referee("Yossi43", "Yossi", 1234, "0101", refereeType);
        Referee refereeTest2 = new Referee("Paul33","Paul",1235,"0102",refereeType2);
        TeamOwner ownerHome = new TeamOwner("yakir","yaki",35,"3535");
        TeamOwner ownerOut = new TeamOwner("yam", "yami", 32, "3536");
        Team teamHome = new Team("Tel Aviv", ownerHome);
        Team teamOut = new Team("Haifa", ownerOut);
        Date  date1 =  new Date("31/03/2020");
        Game game = new Game(teamOut, teamHome, date1, refereeTest, refereeTest2, null);






        Member member = new Player("Ohana", "FSAF", 416, "123", 214, "GSDG", null);
        Member teamowner = new TeamOwner("Moshe", "DASD", 123, "asd");
        Team team = new Team("Bet", ((TeamOwner) teamowner));
        try {
            ((TeamOwner) teamowner).assignNewTeamManager(member, 12431);
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
