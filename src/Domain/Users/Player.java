package Domain.Users;

import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.UsersDAL.PlayersDAL;
import Domain.Alerts.IAlert;
import Domain.Events.*;
import Domain.FootballManagmentSystem;
import Domain.PersonalPages.APersonalPageContent;
import Domain.SeasonManagment.IAsset;
import Domain.SeasonManagment.Team;
import FootballExceptions.*;

import java.sql.SQLException;
import java.util.Date;
import java.util.Queue;

public class Player extends Member implements IAsset {
    private int valAsset;
    private final int assetID;
    private Team myTeam;
    private String role;
    private PersonalInfo info;
    private Date DateOfBirth;
    FootballManagmentSystem system = FootballManagmentSystem.getInstance();
    private double FootballRate;

    /**
     * CONSTRUCTOR FOR restoration object from DB
     **/
    public Player(String name, String password, String real_Name, Queue<IAlert> alertsList, boolean isActive, boolean alertViaMail, String mailAddress, int valAsset, int assetID, Team myTeam, String role, PersonalInfo info, Date dateOfBirth, double footballRate) {
        super(name, password, real_Name, alertsList, isActive, alertViaMail, mailAddress);
        this.valAsset = valAsset;
        this.assetID = assetID;
        this.myTeam = myTeam;
        this.role = role;
        this.info = info;
        DateOfBirth = dateOfBirth;
        FootballRate = footballRate;

    }



    public Player(String name, String realname, int id, String password, int valAsset, String role, Date dateOfBirth) throws mightBeSQLInjectionException, DuplicatedPrimaryKeyException, NoPermissionException, SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException {
        super(name, id, password, realname);
        this.valAsset = valAsset;
        this.role = role;
        assetID = system.generateAssetID();
        system.addTeamAssets(this);
        DateOfBirth = dateOfBirth;
        FootballRate = 5;
        if (!(system.getMembers().containsKey(this.name))) {
            try {
                system.addMember(this);
            } catch (UserInformationException e) {
                e.printStackTrace();
            }
        }

        new PlayersDAL().insert(this);
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
        new PlayersDAL().update(this);
        return true;
    }

    /**
     * adding content to personal page
     *
     * @param content - content of some kind to be added to personal page
     * @return - true if succeeded
     */
    public boolean addContentToPersonalPage(APersonalPageContent content) throws PersonalPageYetToBeCreatedException, UnauthorizedPageOwnerException, SQLException {
        if (info == null) {
            throw new PersonalPageYetToBeCreatedException();
        }
        return info.addContentToPage(this, content);
    }

    // UC - 4.1 (including getters and setters
    public boolean editProfile(String title, String val) throws PersonalPageYetToBeCreatedException, UnauthorizedPageOwnerException, SQLException {
        if (info == null) {
            return false;
        }
        return info.editProfile(this, title, val);
    }

    /**
     * inCase of event the player FootballRate will change accordingly
     *
     * @param event - event the player caused
     */
    public void changePlayerRate(AGameEvent event) throws mightBeSQLInjectionException, DuplicatedPrimaryKeyException, NoPermissionException, SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException {
        if (event instanceof Foul) {
            if (FootballRate >= 0.05) {
                FootballRate = FootballRate - 0.05;
            } else {
                FootballRate = 0;
            }
        }
        if (event instanceof Goal) {
            if (FootballRate <= 9.75) {
                FootballRate = FootballRate + 0.25;
            } else {
                FootballRate = 10;
            }
        }
        if (event instanceof OffSide) {
            if (FootballRate >= 0.01) {
                FootballRate = FootballRate - 0.01;
            } else {
                FootballRate = 0;
            }
        }
        if (event instanceof RedCard) {
            if (FootballRate >= 0.3) {
                FootballRate = FootballRate - 0.3;
            } else {
                FootballRate = 0;
            }
        }
        if (event instanceof YellowCard) {
            if (FootballRate >= 0.15) {
                FootballRate = FootballRate - 0.15;
            } else {
                FootballRate = 0;
            }
        }
        if (myTeam != null) {
            myTeam.calculatePlayerFootballRate();
        }
        new PlayersDAL().update(this);
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public PersonalInfo getInfo() {
        return info;
    }

    public void setInfo(PersonalInfo info) {
        this.info = info;
    }

    public Date getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public double getFootballRate() {
        return FootballRate;
    }

    @Override
    public int getAssetID() {
        return assetID;
    }

    @Override
    public void edit(int value) throws mightBeSQLInjectionException, DuplicatedPrimaryKeyException, NoPermissionException, SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException {
        this.valAsset = value;
        new PlayersDAL().update(this);
    }

    @Override
    public int getValue() {
        return valAsset;
    }


}
