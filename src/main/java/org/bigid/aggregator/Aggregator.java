package org.bigid.aggregator;

import org.bigid.model.MatchLocation;

import java.util.List;
import java.util.Map;

public interface Aggregator {
    void addMatches(Map<String, List<MatchLocation>> matches);
    void printResults();
}
