package Domain.SeasonManagment;

import Domain.FootballManagmentSystem;
import FootballExceptions.IDWasNotEnterdException;
import FootballExceptions.LeagueIDAlreadyExist;
import FootballExceptions.SeasonYearAlreadyExist;

import java.util.HashMap;
import java.util.UUID;

public class Leaugue {
    private FootballManagmentSystem system;
    private int id;
    private UUID objectID;
    private HashMap<Integer, Season> seasons;
    /**
     * year_season
     */
    private int currentYear;

    public Leaugue() {
        objectID = UUID.randomUUID();
        FootballManagmentSystem system1 = FootballManagmentSystem.getInstance();
        this.system = system1;
        seasons = new HashMap<>();
    }


    public void setId(int id) {
        this.id = id;
    }

    public UUID getObjectID() {
        return objectID;
    }

    /**
     * UC 9.1 (Only commisioner can)
     */
    public void setLeagueIntoSystem() throws LeagueIDAlreadyExist, IDWasNotEnterdException {
        if (id == 0) {
            throw new IDWasNotEnterdException("There is no ID !");
        } else {
            system.addLeague(this);
        }
    }


    /**
     * UC 9.2 (Only commisioner can)
     */
    public void addSeasonToLeagueByYear(int year) throws SeasonYearAlreadyExist {
        Season season = new Season(year);
        if (seasons.get(year) != null) {
            throw new SeasonYearAlreadyExist("season with the given year is already exist in this league !");
        } else {
            seasons.put(year, season);
        }
    }


    public Season getSeasonByYear(int year) {
        return seasons.get(year);
    }


    public HashMap<Integer, Season> getSeasons() {
        return seasons;
    }

    public Integer getID() {
        return id;
    }
}

