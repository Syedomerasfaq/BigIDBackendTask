package org.bigid.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class ResourceLoaderUtil {

    private static final Logger log = LoggerFactory.getLogger(ResourceLoaderUtil.class);


    public static InputStream loadFileFromResource(String resourceName) throws IOException {
        log.info("Loading file as InputStream: {}", resourceName);

        InputStream inputStream = ResourceLoaderUtil.class.getClassLoader().getResourceAsStream(resourceName);
        if (inputStream == null) {
            log.error("Could not find the resource: {}", resourceName);
            throw new FileNotFoundException("Resource not found: " + resourceName);
        }
        log.info("Successfully loaded file as InputStream: {}", resourceName);
        return inputStream;
    }

}
