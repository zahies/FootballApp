package Domain.Users;

import Domain.FootballManagmentSystem;
import Domain.PersonalPages.APersonalPageContent;
import Domain.SeasonManagment.IAsset;
import Domain.SeasonManagment.Team;
import FootballExceptions.PersonalPageYetToBeCreatedException;
import FootballExceptions.UnauthorizedPageOwnerException;
import FootballExceptions.UserInformationException;

public class Coach extends Member implements IAsset {
    private int valAsset;
    private Team myTeam;
    private String training;
    private CoachRole role;
    private PersonalInfo info;
    private int assetID;
    FootballManagmentSystem system = FootballManagmentSystem.getInstance();

    public Coach(String name, String realname, int id, String password, int val, String training, CoachRole role) {
        super(name, id, password, realname);
        this.valAsset = val;
        this.training = training;
        this.role = role;
        this.assetID = system.generateAssetID();
        system.addTeamAssets(this);
        if (!(system.getMembers().containsKey(this.name))) {
            try {
                system.addMember(this);
            } catch (UserInformationException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * creates personal page - if personal page already exists this will override it
     * (constraint 4 - one personal page per member) NEED TO WARN MEMBER IN GUI
     *
     * @return - true if succeeded
     */
    public boolean createPersonalPage() {
        //todo WARN MEMBER ABOUT OVERRIDING
        if (info != null) {
            system.removePersonalPage(info);
        }
        info = new PersonalInfo(this);
        return true;
    }

    /**
     * adding content to personal page
     *
     * @param content - content of some kind to be added to personal page
     * @return - true if succeeded
     */
    public boolean addContentToPersonalPage(APersonalPageContent content) throws UnauthorizedPageOwnerException, PersonalPageYetToBeCreatedException {
        if (info == null) {
            throw new PersonalPageYetToBeCreatedException();
        }
        return info.addContentToPage(this, content);
    }

    // UC - 5.1 (including getters and setters
    public boolean editProfile(String title, String val) throws UnauthorizedPageOwnerException, PersonalPageYetToBeCreatedException {
        if (info == null) {
            throw new PersonalPageYetToBeCreatedException();
        }
        return info.editProfile(this, title, val);
    }

    public boolean changeUserName(String newUserName) throws UserInformationException {
        return system.changeUserName(this, newUserName);
    }
    /*getSet*/

    public Team getMyTeam() {
        return myTeam;
    }

    public void setMyTeam(Team myTeam) {
        this.myTeam = myTeam;
    }

    public String getTraining() {
        return training;
    }

    public void setTraining(String training) {
        this.training = training;
    }

    public CoachRole getRole() {
        return role;
    }

    public void setRole(CoachRole role) {
        this.role = role;
    }

    public PersonalInfo getInfo() {
        return info;
    }

    public void setInfo(PersonalInfo info) {
        this.info = info;
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
}
