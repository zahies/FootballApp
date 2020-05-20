package AcceptanceTests;
import Domain.PersonalPages.*;
import Domain.Users.Coach;
import Domain.Users.CoachRole;
import Domain.Users.Player;
import Service.CoachController;
import Service.PlayerController;
import org.junit.Test;
import static org.junit.Assert.*;

public class CoachAcceptanceTest {

    @Test
    public void positiveAddContentToPage() {
        CoachController coachPositiveController = new CoachController();
        Coach positiveCoach = new Coach("Wenger", "arsne wneger", 2314, "#!@$124", 1241242, null, CoachRole.HeadCoach);
        coachPositiveController.createPersonalPage(positiveCoach);
        APersonalPageContent pageContent = new CareerContent();
        ((CareerContent) pageContent).addToCareer("Arsenal", "Head-Coach", 1996);
        coachPositiveController.addContentToPage(positiveCoach, pageContent);

        assertEquals(1, positiveCoach.getInfo().getPageContent().size());
    }

    @Test
    public void negativeAddContentToPage() {
        CoachController coachNegativeController = new CoachController();
        Coach NegativeCoach = new Coach("Mourinho", "zoze mourinho", 45564, "GDS3543", 11152, null, CoachRole.HeadCoach);
        APersonalPageContent pageContent = new ProfileContent();
        ((ProfileContent) pageContent).addFeatureToProfile("PL Champions","3");
        assertFalse(coachNegativeController.addContentToPage(NegativeCoach, pageContent));
    }

    @Test
    public void positiveChangeUserName(){
        CoachController coachPositiveController = new CoachController();
        Coach NegativeCoach = new Coach("Mourinho", "zoze mourinho", 45564, "GDS3543", 11152, null, CoachRole.HeadCoach);
        Coach positiveCoach = new Coach("Wenger", "arsne wneger", 2314, "#!@$124", 1241242, null, CoachRole.HeadCoach);

        assertTrue(coachPositiveController.changeUserName(positiveCoach,"Wenger3F2"));
    }

    @Test
    public void negativeChangeUserName(){
        CoachController coachNegativeController = new CoachController();
        Coach NegativeCoach = new Coach("Mourinho", "zoze mourinho", 45564, "GDS3543", 11152, null, CoachRole.HeadCoach);
        Coach positiveCoach = new Coach("Wenger", "arsne wneger", 2314, "#!@$124", 1241242, null, CoachRole.HeadCoach);

        assertFalse(coachNegativeController.changeUserName(NegativeCoach,"Wenger"));
    }

    @Test
    public void logOut(){
        CoachController coachNegativeController = new CoachController();
        Coach NegativeCoach = new Coach("Mourinho", "zoze mourinho", 45564, "GDS3543", 11152, null, CoachRole.HeadCoach);
        coachNegativeController.logOut(NegativeCoach);
    }

}
