package Domain.Users;

import Domain.Alerts.FinancialAlert;
import Domain.Alerts.IAlert;
import Domain.Alerts.RegistrationRequestAlert;
import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.UsersDAL.TeamOwnersDAL;
import Domain.FootballManagmentSystem;
import Domain.SeasonManagment.*;
import FootballExceptions.*;

import java.sql.SQLException;
import java.util.*;

public class TeamOwner extends Member {

    Team team;
    FootballManagmentSystem system = FootballManagmentSystem.getInstance();

    public TeamOwner(String name, String password, String real_Name, Queue<IAlert> alertsList, boolean isActive, boolean alertViaMail, String mailAddress, Team team) {
        super(name, password, real_Name, alertsList, isActive, alertViaMail, mailAddress);
        this.team = team;
    }

    /**
     * CONSTRUCTOR FOR restoration object from DB
     **/
    public TeamOwner(String name, String password, String real_name, Team team) {
        super(name, 0, password, real_name);
        this.team = team;
    }

    /**
     * Constructor for first team owner when team is not yet open
     *
     * @param name
     * @param id
     * @param password
     */
    public TeamOwner(String name, String realname, int id, String password) throws mightBeSQLInjectionException, DuplicatedPrimaryKeyException, NoPermissionException, SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException {
        super(name, id, password, realname);
        if (!(system.getMembers().containsKey(this.name))) {
            try {
                system.addMember(this);
            } catch (UserInformationException e) {
                e.printStackTrace();
            }
        }
        new TeamOwnersDAL().insert(this);
    }

    /**
     * constructor for team owner when team is already open
     *
     * @param name
     * @param id
     * @param password
     * @param teamID
     */
    public TeamOwner(String name, String realname, int id, String password, UUID teamID) throws mightBeSQLInjectionException, DuplicatedPrimaryKeyException, NoPermissionException, SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException {
        super(name, id, password, realname);
        this.team = system.getTeamByID(teamID);
        if (!(system.getMembers().containsKey(this.name))) {
            try {
                system.addMember(this);
            } catch (UserInformationException e) {
                e.printStackTrace();
            }
        }
        new TeamOwnersDAL().insert(this);
    }

    /**
     * UC 6.1 - adding asset to team (this team owner must be an owner at the team)
     *
     * @param asset - asset to be added
     * @return true if asset was added to asset
     */
    public boolean addAssetToTeam(IAsset asset) throws InactiveTeamException, TeamOwnerWithNoTeamException, UnauthorizedTeamOwnerException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException, NoPermissionException, SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException {
        if (team == null) {
            throw new TeamOwnerWithNoTeamException();
        }
        return team.addAsset(this, asset);
    }

    /**
     * UC 6.1 - removing asset from team (this team owner must be an owner at the team)
     *
     * @param asset - asset to be removed
     * @return - true if asset was removed
     */
    public boolean removeAssetFromTeam(IAsset asset) throws InactiveTeamException, TeamOwnerWithNoTeamException, UnauthorizedTeamOwnerException, InvalidTeamAssetException, SQLException, NoConnectionException {
        if (team == null) {
            throw new TeamOwnerWithNoTeamException();
        }
        return team.removeAssetFromTeam(this, asset);
    }

    /**
     * UC 6.1 - editing asset(this team owner must be an owner at the team)
     *
     * @param asset - asset to be removed
     * @return - true if asset was removed
     */
    public boolean editAsset(IAsset asset, int value) throws InactiveTeamException, TeamOwnerWithNoTeamException, UnauthorizedTeamOwnerException, InvalidTeamAssetException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException, NoPermissionException, SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException {
        if (team == null) {
            throw new TeamOwnerWithNoTeamException();
        }
        return team.editAsset(this, asset, value);
    }

    /**
     * UC - 6.2 - assign member to become team owner, system will make the change
     *
     * @param newOwner - member (!!) that will become team owner
     * @return true if succeeded
     */
    public boolean assignNewTeamOwner(Member newOwner) throws MemberIsAlreadyTeamOwnerException, MemberIsAlreadyTeamManagerException, TeamOwnerWithNoTeamException, InactiveTeamException, UnauthorizedTeamOwnerException, UserInformationException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException, NoPermissionException, SQLException, UserIsNotThisKindOfMemberException, NoConnectionException {
        if (team == null) {
            throw new TeamOwnerWithNoTeamException();
        }
        return team.addNewTeamOwner(this, newOwner);
    }

