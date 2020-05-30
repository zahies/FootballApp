package Domain.Users;

import Domain.Alerts.IAlert;
import Domain.Alerts.PersonalPageAlert;
import Domain.PersonalPages.APersonalPageContent;
import Domain.PersonalPages.ProfileContent;
import Domain.SeasonManagment.Game;
import Domain.FootballManagmentSystem;
import Domain.SystemLog;
import FootballExceptions.PersonalPageYetToBeCreatedException;
import FootballExceptions.UnauthorizedPageOwnerException;

import java.util.LinkedList;
import java.util.Observable;

public class PersonalInfo extends Observable {

    private int pageID;
    private Member pageMemberOwner;
    /**
     * In case this is a coach/player personal page
     */
    private LinkedList<Member> teamPageMembersOwners;
    /**
     * In case this is a team personal page
     */
    private String pageTitle;
    private ProfileContent profile;
    private LinkedList<APersonalPageContent> pageContent;
    private LinkedList<Fan> followers;

    /**
     * constructor for TESTING ONLY!!!
     *
     * @param pageID
     */
    public PersonalInfo(int pageID) {
        this.pageID = pageID;
    }

    public PersonalInfo(Member pageMemberOwner) {
        if (pageMemberOwner instanceof TeamManager) {
            teamPageMembersOwners = new LinkedList<>();
            teamPageMembersOwners.add(pageMemberOwner);
            this.pageTitle = ((TeamManager) pageMemberOwner).getMyTeam().getName();
        } else {
            this.pageTitle = pageMemberOwner.getReal_Name();
        }
        this.pageMemberOwner = pageMemberOwner;
        FootballManagmentSystem footballManagmentSystem = FootballManagmentSystem.getInstance();
        this.pageID = footballManagmentSystem.generatePageID();
        footballManagmentSystem.addPersonalPage(this);
        SystemLog.getInstance().UpdateLog(this.pageMemberOwner.getName() + " created his personal page");
        this.followers = new LinkedList<>();
        pageContent = new LinkedList<>();
    }


    /**
     * editing profile section in personal page
     *
     * @param memberEditing - must be owner member (constraint 4.a.
     * @param title         - info title
     * @param val           - value
     * @return - true if succeeded
     */
    public boolean editProfile(Member memberEditing, String title, String val) throws UnauthorizedPageOwnerException, PersonalPageYetToBeCreatedException {
        if (!isPageOwner(memberEditing)) { //for constraint 4.a.
            throw new UnauthorizedPageOwnerException();
        }
        if (profile == null) {
            throw new PersonalPageYetToBeCreatedException();
        } else {
            profile.addFeatureToProfile(title, val);
            SystemLog.getInstance().UpdateLog(this.pageMemberOwner.getName() + " edited content on personal page");
            return true;
        }
    }

    /**
     * adding content to personal page
     *
     * @param memberContentMaker - must be owner member
     * @param content            - abstract - can be any type of content
     * @return - true if succeeded
     */
    public boolean addContentToPage(Member memberContentMaker, APersonalPageContent content) throws UnauthorizedPageOwnerException {
        if (!isPageOwner(memberContentMaker)) { //for constraint 4.a.
            throw new UnauthorizedPageOwnerException();
        }
        if (content instanceof ProfileContent) {
            this.profile = (ProfileContent) content;
        } else {
            pageContent.add(content);
        }
        SystemLog.getInstance().UpdateLog(this.pageMemberOwner.getName() + " added content to personal page");
        IAlert newContentAlert = new PersonalPageAlert(this, content);
        notifyFansOnNewContent(newContentAlert);
        return true;
    }

    /**
     * part of UC 3.2 - adding the fan to the observers list
     *
     * @param fan - fan
     */
    public void addFollower(Fan fan) {
        followers.add(fan);
    }

    /**
     * part of UC 3.2 - removing the fan from the observers list
     *
     * @param fan - fan
     */
    public void removeFollower(Fan fan) {
        followers.remove(fan);
    }

    /**
     * notifying anyone on the observers list about a game event related to the personal page
     *
     * @param newAlert - game event alert
     * @param game     - the game where the event happened
     */
    public void notifyInfo(IAlert newAlert, Game game) { /// notify for game alert
        for (Fan f : followers) {
            f.update(game, newAlert);
        }
    }

    /**
     * notifying anyone on the observers list about a change in the page
     *
     * @param newContentAlert- new content on page alert
     */
    private void notifyFansOnNewContent(IAlert newContentAlert) { /// notify for new content on page
        for (Fan f : followers) {
            f.update(this, newContentAlert);
        }

    }

    public boolean isPageOwner(Member member) {
        if (pageMemberOwner != null) {
            return pageMemberOwner.equals(member);
        }
        if (teamPageMembersOwners != null) {
            return teamPageMembersOwners.contains(member);
        }
        return false;
    }

    /**
     * view the personal page
     */
    public void viewPersonalPage() {
        /// activate function from GUI
    }

    /**
     * TO BE USED ONLY BY TEAM
     */
    public void addTeamPageMemberOwner(Member pageMemberOwner) {
        if (teamPageMembersOwners != null) {
            if (!teamPageMembersOwners.contains(pageMemberOwner)) {
                teamPageMembersOwners.add(pageMemberOwner);
            }
        }
    }

    /**
     * TO BE USED ONLY BY TEAM
     */
    public void removeOwnerFromPageMemberOwner(Member member) {
        if (teamPageMembersOwners != null) {
            teamPageMembersOwners.remove(member);
        }
    }

    public int getPageID() {
        return pageID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonalInfo that = (PersonalInfo) o;

        return pageID == that.pageID;
    }

    @Override
    public int hashCode() {
        return pageID;
    }

    public Member getPageMemberOwner() {
        return pageMemberOwner;
    }

    public LinkedList<Member> getTeamPageMembersOwners() {
        return teamPageMembersOwners;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public ProfileContent getProfile() {
        return profile;
    }

    public LinkedList<APersonalPageContent> getPageContent() {
        return pageContent;
    }

    public LinkedList<Fan> getFollowers() {
        return followers;
    }


    @Override
    public String toString() {
        return "PersonalInfo{" +
                "pageID=" + pageID +
                ", pageMemberOwner=" + pageMemberOwner.getName() +
                ", pageTitle='" + pageTitle + '\'' +
                ", profile=" + profile +
                ", pageContent=" + pageContent +
                ", followers=" + followers +
                '}';
    }
}
