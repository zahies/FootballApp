package AcceptanceTests;

import Domain.FootballManagmentSystem;
import Domain.PersonalPages.APersonalPageContent;
import Domain.PersonalPages.NewsContent;
import Domain.PersonalPages.StatisticContent;
import Domain.SeasonManagment.IAsset;
import Domain.SeasonManagment.Team;
import Domain.Users.*;
import FootballExceptions.*;
import Service.PlayerController;
import Service.TeamManagerController;
import org.junit.Test;
import static org.junit.Assert.*;

public class TeamManagerAcceptanceTest {


    @Test
    public void positiveHireCoach() throws UserInformationException, UnauthorizedTeamOwnerException, MemberIsAlreadyTeamManagerException, TeamOwnerWithNoTeamException, MemberIsAlreadyTeamOwnerException, InactiveTeamException, UnauthorizedPageOwnerException, PersonalPageYetToBeCreatedException, UserIsNotThisKindOfMemberException {
        TeamManagerController positive = new TeamManagerController();
        TeamOwner teamOwner = new TeamOwner("Tabib","eli-tabib",35325,"$235");
        Team team = new Team("Hapoel Kfar Saba",teamOwner);
        Member member = new Player("Yossi","Yossi Benayoun", 248765,"fs@!#'",24214,"Striker",null);
        teamOwner.assignNewTeamManager(member,37249);
        teamOwner.editManagerPermissions(member,"Hire Coach",true);
        IAsset fitnessCoach = new Coach("asi","asi cohen",2341,"3124",41244,null,CoachRole.FitnessCoach);
        positive.hireCoach(((TeamManager)FootballManagmentSystem.getInstance().getMemberInstanceByKind(member.getName(),"Team Manager")),fitnessCoach);
        assertEquals(1,team.getTeamCoaches().size());
    }

    @Test
    public void negativeHireCoach() throws UserInformationException, UnauthorizedTeamOwnerException, MemberIsAlreadyTeamManagerException, TeamOwnerWithNoTeamException, MemberIsAlreadyTeamOwnerException, InactiveTeamException, UnauthorizedPageOwnerException, PersonalPageYetToBeCreatedException, UserIsNotThisKindOfMemberException {
        TeamManagerController negative = new TeamManagerController();
        TeamOwner teamOwner = new TeamOwner("Tabib","eli-tabib",35325,"$235");
        Team team = new Team("Hapoel Kfar Saba",teamOwner);
        Member member = new Player("Yossi2131421414","Yossi Benayoun", 248765,"fs@!#'",24214,"Striker",null);
        teamOwner.assignNewTeamManager(member,37249);
        IAsset fitnessCoach = new Coach("asi","asi cohen",2341,"3124",41244,null,CoachRole.FitnessCoach);
        negative.hireCoach(((TeamManager)FootballManagmentSystem.getInstance().getMemberInstanceByKind(member.getName(),"Team Manager")),fitnessCoach);
        assertEquals(0,team.getTeamCoaches().size());
    }

}
