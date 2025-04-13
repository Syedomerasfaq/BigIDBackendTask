package org.bigid.matcher;

import org.bigid.model.MatchLocation;

import java.util.*;
import java.util.concurrent.Callable;

public class MatcherTask implements Callable<Map<String, List<MatchLocation>>> {

    private final String text;
    private final int currentLineOffset;
    private final int currentCharacterOffset;
    private final HashSet<String> commonNamesSet;

    public MatcherTask(String text, int currentLineOffset, HashSet<String> commonNamesSet, int currentCharacterOffset) {
        this.text = text;
        this.currentLineOffset = currentLineOffset;
        this.commonNamesSet = commonNamesSet;
        this.currentCharacterOffset = currentCharacterOffset;
    }

    @Override
    public Map<String, List<MatchLocation>> call() {
        HashMap<String, List<MatchLocation>> locationMap = new HashMap<>();
        Scanner scanner = new Scanner(text);
        int lineOffset = 0;
        int totalCharOffset = currentCharacterOffset;

        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            StringBuilder word = new StringBuilder();
            int charOffset = 0;


            for (charOffset = 0; charOffset < line.length(); charOffset++) {
                char c = line.charAt(charOffset);
                if (Character.isLetter(c)){
                    word.append(c);
                }
                else {
                    checkIfWordMatches(locationMap, lineOffset, totalCharOffset + charOffset, word.toString());
                    word.setLength(0);
                }
            }

            checkIfWordMatches(locationMap, lineOffset, totalCharOffset + charOffset, word.toString());
            totalCharOffset += line.length() + 1;
            lineOffset++;
        }

        return locationMap;
    }

    private void checkIfWordMatches(HashMap<String, List<MatchLocation>> locationMap, int lineOffset, int charOffset, String word){
        if (!word.isEmpty() && commonNamesSet.contains(word)){
            int globalLineOffset = currentLineOffset + lineOffset + 1;
            int globalCharOffset = charOffset - word.length() + 1;
            MatchLocation matchLocation = new MatchLocation(globalLineOffset, globalCharOffset);
            locationMap.computeIfAbsent(word, k -> new ArrayList<>())
                    .add(matchLocation);
        }
    }
}
