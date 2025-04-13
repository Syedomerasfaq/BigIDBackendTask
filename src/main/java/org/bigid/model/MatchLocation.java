package org.bigid.model;

public class MatchLocation {
    private int lineOffset;
    private int charOffset;

    public MatchLocation(int lineOffset, int charOffset) {
        this.lineOffset = lineOffset;
        this.charOffset = charOffset;
    }

    public int getLineOffset() {
        return lineOffset;
    }

    public void setLineOffset(int lineOffset) {
        this.lineOffset = lineOffset;
    }

    public int getCharOffset() {
        return charOffset;
    }

    public void setCharOffset(int charOffset) {
        this.charOffset = charOffset;
    }

    @Override
    public String toString() {
        return "[lineOffset=" + lineOffset + ", charOffset=" + charOffset + "]";
    }
}
