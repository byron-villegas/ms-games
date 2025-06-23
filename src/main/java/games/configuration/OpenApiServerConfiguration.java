package games.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Collections;

@Configuration
@RequiredArgsConstructor
public class OpenApiServerConfiguration {
    private final OpenApiConfiguration openApiConfiguration;

    @Bean
    public OpenAPI initialize() {
        Contact contact = new Contact();
        contact.setName(openApiConfiguration.getContact().getName());
        contact.setEmail(openApiConfiguration.getContact().getEmail());
        contact.setUrl(openApiConfiguration.getContact().getUrl());

        License license = new License()
                .name(openApiConfiguration.getLicense())
                .url(openApiConfiguration.getLicenseUrl());

        Info info = new Info()
                .title(openApiConfiguration.getTitle())
                .version(openApiConfiguration.getVersion())
                .description(openApiConfiguration.getDescription())
                .termsOfService(openApiConfiguration.getTermsOfService())
                .contact(contact)
                .license(license);

        Server server = new Server();
        server.setUrl(openApiConfiguration.getServer().getUrl());
        server.setDescription(openApiConfiguration.getServer().getDescription());

        return new OpenAPI()
                .info(info)
                .servers(Collections.singletonList(server))
                .components(new Components()
                        .addSecuritySchemes(openApiConfiguration.getSecurityScheme().getName(), new SecurityScheme()
                                .type(SecurityScheme.Type.valueOf(openApiConfiguration.getSecurityScheme().getType()))
                                .scheme(openApiConfiguration.getSecurityScheme().getScheme())
                                .bearerFormat(openApiConfiguration.getSecurityScheme().getBearerFormat())))
                .addSecurityItem(new SecurityRequirement()
                        .addList(openApiConfiguration.getSecurityScheme().getName()));
    }
}