package Domain.SeasonManagment;

import DataAccess.SeasonManagmentDAL.GamesDAL;
import DataAccess.SeasonManagmentDAL.SeasonDAL;
import Domain.FootballManagmentSystem;
import Domain.Users.Referee;
import FootballExceptions.NotEnoughTeamsInLeague;
import javafx.util.Pair;


import java.util.*;

public class Season {
    private int objectID;
    private int year;
    private LinkedList<Pair<Integer, Team>> teams;
    /**
     * score_teams
     */
    private HashSet<Referee> referees;
    private IScorePolicy scorePolicy;
    private IPlaceTeamsPolicy placeTeamsPolicy;
    private HashSet<Game> games;
    private boolean isItTheBeginningOfSeason;

    public Season(int year) {
        if (year > 0) {
            this.year = year;
            DefaultIScorePolicy defaultIScorePolicy = new DefaultIScorePolicy();
            this.scorePolicy = defaultIScorePolicy;
            DefaultTeamsPolicy defaultTeamsPolicy = new DefaultTeamsPolicy();
            this.placeTeamsPolicy = defaultTeamsPolicy;
            teams = new LinkedList<>();
            referees = new HashSet<>();
            games = new HashSet<>();
            isItTheBeginningOfSeason = true;         /** Change after a while?? */
        } else {
            System.out.println("Illegal year was entered, please try again");
            this.year = Integer.parseInt(null);
        }
        objectID = FootballManagmentSystem.getInstance().idGenerator(new SeasonDAL(), "seasons", "seasonID");
    }


    public Season(IScorePolicy sp, IPlaceTeamsPolicy pp, int year) {
        if (sp == null) {
            DefaultIScorePolicy defaultIScorePolicy = new DefaultIScorePolicy();
            this.scorePolicy = defaultIScorePolicy;
        } else {
            this.scorePolicy = sp;
        }
        if (pp == null) {
            DefaultTeamsPolicy defaultTeamsPolicy = new DefaultTeamsPolicy();
            this.placeTeamsPolicy = defaultTeamsPolicy;
        } else {
            this.placeTeamsPolicy = pp;
        }
        this.year = year;
        teams = new LinkedList<>();
        referees = new HashSet<>();
        games = new HashSet<>();
        isItTheBeginningOfSeason = true;
        objectID = FootballManagmentSystem.getInstance().idGenerator(new SeasonDAL(), "seasons", "seasonID");

    }

    public boolean isItTheBeginningOfSeason() {
        return isItTheBeginningOfSeason;
    }

    public void setItTheBeginningOfSeason(boolean itTheBeginningOfSeason) {
        isItTheBeginningOfSeason = itTheBeginningOfSeason;
    }

    public IScorePolicy getScorePolicy() {
        return scorePolicy;
    }

    public void setScorePolicy(IScorePolicy scorePolicy) {
        this.scorePolicy = scorePolicy;
    }

    public IPlaceTeamsPolicy getPlaceTeamsPolicy() {
        return placeTeamsPolicy;
    }

