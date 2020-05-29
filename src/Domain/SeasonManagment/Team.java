package Domain.SeasonManagment;

import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.SeasonManagmentDAL.ControlBudgetDAL;
import DataAccess.SeasonManagmentDAL.FieldsDAL;
import DataAccess.SeasonManagmentDAL.TeamsDAL;
import DataAccess.UsersDAL.CoachesDAL;
import DataAccess.UsersDAL.PlayersDAL;
import DataAccess.UsersDAL.TeamManagerDAL;
import Domain.Alerts.IAlert;
import Domain.Alerts.TeamManagementAlert;
import Domain.FootballManagmentSystem;
import Domain.PersonalPages.APersonalPageContent;
import Domain.SystemLog;
import Domain.Users.*;
import FootballExceptions.*;

import java.sql.SQLException;
import java.util.*;

public class Team {
    private List<Season> seasons;
    private String Name;
    private Season currentSeason;
    private PersonalInfo info;
    private TeamOwner owner;
    private TeamStatus status;
    private int score; //todo maybe nono
    private UUID id;
    //private HashMap<Integer,Budget> year_budget;
    private ControlBudget controlBudget;
    private boolean isClosed;
    private LinkedList<TeamOwner> secondaryOwners;
    private HashMap<Integer, IAsset> teamPlayers;
    /**
     * the key is asset ID
     */
    private HashMap<Integer, IAsset> teamfields;
    /**
     * the key is asset ID
     */
    private HashMap<Integer, TeamManager> teamMangers;
    /**
     * the key is asset ID
     */
    private HashMap<CoachRole, IAsset> teamCoaches;
    /**
     * the key is Manager role i.e: "Head Coach" or "First Assistant Coach" or "Second Assistant Coach" "First Goal-Keepers Coach
     */
    FootballManagmentSystem system = FootballManagmentSystem.getInstance();
    private double playersFootballRate;
    private HashMap<Team, LinkedList<Game>> gamesHistory;
    private LinkedList<Game> upcomingGames;
    private boolean systemMangerClosed;

    /**
     * CONSTRUCTOR FOR restoration object from DB
     **/
    public Team(List<Season> seasons, String name, PersonalInfo info, TeamOwner owner, TeamStatus status, int score, UUID id, ControlBudget controlBudget, boolean isClosed, LinkedList<TeamOwner> secondaryOwners, HashMap<Integer, IAsset> teamPlayers, HashMap<Integer, IAsset> teamfields, HashMap<Integer, TeamManager> teamMangers, HashMap<CoachRole, IAsset> teamCoaches, double playersFootballRate, LinkedList<Game> upcomingGames, boolean systemMangerClosed) {
        this.seasons = seasons;
        Name = name;
        this.info = info;
        this.owner = owner;
        this.status = status;
        this.score = score;
        this.id = id;
        this.controlBudget = controlBudget;
        this.isClosed = isClosed;
        this.secondaryOwners = secondaryOwners;
        this.teamPlayers = teamPlayers;
        this.teamfields = teamfields;
        this.teamMangers = teamMangers;
        this.teamCoaches = teamCoaches;
        this.playersFootballRate = playersFootballRate;
        this.upcomingGames = upcomingGames;
        this.systemMangerClosed = systemMangerClosed;
    }