    /**
     * UC - 6.3 - remove Team owner
     *
     * @param teamOwnerToRemove - team owner to be removed
     * @return - true if succeeded
     */
    public boolean removeTeamOwner(TeamOwner teamOwnerToRemove) throws TeamOwnerWithNoTeamException, UnauthorizedTeamOwnerException, InactiveTeamException, CantRemoveMainOwnerException {

        if (team == null) {
            throw new TeamOwnerWithNoTeamException();
        }
        return team.removeTeamOwner(teamOwnerToRemove, this);
    }

    /**
     * UC - 6.4 - add a new Team Manger by default all the new team manager permissions will be off
     *
     * @param newTeamManager - member to become team manager
     * @param value          - his asset value
     * @return - true if succeeded
     */
    public boolean assignNewTeamManager(Member newTeamManager, int value) throws MemberIsAlreadyTeamOwnerException, MemberIsAlreadyTeamManagerException, TeamOwnerWithNoTeamException, UnauthorizedTeamOwnerException, UserInformationException, InactiveTeamException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException, NoPermissionException, SQLException, UserIsNotThisKindOfMemberException, NoConnectionException {
        if (team == null) {
            throw new TeamOwnerWithNoTeamException();
        }
        return team.addNewTeamManger(this, newTeamManager, value);
    }

    /**
     * UC 6.4 - edit permissions for team manager
     *
     * @param permissionsType - the permission this team owner wants to edit
     * @param permissionBol   - the value
     * @return - true if succeeded
     */
    public boolean editManagerPermissions(Member member, String permissionsType, boolean permissionBol) throws PersonalPageYetToBeCreatedException, UnauthorizedPageOwnerException, UnauthorizedTeamOwnerException, InactiveTeamException, UserInformationException, TeamOwnerWithNoTeamException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException, NoPermissionException, SQLException, UserIsNotThisKindOfMemberException, NoConnectionException {
        if (team == null) {
            throw new TeamOwnerWithNoTeamException();
        }
        return team.editManagerPermissions(this, member, permissionsType, permissionBol);
    }

    /**
     * UC 6.5 - remove team manager
     *
     * @param teamManager - team manager to be removed
     * @return - true if succeeded
     */
    public boolean removeTeamManager(Member teamManager) throws TeamOwnerWithNoTeamException, UnauthorizedTeamOwnerException, InactiveTeamException, UserIsNotThisKindOfMemberException {
        if (team == null) {
            throw new TeamOwnerWithNoTeamException();
        }
        return team.removeTeamManager(this, teamManager);
    }

    /**
     * UC 6.6 - change team status - for both close and reopen
     *
     * @param newStatus - new status
     * @return - true if succeeded
     */
    public boolean changeTeamStatus(TeamStatus newStatus) throws TeamOwnerWithNoTeamException, UnauthorizedTeamOwnerException, TeamCannotBeReopenException {
        if (team == null) {
            throw new TeamOwnerWithNoTeamException();
        }
        return team.changeTeamStatus(this, newStatus);
    }

    /**
     * UC 6.7 - add budget activity
     * the func checks if activity is income of outcome and handle accordingly
     *
     * @param date
     * @param description
     * @param amount
     * @return true if succeeded
     */
    public boolean addBudgetActivity(Date date, BudgetActivity description, int amount) throws TeamOwnerWithNoTeamException, UnauthorizedTeamOwnerException, InactiveTeamException, SQLException, NoConnectionException {
        if (team == null) {
            throw new TeamOwnerWithNoTeamException();
        }
        if (description == BudgetActivity.BuyPlayer || description == BudgetActivity.MaintenanceField || description == BudgetActivity.Salaries || description == BudgetActivity.Tax) {
            return team.addBudgetActivity(this, date, description, (amount * (-1)));
        } else {
            return team.addBudgetActivity(this, date, description, (amount));
        }
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void sendRegisterRequestForNewTeam(String teamName,Leaugue leaugue, int year){
        boolean found = false;
        HashMap<String, LinkedList<Member>> members = system.getMembers();
        for (String name : members.keySet()) {
            for (int i = 0; i < members.get(name).size(); i++) {
                if (members.get(name).get(i) instanceof Commissioner) {
                    members.get(name).get(i).handleAlert(new RegistrationRequestAlert(teamName,leaugue,year,this));
                    found = true;
                    break;
                }
                if (found){
                    break;
                }
            }
            if (found){
                break;
            }
        }
    }

}
