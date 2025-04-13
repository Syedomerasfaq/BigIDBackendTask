package org.bigid.aggregator;

import org.bigid.model.MatchLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AggregatorService implements Aggregator{

    private final Map<String, List<MatchLocation>> aggregatedMap = new HashMap<>();

    @Override
    public void addMatches(Map<String, List<MatchLocation>> matches) {
        for (Map.Entry<String,List<MatchLocation>> entry : matches.entrySet()) {
            String name = entry.getKey();
            List<MatchLocation> locations = entry.getValue();

            aggregatedMap.computeIfAbsent(name, k -> new ArrayList<>()).addAll(locations);
        }
    }

    @Override
    public void printResults() {
        for (Map.Entry<String,List<MatchLocation>> entry : aggregatedMap.entrySet()) {
            System.out.println(entry.getKey() + " --> " + entry.getValue());
        }
    }
}
