package Domain.Users;

import Domain.FootballManagmentSystem;
import Domain.PersonalPages.APersonalPageContent;
import Domain.SeasonManagment.IAsset;
import Domain.SeasonManagment.Team;
import Domain.SystemLog;
import FootballExceptions.*;

import java.util.HashMap;

public class TeamManager extends Member implements IAsset {

    FootballManagmentSystem system = FootballManagmentSystem.getInstance();
    private int valAsset;
    private int assetID;
    private Team myTeam;

    private TeamOwner teamOwnerAssignedThis;
    /**
     * according to UC 7.1 the team owner assigned the team manager is the only one that can change permissions
     */
    private HashMap<String, Boolean> permissions;

    /**
     * CONSTRUCTOR FOR restoration object from DB
     **/
    public TeamManager(String name, String password, String real_name, int valAsset, int assetID, Team myTeam, TeamOwner teamOwnerAssignedThis, HashMap<String, Boolean> permissions) {
        super(name, 0, password, real_name);
        this.valAsset = valAsset;
        this.assetID = assetID;
        this.myTeam = myTeam;
        this.teamOwnerAssignedThis = teamOwnerAssignedThis;
        this.permissions = permissions;
    }

    /**
     * The hashmap in the value represent the specific team manager permission type (key for permission and val for T/F
     */


    public TeamManager(String name, String realname, int id, String password, int valAsset, Team myTeam, TeamOwner teamOwnerAssignedThis) {
        super(name, id, password, realname);
        this.valAsset = valAsset;
        this.myTeam = myTeam;
        assetID = system.generateAssetID();
        permissions = new HashMap<>();
        permissions.put("Hire Coach", false);
        permissions.put("Create Personal Page", false);
        permissions.put("Add Content To Personal Page", false);
        permissions.put("Edit Personal Page Profile", false);
        system.addTeamAssets(this);
        this.teamOwnerAssignedThis = teamOwnerAssignedThis;
        if (!(system.getMembers().containsKey(this.name))) {
            try {
                system.addMember(this);
            } catch (UserInformationException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * edit permission for this team manger, edit can be done only by team owner assigned this team manager
     * (according to UC 7.1 the team owner assigned the team manager is the only one that can change permissions)
     *
     * @param member        - team owner wishing to edit
     * @param permission    - the permission type he wants to edit
     * @param permissionBol - boolean represent the value
     * @return true if succeeded
     */
    public boolean editPermissions(Member member, String permission, boolean permissionBol) throws UnauthorizedTeamOwnerException, InactiveTeamException, UnauthorizedPageOwnerException, PersonalPageYetToBeCreatedException {
        if (isAutorizedTeamOwner(member)) {
            permissions.replace(permission, permissionBol);
            if (permission.equals("Add Content To Personal Page") || permission.equals("Edit Personal Page Profile")) {
                if (permissionBol) {
                    myTeam.addPersonalPageEditor(this);
                } else {
                    myTeam.removePersonalPageEditor(this);
                }
            }
            String perBol = "";
            if (permissionBol) {
                perBol = "off";
            } else {
                perBol = "on";
            }
            SystemLog.getInstance().UpdateLog("Team Manager: " + this.getName() + " Permissions to " + permission + "are now " + perBol);
            return true;
        }
        return false;
    }

    /**
     * adding coach to coach staff (if team already has coach at that role this will override it)
     *
     * @param newCoach
     * @return
     */
    public boolean hireCoach(IAsset newCoach) throws UserInformationException, InactiveTeamException, UnauthorizedTeamManagerException {
        //todo WARN MEMBER ABOUT OVERRIDING
        if (permissions.get("Hire Coach")) {
            myTeam.addCoach(this, newCoach);
            return true;
        }
        return false;
    }

    /**
     * creates personal page (only if team manager has permissions) - if personal page already exists this will override it
     * (constraint 4 - one personal page per member) NEED TO WARN MEMBER IN GUI
     *
     * @return - true if succeeded
     */
    public boolean createPersonalPageForTeam() throws UnauthorizedPageOwnerException, UnauthorizedTeamManagerException, InactiveTeamException {
        //todo WARN MEMBER ABOUT OVERRIDING
        if (permissions.get("Create Personal Page")) {
            return myTeam.createPersonalPage(this);
        }
        return false;
    }

    /**
     * adding content to personal page
     *
     * @param content - content of some kind to be added to personal page
     * @return - true if succeeded
     */
    public boolean addContentToTeamsPersonalPage(APersonalPageContent content) throws UnauthorizedPageOwnerException, UnauthorizedTeamManagerException, InactiveTeamException {
        if (permissions.get("Add Content To Personal Page")) {
            return myTeam.addContentToPersonalPage(this, content);
        }
        return false;
    }

    /**
     * editing profile content in personal page (only according to constraint)
     *
     * @param title - info title
     * @param val-  val
     * @return - true if succeeded
     */
    public boolean editProfileOnPersonalPage(String title, String val) throws UnauthorizedPageOwnerException, UnauthorizedTeamManagerException, InactiveTeamException, PersonalPageYetToBeCreatedException {
        if (permissions.get("Edit Personal Page Profile")) {
            return myTeam.editPersonalPageProfile(this, title, val);
        }
        return false;
    }

    /**
     * checking if member is the team owner who assigned this team manager
     *
     * @param member -
     * @return -
     */
    public boolean isAutorizedTeamOwner(Member member) throws UnauthorizedTeamOwnerException {
        if (member.equals(teamOwnerAssignedThis)) {
            return true;
        }
        throw new UnauthorizedTeamOwnerException();
    }

    public void resetPermissions() {
        for (String permission : permissions.keySet()) {
            permissions.replace(permission, false);
        }
    }

    public Team getMyTeam() {
        return myTeam;
    }

    public void setTeamOwnerAssignedThis(TeamOwner teamOwnerAssignedThis) {
        this.teamOwnerAssignedThis = teamOwnerAssignedThis;
    }

    public TeamOwner getTeamOwnerAssignedThis() {
        return teamOwnerAssignedThis;
    }

    @Override
    public int getAssetID() {
        return assetID;
    }

    @Override
    public void edit(int value) {
        this.valAsset = value;
    }

    @Override
    public int getValue() {
        return valAsset;
    }

    public HashMap<String, Boolean> getPermissions() {
        return permissions;
    }
}
