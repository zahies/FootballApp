package Domain.Alerts;

import Domain.SeasonManagment.Team;
import Domain.SeasonManagment.TeamStatus;

import java.util.UUID;

public class TeamManagementAlert implements IAlert {


    private UUID objectID;
    TeamStatus teamStatus;
    String message;
    private boolean hadSent;
/***FOR DB*/
    public TeamManagementAlert(UUID objectID, TeamStatus teamStatus, String message,boolean hadSent) {
        this.objectID = objectID;
        this.teamStatus = teamStatus;
        this.message = message;
        this.hadSent = hadSent;
    }


    public TeamManagementAlert(String message, TeamStatus teamStatus) {
        objectID = UUID.randomUUID();
        this.message = message;
        hadSent = false;
        this.teamStatus = teamStatus;
    }

    public TeamManagementAlert(TeamStatus teamStatus, Team team) {
        this.teamStatus = teamStatus;
        objectID = UUID.randomUUID();
        hadSent = false;
        message = "Team: " + team.getName() + " status changed to:" + teamStatus.toString();
    }

    public UUID getObjectID() {
        return objectID;
    }

    @Override
    public String toString() {
        return "team status = " + teamStatus + " , message = " + message ;
    }


    @Override
    public String view() {
        return this.toString();
    }

    @Override
    public String getType() {
        return "Team Management Alert";
    }

    public TeamStatus getTeamStatus() {
        return teamStatus;
    }

    public String getMessage() {
        return message;
    }
    @Override
    public void setNewID() {
        objectID = UUID.randomUUID();
    }

    public boolean isHadSent() {
        return hadSent;
    }

    public void setHadSent(boolean hadSent) {
        this.hadSent = hadSent;
    }

}
