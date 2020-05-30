package Domain.Users;

import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.UsersDAL.CoachesDAL;
import Domain.Alerts.IAlert;
import Domain.FootballManagmentSystem;
import Domain.PersonalPages.APersonalPageContent;
import Domain.SeasonManagment.IAsset;
import Domain.SeasonManagment.Team;
import FootballExceptions.*;

import java.sql.SQLException;
import java.util.Queue;

public class Coach extends Member implements IAsset {
    private int valAsset;
    private Team myTeam;
    private String training;
    private CoachRole role;
    private PersonalInfo info;
    private int assetID;
    FootballManagmentSystem system = FootballManagmentSystem.getInstance();
    /**
     * CONSTRUCTOR FOR restoration object from DB
     **/
    public Coach(String name, String password, String real_Name, Queue<IAlert> alertsList, boolean isActive, boolean alertViaMail, String mailAddress, int valAsset, Team myTeam, String training, CoachRole role, PersonalInfo info, int assetID) {
        super(name, password, real_Name, alertsList, isActive, alertViaMail, mailAddress);
        this.valAsset = valAsset;
        this.myTeam = myTeam;
        this.training = training;
        this.role = role;
        this.info = info;
        this.assetID = assetID;
    }



    public Coach(String name, String realname, int id, String password, int val, String training, CoachRole role) throws mightBeSQLInjectionException, DuplicatedPrimaryKeyException, NoPermissionException, SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException {
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

        new CoachesDAL().insert(this);
    }

    /**
     * creates personal page - if personal page already exists this will override it
     * (constraint 4 - one personal page per member) NEED TO WARN MEMBER IN GUI
     *
     * @return - true if succeeded
     */
    public boolean createPersonalPage() throws mightBeSQLInjectionException, DuplicatedPrimaryKeyException, NoPermissionException, SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException {
        //todo WARN MEMBER ABOUT OVERRIDING
        if (info != null) {
            system.removePersonalPage(info);
        }
        info = new PersonalInfo(this);
        new CoachesDAL().update(this);
        return true;
    }

    /**
     * adding content to personal page
     *
     * @param content - content of some kind to be added to personal page
     * @return - true if succeeded
     */
    public boolean addContentToPersonalPage(APersonalPageContent content) throws UnauthorizedPageOwnerException, PersonalPageYetToBeCreatedException, SQLException {
        if (info == null) {
            throw new PersonalPageYetToBeCreatedException();
        }
        return info.addContentToPage(this, content);
    }

    // UC - 5.1 (including getters and setters
    public boolean editProfile(String title, String val) throws UnauthorizedPageOwnerException, PersonalPageYetToBeCreatedException, SQLException {
        if (info == null) {
            throw new PersonalPageYetToBeCreatedException();
        }
        return info.editProfile(this, title, val);
    }

    public boolean changeUserName(String newUserName) throws UserInformationException, mightBeSQLInjectionException, DuplicatedPrimaryKeyException, NoPermissionException, SQLException, UserIsNotThisKindOfMemberException, NoConnectionException {
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
    public void edit(int value) throws mightBeSQLInjectionException, DuplicatedPrimaryKeyException, NoPermissionException, SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException {
        this.valAsset = value;
        new CoachesDAL().update(this);

    }

    @Override
    public int getValue() {
        return valAsset;
    }
}
