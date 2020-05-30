package Domain.PersonalPages;

import DataAccess.UserInformationDAL.PersonalPageContentDAL;
import Domain.FootballManagmentSystem;

import java.util.UUID;

public abstract class APersonalPageContent {

    private UUID objectID;

    public APersonalPageContent() {
        this.objectID = UUID.randomUUID();
    }

    public UUID getObjectID() {
        return objectID;
    }


}
