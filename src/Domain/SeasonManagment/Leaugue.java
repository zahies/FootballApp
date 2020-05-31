package Domain.SeasonManagment;

import DataAccess.Exceptions.DuplicatedPrimaryKeyException;
import DataAccess.Exceptions.NoConnectionException;
import DataAccess.Exceptions.mightBeSQLInjectionException;
import DataAccess.SeasonManagmentDAL.LeaguesDAL;
import Domain.FootballManagmentSystem;
import FootballExceptions.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class Leaugue {
    private FootballManagmentSystem system = FootballManagmentSystem.getInstance();

    private UUID objectID;
    private HashMap<Integer, Season> seasons;
    /**
     * year_season
     */
    private int currentYear;

    /***DB CONSTRUCTOR*/
    public Leaugue(UUID objectID, HashMap<Integer, Season> seasons) {
        this.objectID = objectID;
        this.seasons = seasons;
    }



    public Leaugue() throws mightBeSQLInjectionException, DuplicatedPrimaryKeyException, NoPermissionException, SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException {
        objectID = UUID.randomUUID();
        FootballManagmentSystem system1 = FootballManagmentSystem.getInstance();
        this.system = system1;
        seasons = new HashMap<>();
        new LeaguesDAL().insert(this);
    }


    public void setObjectID(UUID id) {
        this.objectID = id;
    }

    public UUID getObjectID() {
        return objectID;
    }

    /**
     * UC 9.1 (Only commisioner can)
     */
    public void setLeagueIntoSystem() throws LeagueIDAlreadyExist, IDWasNotEnterdException {
        if (objectID == null) {
            throw new IDWasNotEnterdException("There is no ID !");
        } else {
            system.addLeague(this);
        }
    }


    /**
     * UC 9.2 (Only commisioner can)
     */
    public void addSeasonToLeagueByYear(int year) throws SeasonYearAlreadyExist, mightBeSQLInjectionException, DuplicatedPrimaryKeyException, NoPermissionException, SQLException, UserInformationException, UserIsNotThisKindOfMemberException, NoConnectionException {
        Season season = new Season(year);
        if (seasons.get(year) != null) {
            throw new SeasonYearAlreadyExist("season with the given year is already exist in this league !");
        } else {
            seasons.put(year, season);
        }
        new LeaguesDAL().update(this);
    }


    public Season getSeasonByYear(int year) {
        return seasons.get(year);
    }


    public HashMap<Integer, Season> getSeasons() {
        return seasons;
    }


}

