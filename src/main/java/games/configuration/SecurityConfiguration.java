package games.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties("security")
public class SecurityConfiguration {
    private List<String> whiteListUrls;
    private Cors cors;

    @Data
    static class Cors {
        private boolean allowCredentials;
        private String allowedOrigin;
        private String allowedHeader;
        private String AllowedMethod;
        private String pattern;
    }
}