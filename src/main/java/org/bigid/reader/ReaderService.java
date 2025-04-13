package org.bigid.reader;

public interface ReaderService {
    boolean hasNextBatch();
    int getCurrentLineOffset();
    int getCurrentCharacterOffset();
    String readNextBatch();
}
