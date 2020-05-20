package Domain.Searcher;

import Domain.FootballManagmentSystem;
import Domain.Users.Member;

import java.util.HashSet;
import java.util.LinkedList;

public class SearchByName extends Searcher {

    @Override
    public boolean search(String str) {
        HashSet<Object> anstoquerry = new HashSet<>();
        HashSet<Member> tmp = new HashSet<>();
        for (LinkedList<Member> list : FootballManagmentSystem.getInstance().getMembers().values()) {
            for (Member m : list) {
                tmp.add(m);
            }

        }
        for (Member m : tmp) {
            if (m.getReal_Name().equals(str)) {
                anstoquerry.add((Object) m);
            }
        }
        if (anstoquerry.size() == 0) {
            return false;
        }

        setAnswer(anstoquerry);
        return true;
    }
}
