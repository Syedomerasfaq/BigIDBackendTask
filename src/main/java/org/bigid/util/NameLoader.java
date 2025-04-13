package org.bigid.util;

import java.io.*;
import java.util.HashSet;

public class NameLoader {
    public static HashSet<String> loadNames(String resourceName) throws IOException {
        HashSet<String> names = new HashSet<>();

        try (InputStream inputStream = ResourceLoaderUtil.loadFileFromResource(resourceName)) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    names.add(line.trim());
                }
            }
        }

        return names;
    }
}