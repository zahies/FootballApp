package Domain.PersonalPages;

import java.util.HashMap;

public class StatisticContent extends APersonalPageContent {

    private HashMap<String, Integer> statistics; // Key is for stats title and Value is for the val. i.e. : key - "Goals" value - "20"

    public StatisticContent() {
        statistics = new HashMap<>();
    }

    public void addStatistic(String statTitle, Integer statVal) {
        if (statistics.containsKey(statTitle)) {
            statistics.replace(statTitle, statVal);
        } else {
            statistics.put(statTitle, statVal);
        }
    }

    public HashMap<String, Integer> getStatistics() {
        return statistics;
    }
}
