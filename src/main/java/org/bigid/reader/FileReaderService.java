package org.bigid.reader;

import org.bigid.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class FileReaderService implements ReaderService {

    private static final Logger log = LoggerFactory.getLogger(FileReaderService.class);

    private final Scanner reader;
    private int currentLineOffset = 0;
    private int currentCharOffset = 0;

    public FileReaderService(InputStream inputStream){
        this.reader = new Scanner(inputStream);
    }

    @Override
    public boolean hasNextBatch(){
        boolean hasNext = reader.hasNextLine();
        log.debug("Checking for next batch: {}", hasNext);
        return hasNext;
    }

    @Override
    public int getCurrentLineOffset(){
        return currentLineOffset;
    }

    @Override
    public int getCurrentCharacterOffset(){
        return currentCharOffset;
    }

    @Override
    public String readNextBatch() {
        StringBuilder sb = new StringBuilder();
        int linesRead = 0;
        int charsReadInBatch = 0;
        while (reader.hasNextLine() && linesRead < Configuration.BATCH_SIZE) {
            String line = reader.nextLine();
            sb.append(line).append("\n");
            charsReadInBatch += line.length() + 1;
            linesRead++;
        }

        currentCharOffset+=charsReadInBatch;
        currentLineOffset+=linesRead;
        log.debug("Read batch - lines: {}, chars: {}", linesRead, charsReadInBatch);
        return sb.toString();

    }

}
