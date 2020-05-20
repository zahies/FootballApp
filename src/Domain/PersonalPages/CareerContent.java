package Domain.PersonalPages;

import javafx.util.Pair;

import java.util.HashMap;

public class CareerContent extends APersonalPageContent {
    /**
     * Key is pair(first string for team and the other for role page owner had in that team and value is the year he started
     */
    HashMap<Pair<String, String>, Integer> career;

    public CareerContent() {
        this.career = new HashMap<>();
    }

    public void addToCareer(String team, String role, Integer year) {
        Pair<String, String> pair = new Pair(team, role);
        if (career.containsKey(pair)) {
            career.replace(pair, year);
        } else {
            career.put(pair, year);
        }
    }

    public HashMap<Pair<String, String>, Integer> getCareer() {
        return career;
    }

    public int getObjectID() {
        return objectID;
    }
}
