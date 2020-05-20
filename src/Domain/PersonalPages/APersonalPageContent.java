package Domain.PersonalPages;

import DataAccess.UserInformationDAL.PersonalPageContentDAL;
import Domain.FootballManagmentSystem;

public abstract class APersonalPageContent {

    int objectID;

    public APersonalPageContent() {
        this.objectID = FootballManagmentSystem.getInstance().idGenerator(new PersonalPageContentDAL(), "personalpagescontent", "objectID");
    }

    public int getObjectID() {
        return objectID;
    }
}
