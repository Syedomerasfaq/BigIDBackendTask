package org.bigid.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

public class Configuration {

    private static final Properties properties = new Properties();
    private static final Logger log = LoggerFactory.getLogger(Configuration.class);

    static {
        try {
            log.debug("Loading Configuration");
            InputStream inputStream = Configuration.class.getClassLoader().getResourceAsStream("config.properties");
            if (inputStream == null) {
                log.error("Unable to find file");
                System.exit(1);
            }
            properties.load(inputStream);

        } catch (Exception e) {
            log.error("Unable to load file");
        }
    }

    public static final String INPUT_FILE_NAME = properties.getProperty("inputFilePath");
    public static final String COMMON_NAMES_FILE = properties.getProperty("namesFilePath");
    public static final int BATCH_SIZE = Integer.parseInt(properties.getProperty("batchSize"));

}
