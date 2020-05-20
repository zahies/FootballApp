package Domain.Searcher;

import Domain.FootballManagmentSystem;
import Domain.SeasonManagment.Field;
import Domain.SeasonManagment.IAsset;
import Domain.SeasonManagment.Team;
import Domain.Users.Member;

import java.util.HashSet;
import java.util.LinkedList;

public class SearchByKeyword extends Searcher {
    @Override
    public boolean search(String str) {

        HashSet<Object> anstoquerry = new HashSet<>();
        HashSet<Member> tmpMemb = new HashSet<>();
        HashSet<Team> tmpteam = new HashSet<>();
        HashSet<IAsset> tmpasset = new HashSet<>(); // stadium field not for now?
        for (LinkedList<Member> list : FootballManagmentSystem.getInstance().getMembers().values()) {
            for (Member m : list) {
                tmpMemb.add(m);
            }

        }
        for (Team team : FootballManagmentSystem.getInstance().getAllTeams().values()) {
            tmpteam.add(team);

        }
        for (IAsset asset : FootballManagmentSystem.getInstance().getAllAssests().values()) {
            if (asset instanceof Field) {
                tmpasset.add(asset);
            }
        }
        for (Member m : tmpMemb) {
            String[] partofthetext = str.split(" ");
            for (int i = 0; i < partofthetext.length; i++) {
                if (m.getReal_Name().equals(partofthetext[i])) {
                    anstoquerry.add(m);
                }
            }
        }
        for (Team team : tmpteam) {
            String[] partofthetext = str.split(" ");
            for (int i = 0; i < partofthetext.length; i++) {
                if (team.getName().equals(partofthetext[i])) {
                    anstoquerry.add(team);
                }
            }
        }
        if (anstoquerry.size() == 0) {
            return false;
        }
        setAnswer(anstoquerry);

        return true;
    }
}
