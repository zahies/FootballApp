package Domain.Alerts;

import Domain.PersonalPages.APersonalPageContent;
import Domain.Users.PersonalInfo;

import java.util.UUID;

public class PersonalPageAlert implements IAlert {

    private UUID objectID;
    private PersonalInfo personalPage;
    private APersonalPageContent newContent;
    private boolean hadSent;

    public PersonalPageAlert(PersonalInfo personalPage, APersonalPageContent newContent) {
        this.personalPage = personalPage;
        this.newContent = newContent;
        objectID = UUID.randomUUID();
        hadSent = false;
    }


    public UUID getObjectID() {
        return objectID;
    }

    public PersonalInfo getPersonalPage() {
        return personalPage;
    }

    public APersonalPageContent getNewContent() {
        return newContent;
    }

    @Override
    public String view() {
        return this.toString();
    }

    @Override
    public String getType() {
        return "Personal Page Alert";
    }

    @Override
    public String toString() {
        return "new content = " + newContent + " , personal page = " + personalPage.getPageContent();
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
