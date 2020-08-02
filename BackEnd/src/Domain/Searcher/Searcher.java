package Domain.Searcher;

import Domain.SeasonManagment.Leaugue;
import Domain.SeasonManagment.Season;
import Domain.SeasonManagment.Team;
import Domain.Users.Player;
import Domain.Users.TeamManager;
import Domain.Users.TeamOwner;

import java.util.HashSet;

public abstract class Searcher {

    private HashSet<Object> answer;

    public abstract boolean search(String str);

    public void setAnswer(HashSet<Object> answer) {
        this.answer = answer;
    }

    public HashSet<Object> getAnswer() {
        return answer;
    }

    public HashSet<Player> PlayerFilter() {
        HashSet<Player> PlayerSearchResults = new HashSet<>();
        for (Object o : answer) {
            if (o instanceof Player) {
                PlayerSearchResults.add((Player) o);
            }
        }
        return PlayerSearchResults;
    }

    public HashSet<TeamOwner> TeamOwnerFilter() {
        HashSet<TeamOwner> TeamOwnerSearchResults = new HashSet<>();
        for (Object o : answer) {
            if (o instanceof TeamOwner) {
                TeamOwnerSearchResults.add((TeamOwner) o);
            }
        }
        return TeamOwnerSearchResults;
    }

    public HashSet<TeamManager> TeamManagerFilter() {
        HashSet<TeamManager> TeamManagerSearchResults = new HashSet<>();
        for (Object o : answer) {
            if (o instanceof TeamManager) {
                TeamManagerSearchResults.add((TeamManager) o);
            }
        }
        return TeamManagerSearchResults;
    }

    public HashSet<Leaugue> leaugueFilter() {
        HashSet<Leaugue> leauguesSearchResults = new HashSet<>();
        for (Object o : answer) {
            if (o instanceof Leaugue) {
                leauguesSearchResults.add((Leaugue) o);
            }
        }
        return leauguesSearchResults;
    }

    public HashSet<Team> teamFilter() {
        HashSet<Team> teamsSearchResults = new HashSet<>();
        for (Object o : answer) {
            if (o instanceof Team) {
                teamsSearchResults.add((Team) o);
            }
        }
        return teamsSearchResults;
    }

    public HashSet<Season> seasonFilter() {
        HashSet<Season> seasonsSearchResults = new HashSet<>();
        for (Object o : answer) {
            if (o instanceof Season) {
                seasonsSearchResults.add((Season) o);
            }
        }
        return seasonsSearchResults;
    }
}