    public void setPlaceTeamsPolicy(IPlaceTeamsPolicy placeTeamsPolicy) {
        this.placeTeamsPolicy = placeTeamsPolicy;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public HashSet<Game> getGames() {
        return games;
    }

    public LinkedList<Pair<Integer, Team>> getScore_teams() {
        return teams;
    }

    public void setScore_teams(int score, Team team) {
        for (Pair pair : teams) {
            if (((Team) pair.getValue()).getId() == team.getId()) {
                teams.remove(pair);
                Pair<Integer, Team> pair1 = new Pair<>(score, team);
                teams.add(pair1);
            }
        }
    }

    public HashSet<Referee> getReferees() {
        return referees;
    }

    public void setReferees(HashSet<Referee> referees) {
        this.referees = referees;
    }


    public LinkedList<Pair<Integer, Team>> calculateTheNFirstPlaces(int n) {
        LinkedList<Pair<Integer, Team>> firstPlaces = new LinkedList<>();
        LinkedList<Pair<Integer, Team>> teamsCopy = new LinkedList<>();
        teamsCopy = (LinkedList<Pair<Integer, Team>>) teams.clone();
        Pair<Integer, Team> max = new Pair<Integer, Team>(teams.getFirst().getKey(), teams.getFirst().getValue());

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < teamsCopy.size(); j++) {
                if (max.getKey() < teamsCopy.get(j).getKey()) {
                    max = teamsCopy.get(j);
                    teamsCopy.remove(max);
                }
            }
            firstPlaces.add(max);
        }
        return firstPlaces;
    }

    /**
     * UC 9.3   (only comissioner can add)
     */
    public void deleteRefereeFromSeasonByName(String ref) {
        if (ref != null) {
            for (Referee referee : referees) {
                if (ref.equals(referee.getName())) {
                    referees.remove(referee);
                    break;
                }
            }
        }
    }


    /**
     * UC 9.4   (only comissioner can add)
     */
    public void addRefereeToSeason(Referee rf) {
        if (rf != null) {
            referees.add(rf);
        }
    }


    /**
     * UC 9.5   (only comissioner can add)
     */
    public void setNewScorePolicy(IScorePolicy sp) {
        if (sp != null) {
            this.scorePolicy = sp;
        }
    }


    /**
     * UC 9.6   (only comissioner can add)
     */
    public void setNewTeamsPolicy(IPlaceTeamsPolicy pp) {
        if (pp != null) {
            this.placeTeamsPolicy = pp;
        }
    }


    /**
     * UC 9.7   (only comissioner can)
     */
    public void runPlacingTeamsAlgorithm() throws NotEnoughTeamsInLeague {
        placingAlgorithm();
    }


    public void addTeamToSeason(Team t) {
        if (t != null) {
            Pair<Integer, Team> pair = new Pair<>(t.getScore(), t);
            teams.add(pair);
        }
    }


    /**
     * UC 9.7   (only comissioner can run)
     */
    private void placingAlgorithm() throws NotEnoughTeamsInLeague {
        Calendar c = Calendar.getInstance();

        if (teams.size() > 1) {
            final int daysBetweenGames = 7;
            int increasingDays = 7;
            int i = 0;
            while (i < (teams.size() - 1) * 2) { // number of periods
                List<Integer> playing = new LinkedList<>();
                for (int j = 0; j < teams.size(); j++) {
                    for (int k = 0; k < teams.size(); k++) {
                        if (j != k && (!playing.contains(j) && !playing.contains(k)) && !(checkifGameScheduld(teams.get(j).getValue(), teams.get(k).getValue()))) {
                            /**set  Game*/
                            Referee[] twoRef = getRefereesToGame();
                            Date d = calendarToDate(c);
                            Game gameOne = new Game(teams.get(k).getValue(), teams.get(j).getValue(), d, twoRef[0], twoRef[1], this);
                            gameOne.notifyRefereesWithNewDate(new Date());                   /** alerting referees */

                            teams.get(j).getValue().addGameToUpcomingGames(gameOne);
                            teams.get(k).getValue().addGameToUpcomingGames(gameOne);
                            games.add(gameOne);
                            playing.add(j);
                            playing.add(k);
                            increasingDays = increasingDays + daysBetweenGames;
                            c.add(Calendar.DAY_OF_MONTH, increasingDays);
                            break;
                        }


                    }
                }

                i++;
            }
        } else {
            throw new NotEnoughTeamsInLeague("there is not enough teams in the season!");
        }
    }

    private boolean checkifGameScheduld(Team home, Team away) {
        for (Game game : games) {
            if (game.getHome().getName().equals(home.getName()) && game.getAway().getName().equals(away.getName()))
                return true;
        }
        return false;
    }


    public Referee[] getRefereesToGame() {               /** returns two referees for game*/
        if (referees.size() > 1) {
            List<Referee> refs = new LinkedList<>();
            for (Referee ref : referees
            ) {
                refs.add(ref);

            }
            Referee[] twoRefToJudgeGame = new Referee[2];
            int random = (int) (Math.random() % referees.size());
            twoRefToJudgeGame[0] = refs.get(random);
            for (Referee ref : refs) {
                random = (int) (Math.random() % referees.size());
                if (!ref.getName().equals(twoRefToJudgeGame[0].getName())) {         /**check if the two are not the same person*/
                    twoRefToJudgeGame[1] = ref;
                    break;
                }
            }

            return twoRefToJudgeGame;
        } else {
            System.out.println("there is not enough referees !!! ");
            return null;
        }
    }


    public int getObjectID() {
        return objectID;
    }

    private Date calendarToDate(Calendar calendar) {
        return calendar.getTime();
    }


}
