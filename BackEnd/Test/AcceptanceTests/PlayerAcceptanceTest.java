package AcceptanceTests;

import Domain.PersonalPages.APersonalPageContent;
import Domain.PersonalPages.NewsContent;
import Domain.PersonalPages.StatisticContent;
import Domain.Users.Player;
import Service.PlayerController;
import org.junit.Test;
import static org.junit.Assert.*;

public class PlayerAcceptanceTest {


    @Test
    public void positiveAddContentToPage(){
        PlayerController playerPositiveController = new PlayerController();
        Player playerPositive = new Player("Yossi","Yossi Benayoun", 248765,"fs@!#'",24214,"Striker",null);
        playerPositiveController.createPersonalPage(playerPositive);
        APersonalPageContent pageContent = new StatisticContent();
        ((StatisticContent)pageContent).addStatistic("Goals",120);
        playerPositiveController.addContentToPage(playerPositive,pageContent);

        assertEquals(1,playerPositive.getInfo().getPageContent().size());
    }

    @Test
    public void negativeAddContentToPage(){
        PlayerController playerPositiveController = new PlayerController();
        Player playerPositive = new Player("Revivo","Haim Revivo", 489498,"sda@!#",4894,"Striker",null);
        APersonalPageContent pageContent = new NewsContent();
        ((NewsContent)pageContent).setContent("Life At Fenerbeche");
        ((NewsContent)pageContent).setTitle("Nice");

        assertFalse(playerPositiveController.addContentToPage(playerPositive,pageContent));

    }

    @Test
    public void positiveChangeUserName(){
        PlayerController playerPositiveController = new PlayerController();
        Player playerNegative = new Player("Revivo","Haim Revivo", 489498,"sda@!#",4894,"Striker",null);
        Player playerPositive = new Player("Yossi","Yossi Benayoun", 248765,"fs@!#'",24214,"Striker",null);

        assertTrue(playerPositiveController.changeUserName(playerPositive,"RevivoTheKing"));
    }

    @Test
    public void negativeChangeUserName(){
        PlayerController playerPositiveController = new PlayerController();
        Player playerNegative = new Player("Revivo","Haim Revivo", 489498,"sda@!#",4894,"Striker",null);
        Player playerPositive = new Player("Yossi","Yossi Benayoun", 248765,"fs@!#'",24214,"Striker",null);

        assertFalse(playerPositiveController.changeUserName(playerNegative,"Yossi"));

    }

}
