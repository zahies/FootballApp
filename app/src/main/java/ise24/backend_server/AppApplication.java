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

    public static void main(String[] args) throws UserInformationException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException, NoPermissionException, SQLException, UserIsNotThisKindOfMemberException, NoConnectionException, EmptyPersonalPageException {
        FootballManagmentSystem fms = FootballManagmentSystem.getInstance();
        SpringApplication app = new SpringApplication(AppApplication.class);
        app.setDefaultProperties(Collections
                .singletonMap("server.port", "8080"));
        app.run(args);
    }

}
