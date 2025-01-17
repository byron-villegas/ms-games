package games.configuration;

import games.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import java.util.Arrays;
import java.util.List;
import java.util.stream.StreamSupport;

@Slf4j
@Configuration
@ConditionalOnExpression("'${logging.level.web}'.equalsIgnoreCase('INFO') or '${logging.level.web}'.equalsIgnoreCase('DEBUG')")
public class PropertiesLoggerConfiguration {
    private static final String TITLE_ENVIRONMENT_AND_CONFIGURATION = "Environment and configuration";
    private static final List<String> PROPERTIES_TO_EXCLUDE = Arrays.asList("credentials", "password", "token",
            "secret", "host", "uri");


    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        final Environment environment = event.getApplicationContext().getEnvironment();
        StringBuilder stringBuilderProperties = new StringBuilder();

        // Initial title
        stringBuilderProperties
                .append(Constants.LINE_BREAK)
                .append(Strings.repeat(Constants.EQUAL, 42))
                .append(Constants.SPACE)
                .append(TITLE_ENVIRONMENT_AND_CONFIGURATION)
                .append(Constants.SPACE)
                .append(Strings.repeat(Constants.EQUAL, 42))
                .append(Constants.LINE_BREAK);

        final MutablePropertySources mutablePropertySources = ((AbstractEnvironment) environment).getPropertySources();

        // Add property
        StreamSupport
                .stream(mutablePropertySources.spliterator(), false)
                .filter(propertySource -> propertySource instanceof OriginTrackedMapPropertySource)
                .map(propertySource -> ((EnumerablePropertySource<?>) propertySource).getPropertyNames())
                .flatMap(Arrays::stream)
                .distinct()
                .filter(propertySource -> PROPERTIES_TO_EXCLUDE
                        .stream()
                        .noneMatch(key -> propertySource.toLowerCase().contains(key)))
                .forEach(property -> stringBuilderProperties
                        .append(property)
                        .append(Constants.COLON)
                        .append(Constants.SPACE)
                        .append(environment.getProperty(property))
                        .append(Constants.LINE_BREAK));

        // End of message
        stringBuilderProperties.append(Strings.repeat(Constants.EQUAL, 115));

        log.info(stringBuilderProperties.toString());
    }
}