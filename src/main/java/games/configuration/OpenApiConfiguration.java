package games.configuration;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("open.api")
public class OpenApiConfiguration {
    private String title;
    private String description;
    private String version;
    private String termsOfService;
    private Contact contact;
    private String license;
    private String licenseUrl;
    private Server server;

    @Data
    static class Contact {
        private String name;
        private String url;
        private String email;
    }

   @Data
    static class Server {
        private String url;
        private String description;
    }
}
