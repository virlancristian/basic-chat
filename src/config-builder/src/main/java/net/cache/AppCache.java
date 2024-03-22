package net.cache;

import net.io.TextFileOperator;

import java.io.File;
import java.util.List;

public class AppCache {
    public static List<String> APPLICATION_PROPERTIES;

    public AppCache() {
        APPLICATION_PROPERTIES = new TextFileOperator("cache/application_properties_template.txt").readAllLines();
    }
}
