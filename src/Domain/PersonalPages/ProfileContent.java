package Domain.PersonalPages;

import Domain.SeasonManagment.Team;

import java.util.HashMap;

public class ProfileContent extends APersonalPageContent {

    private HashMap<String, String> profile; // Key is for title and Value is for the val. i.e. : key - "height" value - "1.80 cm"
    private Team team;

    public ProfileContent() {
        this.profile = new HashMap<>();
    }

    public void addFeatureToProfile(String title, String val) {
        if (profile.containsKey(title)) {
            profile.replace(title, val);
        } else {
            profile.put(title, val);
        }
    }

    public HashMap<String, String> getProfile() {
        return profile;
    }

    public int getObjectID() {
        return objectID;
    }
}