    /**
     * constructor
     *
     * @param owner
     * @param Name
     */
    public Team(String Name, TeamOwner owner) {
        secondaryOwners = new LinkedList<>();
        upcomingGames = new LinkedList<>();
        teamCoaches = new HashMap<>();
        teamPlayers = new HashMap<>();
        teamMangers = new HashMap<>();
        teamfields = new HashMap<>();
        seasons = new LinkedList<>();
        gamesHistory = new HashMap<>();
        this.Name = Name;
        this.owner = owner;
        this.status = TeamStatus.Active; /**by default team status is active*/
        this.id = UUID.randomUUID();
        this.owner.setTeam(this);
        system.registerTeam(this);
        this.controlBudget = new ControlBudget(this.id);
        this.systemMangerClosed = false;
        try {
            new ControlBudgetDAL().insert(controlBudget);
            new TeamsDAL().insert(this);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (UserInformationException e) {
            e.printStackTrace();
        } catch (UserIsNotThisKindOfMemberException e) {
            e.printStackTrace();
        } catch (NoConnectionException e) {
            e.printStackTrace();
        } catch (NoPermissionException e) {
            e.printStackTrace();
        } catch (mightBeSQLInjectionException e) {
            e.printStackTrace();
        } catch (DuplicatedPrimaryKeyException e) {
            e.printStackTrace();
        }
    }

    public UUID getId() {
        return id;
    }

    public boolean isActive() {
        if (status == TeamStatus.Active) {
            return true;
        }
        return false;
    }


    public int getCurrentBudget() {
        Date date = new Date();
        return controlBudget.getCurrentBudget(date);
    }


    public void calculatePlayerFootballRate() {
        double totalRate = 0;
        for (Integer id : teamPlayers.keySet()) {
            totalRate += ((Player) teamPlayers.get(id)).getFootballRate();
        }
        if (teamPlayers.size() != 0) {
            playersFootballRate = totalRate / teamPlayers.size();
        }
    }

    public HashMap<Integer, IAsset> getTeamPlayers() {
        return teamPlayers;
    }

    public HashMap<CoachRole, IAsset> getTeamCoaches() {
        return teamCoaches;
    }

    public void setTeamPlayers(HashMap<Integer, IAsset> teamPlayers) {
        this.teamPlayers = teamPlayers;
    }

    /**
     * adding asset added by team owner, checking if he is really team owner
     *
     * @param asset  - asset to be added
     * @param member - member wishing to add asset
     * @return - true if succeeded
     */
    public boolean addAsset(Member member, IAsset asset) throws InactiveTeamException, UnauthorizedTeamOwnerException {
        if (!isActive()) {
            throw new InactiveTeamException();
        }
        if (isTeamOwner(member)) {
            if (asset instanceof Player) {
                teamPlayers.put(asset.getAssetID(), asset);
                calculatePlayerFootballRate();
                ((Player) asset).setMyTeam(this);
                try {
                    new PlayersDAL().update((Player) asset);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (UserIsNotThisKindOfMemberException e) {
                    e.printStackTrace();
                } catch (UserInformationException e) {
                    e.printStackTrace();
                } catch (NoConnectionException e) {
                    e.printStackTrace();
                } catch (NoPermissionException e) {
                    e.printStackTrace();
                } catch (mightBeSQLInjectionException e) {
                    e.printStackTrace();
                } catch (DuplicatedPrimaryKeyException e) {
                    e.printStackTrace();
                }
                SystemLog.getInstance().UpdateLog("New Player: " + asset.getClass().toString().toLowerCase() + " has been added to team: " + asset.getMyTeam() + "by" + member.getName());
            }
            if (asset instanceof Coach) {
                teamCoaches.put(((Coach) asset).getRole(), asset);
                ((Coach) asset).setMyTeam(this);
                try {
                    new CoachesDAL().update((Coach) asset);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (mightBeSQLInjectionException e) {
                    e.printStackTrace();
                } catch (DuplicatedPrimaryKeyException e) {
                    e.printStackTrace();
                } catch (NoPermissionException e) {
                    e.printStackTrace();
                } catch (UserInformationException e) {
                    e.printStackTrace();
                } catch (UserIsNotThisKindOfMemberException e) {
                    e.printStackTrace();
                } catch (NoConnectionException e) {
                    e.printStackTrace();
                }
                SystemLog.getInstance().UpdateLog("New " + ((Coach) asset).getRole() + " " + asset.getClass().toString().toLowerCase() + " has been added to team: " + asset.getMyTeam() + "by" + member.getName());
            }
            if (asset instanceof Field) {
                ((Field) asset).setMyTeam(this);
                teamfields.put(asset.getAssetID(), asset);
                try {
                    new FieldsDAL().update((Field) asset);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (UserIsNotThisKindOfMemberException e) {
                    e.printStackTrace();
                } catch (UserInformationException e) {
                    e.printStackTrace();
                } catch (NoConnectionException e) {
                    e.printStackTrace();
                } catch (NoPermissionException e) {
                    e.printStackTrace();
                }
                SystemLog.getInstance().UpdateLog("New Field: " + asset.getClass().toString().toLowerCase() + " has been added to team: " + asset.getMyTeam() + "by" + member.getName());
            }
        }
        return true;
    }

    /**
     * removing asset, checking if he is really team owner
     *
     * @param member - member wishing to remove asset
     * @param asset  - asset to be removed
     * @return true if succeeded
     */
    public boolean removeAssetFromTeam(Member member, IAsset asset) throws InactiveTeamException, UnauthorizedTeamOwnerException, InvalidTeamAssetException {
        if (!isActive()) {
            throw new InactiveTeamException();
        }
        if (isTeamOwner(member)) {
            if (asset instanceof Player) {
                if (!teamPlayers.containsKey(asset.getAssetID())) {
                    throw new InvalidTeamAssetException();
                }
                teamPlayers.remove(asset.getAssetID());
                system.removeAsset(asset);
                calculatePlayerFootballRate();
                SystemLog.getInstance().UpdateLog("Player: " + asset.getClass().toString().toLowerCase() + " was removed from team: " + asset.getMyTeam() + "by" + member.getName());
            }
            if (asset instanceof Coach) {
                if (!teamCoaches.containsKey(((Coach) asset).getRole())) {
                    throw new InvalidTeamAssetException();
                }
                teamCoaches.remove(((Coach) asset).getRole());
                system.removeAsset(asset);
                SystemLog.getInstance().UpdateLog(((Coach) asset).getRole() + " " + asset.getClass().toString().toLowerCase() + " has been added to team: " + asset.getMyTeam() + "by" + member.getName());
            }
            if (asset instanceof Field) {
                if (!teamfields.containsKey(asset.getAssetID())) {
                    throw new InvalidTeamAssetException();
                }
                teamfields.remove(asset.getAssetID());
                system.removeAsset(asset);
                SystemLog.getInstance().UpdateLog("Field: " + asset.getClass().toString().toLowerCase() + " was removed from team: " + asset.getMyTeam() + "by" + member.getName());
            }
        }
        return true;
    }

    /**
     * editing asset, checking if he is really team owner
     *
     * @param member - member wishing to edit asset
     * @param asset  - asset to be edited
     * @return true if succeeded
     */
    public boolean editAsset(Member member, IAsset asset, int value) throws InactiveTeamException, UnauthorizedTeamOwnerException, InvalidTeamAssetException {
        if (!isActive()) {
            throw new InactiveTeamException();
        }
        if (isTeamOwner(member)) {
            if (asset instanceof Player) {
                IAsset editedAsset = teamPlayers.get(asset.getAssetID());
                if (editedAsset == null) {
                    throw new InvalidTeamAssetException();
                }
                editedAsset.edit(value);
                teamPlayers.replace(asset.getAssetID(), editedAsset);
                system.addTeamAssets(asset);
                SystemLog.getInstance().UpdateLog("Player: " + asset.getClass().toString().toLowerCase() + " asset was edited from team: " + asset.getMyTeam() + "by" + member.getName());
            }
            if (asset instanceof Coach) {
                IAsset editedAsset = teamCoaches.get(((Coach) asset).getRole());
                if (editedAsset == null) {
                    throw new InvalidTeamAssetException();
                }
                if (editedAsset == null) {
                    throw new InvalidTeamAssetException();
                }
                editedAsset.edit(value);
                teamCoaches.replace(((Coach) asset).getRole(), editedAsset);
                system.addTeamAssets(asset);
                SystemLog.getInstance().UpdateLog("Coach: " + asset.getClass().toString().toLowerCase() + " asset was edited from team: " + asset.getMyTeam() + "by" + member.getName());
            }
            if (asset instanceof Field) {
                IAsset editedAsset = teamfields.get(asset.getAssetID());
                if (editedAsset == null) {
                    throw new InvalidTeamAssetException();
                }
                editedAsset.edit(value);
                teamfields.replace(asset.getAssetID(), editedAsset);
                system.addTeamAssets(asset);
                SystemLog.getInstance().UpdateLog("Field: " + asset.getClass().toString().toLowerCase() + " asset was edited from team: " + asset.getMyTeam() + "by" + member.getName());
            }
        }
        return true;
    }

    /**
     * adding new member to become team owner, system will make the change (only if team owner added him)
     *
     * @param ownerAssigning - team owner assigning the new owner
     * @param newOwner       - the assigned new owner
     * @return true if succeeded
     */
    public boolean addNewTeamOwner(Member ownerAssigning, Member newOwner) throws MemberIsAlreadyTeamManagerException, MemberIsAlreadyTeamOwnerException, InactiveTeamException, UnauthorizedTeamOwnerException, UserInformationException {
        if (!isActive()) {
            throw new InactiveTeamException();
        }
        List<Member> memberAccountsCheck = system.getMemberByUserName(newOwner.getName());
        for (Member memberAccount : memberAccountsCheck) {
            if (owner.equals(memberAccount) || secondaryOwners.contains(memberAccount)) {
                throw new MemberIsAlreadyTeamOwnerException();
            }
            if (memberAccount instanceof TeamManager) {
                if (teamMangers.containsKey(((TeamManager) memberAccount).getAssetID())) {
                    throw new MemberIsAlreadyTeamManagerException();
                }
            }
        }

        if (isTeamOwner(ownerAssigning)) {
            LinkedList<Member> memberAccounts = system.makeMemberTeamOwner(newOwner, this);
            if (memberAccounts != null) {
                for (Member member : memberAccounts) {
                    if (member instanceof TeamOwner) {
                        secondaryOwners.add((TeamOwner) member);
                        IAlert alert = new TeamManagementAlert("Your are now Team owner of " + this.Name);
                        member.handleAlert(alert);
                        return true;
                    }
                }
            } else {
                throw new UserInformationException();
            }
        }
        return true;
    }

    /**
     * removing team owner(only if team owner removed him)
     *
     * @param teamOwnerToRemove
     * @param teamOwner
     * @return true if succeeded
     */
    public boolean removeTeamOwner(TeamOwner teamOwnerToRemove, TeamOwner teamOwner) throws UnauthorizedTeamOwnerException, InactiveTeamException, CantRemoveMainOwnerException {
        if (!isActive()) {
            throw new InactiveTeamException();
        }
        if (teamOwnerToRemove.equals(owner)) {
            throw new CantRemoveMainOwnerException();
        }
        if (isTeamOwner(teamOwner)) {
            LinkedList<Member> list = new LinkedList<>();
            list.add(teamOwnerToRemove);
            system.RemoveMember(list);
            IAlert teamAlert = new TeamManagementAlert(teamOwnerToRemove.getName() + " is no longer a team owner");
            teamOwnerToRemove.handleAlert(teamAlert); /**notify the team owner he is not team owner anymore*/
            secondaryOwners.remove(teamOwnerToRemove);
            return true;
        }
        throw new UnauthorizedTeamOwnerException();
    }

    /**
     * add new team manager (only if team owner added him)
     *
     * @param teamOwner      - member adding the team manager
     * @param newTeamManager - member to become team manager
     * @param value          - his asset value
     * @return - true if succeeded
     */
    public boolean addNewTeamManger(TeamOwner teamOwner, Member newTeamManager, int value) throws MemberIsAlreadyTeamManagerException, MemberIsAlreadyTeamOwnerException, UnauthorizedTeamOwnerException, InactiveTeamException, UserInformationException {
        if (!isActive()) {
            throw new InactiveTeamException();
        }
        List<Member> memberAccountsCheck = system.getMemberByUserName(newTeamManager.getName());
        for (Member memberAccount : memberAccountsCheck) {
            if (owner.equals(memberAccount) || secondaryOwners.contains(memberAccount)) {
                throw new MemberIsAlreadyTeamOwnerException();
            }
            if (memberAccount instanceof TeamManager) {
                if (teamMangers.containsKey(((TeamManager) memberAccount).getAssetID())) {
                    throw new MemberIsAlreadyTeamManagerException();
                }
            }
        }
        if (isTeamOwner(teamOwner)) {
            LinkedList<Member> memberAccounts = system.makeMemberTeamManger(newTeamManager, this, value, teamOwner);
            if (memberAccounts != null) {
                for (Member member : memberAccounts) {
                    if (member instanceof TeamManager) {
                        teamMangers.put(((TeamManager) member).getAssetID(), (TeamManager) member);
                        IAlert alert = new TeamManagementAlert("Your are now Team manager of " + this.Name);
                        member.handleAlert(alert);
                        return true;
                    }
                }
            } else {
                throw new UserInformationException();
            }
        }
        return false;

    }

    /**
     * editing team manager permissions
     *
     * @param teamOwner
     * @param teamManager
     * @param permissionsType
     * @param permissionBol
     * @return
     */
    public boolean editManagerPermissions(TeamOwner teamOwner, Member teamManager, String permissionsType, boolean permissionBol) throws UnauthorizedPageOwnerException, InactiveTeamException, UserInformationException, PersonalPageYetToBeCreatedException, UnauthorizedTeamOwnerException {
        if (!isActive()) {
            throw new InactiveTeamException();
        }
        List<Member> memberAccount = system.getMemberByUserName(teamManager.getName());
        if (memberAccount == null) {
            throw new UserInformationException();
        }
        for (Member member : memberAccount) {
            if (member instanceof TeamManager) {
                teamManager = member;
            }
        }
        if (!(teamManager instanceof TeamManager)) {
            throw new UserInformationException();
        }
        if (teamMangers.containsKey(((TeamManager) teamManager).getAssetID())) {
            TeamManager editedTeamManger = teamMangers.get(((TeamManager) teamManager).getAssetID());
            if (editedTeamManger == null) {
                throw new UserInformationException();
            }
            if (editedTeamManger.editPermissions(teamOwner, permissionsType, permissionBol)) {
                teamMangers.replace(((TeamManager) teamManager).getAssetID(), editedTeamManger);
                try {
                    new TeamManagerDAL().update(editedTeamManger);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (NoConnectionException e) {
                    e.printStackTrace();
                } catch (UserIsNotThisKindOfMemberException e) {
                    e.printStackTrace();
                } catch (NoPermissionException e) {
                    e.printStackTrace();
                } catch (mightBeSQLInjectionException e) {
                    e.printStackTrace();
                } catch (DuplicatedPrimaryKeyException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * remove team manager if the right team owner asked to do so
     * removes his team manager user from system
     *
     * @param teamOwner - team owner wants to remove the team manager
     * @param member    - team manager to be removed
     * @return - true if succeeded
     */
    public boolean removeTeamManager(TeamOwner teamOwner, Member member) throws UnauthorizedTeamOwnerException, InactiveTeamException, UserIsNotThisKindOfMemberException {
        if (!isActive()) {
            throw new InactiveTeamException();
        }
        TeamManager teamManager = ((TeamManager) system.getMemberInstanceByKind(member.getName(), "Team Manager"));
        if (isTeamOwner(teamOwner)) {
            if (teamManager.isAutorizedTeamOwner(teamOwner)) {
                system.removeMemberSpecificAccount(member, "Team Manager");
                teamMangers.remove(teamManager.getAssetID());
                return true;
            }
        }
        return false;
    }

    /**
     * checking if member is team owner
     *
     * @param member
     * @return
     */
    public boolean isTeamOwner(Member member) throws UnauthorizedTeamOwnerException {
        List<Member> memberAccounts = system.getMemberByUserName(member.getName());
        if (memberAccounts == null) {
            return false;
        }
        for (Member memberAccount : memberAccounts) {
            if (owner.equals(memberAccount) || secondaryOwners.contains(memberAccount)) {
                return true;
            }
        }
        throw new UnauthorizedTeamOwnerException();
    }

    /**
     * checking if member is team manager
     *
     * @param member
     * @return
     */
    public boolean isTeamManager(Member member) {
        List<Member> memberAccounts = system.getMemberByUserName(member.getName());
        if (memberAccounts == null) {
            return false;
        }
        for (Member memberAccount : memberAccounts) {
            if (memberAccount instanceof TeamManager) {
                return teamMangers.containsKey(((TeamManager) memberAccount).getAssetID());
            }
        }
        return false;
    }

    /**
     * @return - all team owners
     */
    public LinkedList<TeamOwner> getAllTeamOwners() {
        LinkedList allTeamOwners = new LinkedList();
        allTeamOwners.add(owner);
        for (TeamOwner teamOwner : secondaryOwners) {
            allTeamOwners.add(teamOwner);
        }
        return allTeamOwners;
    }

    /**
     * @return - all team managers
     */
    public LinkedList<TeamManager> getAllTeamManaers() {
        LinkedList allTeamMangers = new LinkedList();
        for (Integer i : teamMangers.keySet()) {
            allTeamMangers.add(teamMangers.get(i));
        }
        return allTeamMangers;
    }

    /**
     * adding new coach to coach staff - if team already has coach in the new coach role it will override it
     *
     * @param teamManager
     * @param newCoach
     * @return
     */
    public boolean addCoach(TeamManager teamManager, IAsset newCoach) throws InactiveTeamException, UserInformationException, UnauthorizedTeamManagerException {
        if (!isActive()) {
            throw new InactiveTeamException();
        }
        if (isTeamManager(teamManager)) {
            if (newCoach instanceof Coach) {
                if (teamCoaches.containsKey(((Coach) newCoach).getRole())) {
                    teamCoaches.replace(((Coach) newCoach).getRole(), newCoach);
                } else {
                    teamCoaches.put(((Coach) newCoach).getRole(), newCoach);
                }
                return true;
            }
        } else {
            throw new UnauthorizedTeamManagerException();
        }
        return false;
    }

    /**OPERATING PERSONAL PAGE - operated by team manager (with the right permissions)*/
    /**
     * creating a new personal page, if the team already has personal page this will override it
     * (constraint 4). NEED TO WARN MEMBER IN GUI
     *
     * @param teamManager -
     * @return - true if succeeded
     */
    public boolean createPersonalPage(TeamManager teamManager) throws InactiveTeamException, UnauthorizedTeamManagerException {
        if (!isActive()) {
            throw new InactiveTeamException();
        }
        if (isTeamManager(teamManager)) {
            if (info != null) {
                system.removePersonalPage(info);
            }
            info = new PersonalInfo(teamManager);
            return true;
        }
        throw new UnauthorizedTeamManagerException();
    }

    /**
     * adding content to personal page only if team manager is able to (constraint 4)
     *
     * @param teamManager
     * @param content
     * @return
     */
    public boolean addContentToPersonalPage(TeamManager teamManager, APersonalPageContent content) throws UnauthorizedPageOwnerException, InactiveTeamException, UnauthorizedTeamManagerException {
        if (!isActive()) {
            throw new InactiveTeamException();
        }
        if (isTeamManager(teamManager)) {
            if (info == null) {
                return false;
            }
            return info.addContentToPage(teamManager, content);
        }
        return false;
    }

    /**
     * editing profile content in personal page (only according to constraint)
     *
     * @param teamManager
     * @param title
     * @param val
     * @return
     */
    public boolean editPersonalPageProfile(TeamManager teamManager, String title, String val) throws UnauthorizedPageOwnerException, PersonalPageYetToBeCreatedException, InactiveTeamException, UnauthorizedTeamManagerException {
        if (!isActive()) {
            throw new UnauthorizedTeamManagerException();
        }
        if (isTeamManager(teamManager)) {
            if (info == null) {
                throw new PersonalPageYetToBeCreatedException();
            }
            return info.editProfile(teamManager, title, val);
        }
        return false;
    }

    /**
     * adding team manager to list, becoming able to edit personal page editor
     *
     * @param teamManager -
     * @return -
     */
    public boolean addPersonalPageEditor(TeamManager teamManager) throws InactiveTeamException, PersonalPageYetToBeCreatedException {
        if (!isActive()) {
            throw new InactiveTeamException();
        }
        if (info == null) {
            throw new PersonalPageYetToBeCreatedException();
        }
        info.addTeamPageMemberOwner(teamManager);
        return true;
    }

    /**
     * removing team manager from the list and he wont be able to edit personal page
     *
     * @param teamManager
     * @return
     */
    public boolean removePersonalPageEditor(TeamManager teamManager) throws InactiveTeamException, PersonalPageYetToBeCreatedException {
        if (!isActive()) {
            throw new InactiveTeamException();
        }
        if (info == null) {
            throw new PersonalPageYetToBeCreatedException();
        }
        info.removeOwnerFromPageMemberOwner(teamManager);
        return true;
    }

    /**
     * this func add a budget activity to team budget
     *
     * @param teamOwner
     * @param date
     * @param ba
     * @param i
     * @return true if succeeded
     */
    public boolean addBudgetActivity(TeamOwner teamOwner, Date date, BudgetActivity ba, int i) throws UnauthorizedTeamOwnerException, InactiveTeamException {
        if (!isActive()) {
            throw new InactiveTeamException();
        }
        if (isTeamOwner(teamOwner)) {
            if (controlBudget != null) {
                controlBudget.addFinanceActivity(date, ba, i);
                return true;
            }
        }
        return false;
    }

    /**
     * this func runs only by team owner, get status and changes the team status accordingly, in addition
     * it notify to everyone who needs about the status change
     *
     * @param teamOwner - member operating
     * @param newStatus - Active/Closed
     * @return - true if succeeded
     */
    public boolean changeTeamStatus(TeamOwner teamOwner, TeamStatus newStatus) throws UnauthorizedTeamOwnerException, TeamCannotBeReopenException {
        if (systemMangerClosed) {
            throw new TeamCannotBeReopenException();
        }
        if (isTeamOwner(teamOwner)) {
            if (status == newStatus) {
                return false;
            }
            status = newStatus;
            if (newStatus == TeamStatus.Active) {
                resetAllTeamManagerPermissions(); /** MAYBE - according to UC 6.6 last sentence */
            }
            noitfyTeamStatusChanged(newStatus);
            SystemLog.getInstance().UpdateLog(teamOwner.getName() + " has changer team " + Name + " status to " + newStatus.toString());
            try {
                new TeamsDAL().update(this);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (NoConnectionException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    /**
     * reset all team managers permissions to false
     * MAYBE - according to UC 6.6 last sentence
     */
    private void resetAllTeamManagerPermissions() {
        for (Integer id : teamMangers.keySet()) {
            teamMangers.get(id).resetPermissions();
        }
    }

    private void noitfyTeamStatusChanged(TeamStatus newStatus) {
        IAlert teamManagmentAlert = new TeamManagementAlert(newStatus, this);
        owner.handleAlert(teamManagmentAlert);
        for (TeamOwner teamOwner : secondaryOwners) {
            teamOwner.handleAlert(teamManagmentAlert);
        }
        for (Integer id : teamMangers.keySet()) {
            teamMangers.get(id).handleAlert(teamManagmentAlert);
        }
        List<SystemManager> allSystem = system.getAllInCharge();
        for (SystemManager sys : allSystem) {
            sys.handleAlert(teamManagmentAlert);
        }
    }

    /**
     * handling alerts - runs when game the team involves start an alert and the func transfer the alert to the personal page to  inform followers
     *
     * @param newAlert
     * @param game
     */
    public void notifyTeam(IAlert newAlert, Game game) throws PersonalPageYetToBeCreatedException {

        if (info == null) {
            throw new PersonalPageYetToBeCreatedException();
        }
        info.notifyInfo(newAlert, game);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Team team = (Team) o;

        return id == team.id;
    }

    public void addToGamesHistory(Team otherTeam, Game game) {
        if (gamesHistory.containsKey(otherTeam)) {
            LinkedList<Game> gameWithOtherTeamHistory = gamesHistory.get(otherTeam);
            gameWithOtherTeamHistory.add(game);
            gamesHistory.replace(otherTeam, gameWithOtherTeamHistory);
        } else {
            LinkedList gameWithOtherTeamHistory = new LinkedList();
            gameWithOtherTeamHistory.add(game);
            gamesHistory.put(otherTeam, gameWithOtherTeamHistory);
        }
    }

    public LinkedList<Game> getGameHistoryWithOtherTeam(Team otherTeam) {
        LinkedList<Game> history = new LinkedList<>();
        if (gamesHistory.containsKey(otherTeam)) {
            history = gamesHistory.get(otherTeam);
        }
        return history;
    }

    public void addGameToUpcomingGames(Game game) {
        upcomingGames.add(game);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public double getPlayersFootballRate() {
        return playersFootballRate;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }


    public List<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<Season> seasons) {
        this.seasons = seasons;
    }

    public Season getCurrentSeason() {
        return currentSeason;
    }

    public void setCurrentSeason(Season currentSeason) {
        this.currentSeason = currentSeason;
    }

    public PersonalInfo getInfo() {
        return info;
    }

    public void setInfo(PersonalInfo info) {
        this.info = info;
    }

    public LinkedList<Game> getUpcomingGames() {
        return upcomingGames;
    }

    public TeamOwner getOwner() {
        return owner;
    }

    // public void setOwner(TeamOwner owner) {
    // this.owner = owner;
    //}

    public TeamStatus getStatus() {
        return status;
    }

    public void setStatus(TeamStatus status) {
        this.systemMangerClosed = true;
        this.status = status;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Integer getScore() {
        return score;
    } //why?!

    public String getName() {
        return Name;
    }

    public boolean isSystemMangerClosed() {
        return systemMangerClosed;
    }

    public ControlBudget getControlBudget() {
        return controlBudget;
    }

    public String toString() {
        return this.getName();
    }
}
