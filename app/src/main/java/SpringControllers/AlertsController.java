package SpringControllers;

import DataAccess.UsersDAL.MembersDAL;
import Domain.Alerts.GameEventAlert;
import Domain.Alerts.IAlert;
import Domain.Events.Foul;
import Domain.Events.IEvent;
import Domain.FootballManagmentSystem;
import Domain.Users.Member;
import Domain.Users.Player;
import org.json.JSONObject;

import java.util.Queue;

public class AlertsController {





    /** alert for online user */
    public String showAlerts(String username) {
        //Member member = new MembersDAL().select(username);
        Member member = new Player("Ohana","FSAF",416,"123",214,"GSDG",null);
        IEvent foul = new Foul(32);
        GameEventAlert al = new GameEventAlert(2,foul);

        Queue<IAlert> alerts = member.getAlertsList();
        String message = "";

        /** via MAIL */
        if (member.isAlertViaMail()){
            FootballManagmentSystem system = FootballManagmentSystem.getInstance();
            for (IAlert alert:alerts) {
                system.sendInvitationByMail(member.getMailAddress(),alert.getClass().getSimpleName(),alert.toString());
                member.deleteSpecificAlert(alert);
            }

        /** via APP */
        }else{
            alerts.add(al);
            if (alerts.size() == 0){
                message = " Have no messages.";
            }else{
                if (alerts.size() > 1){
                    message = "[";
                }
                for (IAlert alert:alerts) {
                    int i = 0;
                    JSONObject json = new JSONObject();
                    json.put(alert.getClass().getSimpleName(),alert.toString());
                    message += json.toString(2);
                    i++;
                    if (i<alerts.size()){
                        message+=",";
                    }
                    member.deleteSpecificAlert(alert);
                }
                if (alerts.size() > 1){
                    message += "]";
                }
            }
        }
        return message;
    }






}
