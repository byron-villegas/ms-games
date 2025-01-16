package games.constants;

import java.util.Arrays;
import java.util.List;

public class Constants {
    // Characters
    public static final String LINE_BREAK = "\n";
    public static final String COLON = ":";
    public static final String SEMICOLON = ";";
    public static final String SPACE = " ";
    public static final String EQUAL = "=";

    // Titles
    public static final String BEGIN_TITLE_ENVIRONMENT_AND_CONFIGURATION = "Environment and configuration";

    public static final List<String> PROPERTIES_TO_EXCLUDE = Arrays.asList("credentials", "password", "token", "secret", "DB_USER", "DB_PASSWORD", "DB_HOST", "spring.data.mongodb.uri");
}