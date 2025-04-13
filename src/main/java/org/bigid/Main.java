package org.bigid;

import jdk.nashorn.internal.runtime.regexp.joni.Config;
import org.bigid.aggregator.AggregatorService;
import org.bigid.configuration.Configuration;
import org.bigid.matcher.MatcherTask;
import org.bigid.model.MatchLocation;
import org.bigid.reader.FileReaderService;
import org.bigid.reader.ReaderService;
import org.bigid.util.NameLoader;
import org.bigid.util.ResourceLoaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        try {

            String filePath = Configuration.INPUT_FILE_NAME;
            log.info("Starting processing for file: {}", filePath);

            ReaderService fileReaderService = new FileReaderService(ResourceLoaderUtil.loadFileFromResource(filePath));
            HashSet<String> names = NameLoader.loadNames(Configuration.COMMON_NAMES_FILE);

            ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            AggregatorService aggregatorService = new AggregatorService();

            List<Future<Map<String, List<MatchLocation>>>> futures = new ArrayList<>();

            while (fileReaderService.hasNextBatch()){
                int startLineOffset = fileReaderService.getCurrentLineOffset();
                int startCharOffset = fileReaderService.getCurrentCharacterOffset();
                String text = fileReaderService.readNextBatch();
                MatcherTask matcherTask = new MatcherTask(text,startLineOffset,names,startCharOffset);
                Future<Map<String, List<MatchLocation>>> future = executorService.submit(matcherTask);
                log.debug("Submitting task for line offset: {}, char offset: {}", startLineOffset, startCharOffset);
                futures.add(future);
            }

            executorService.shutdown();
            boolean isTerminated = executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            for (Future<Map<String, List<MatchLocation>>> future : futures) {
                Map<String, List<MatchLocation>> result = future.get();
                aggregatorService.addMatches(result);
            }
            log.info("All tasks completed. Printing results.");
            aggregatorService.printResults();
        }
        catch (Exception e) {
            log.error("An error occurred while processing the task", e);
        }
    }


}