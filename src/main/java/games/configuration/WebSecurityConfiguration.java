package games.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {
    private final SecurityConfiguration securityConfiguration;
    //private final JwtTokenFilter jwtTokenFilter;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(securityConfiguration
                                .getWhiteListUrls()
                                .toArray(new String[0]))
                        .permitAll()
                        .anyRequest()
                        .authenticated());

        return http.build();
    }

    // Used by Spring Security if CORS is enabled.
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(securityConfiguration.getCors().isAllowCredentials());
        corsConfiguration.addAllowedOrigin(securityConfiguration.getCors().getAllowedOrigin());
        corsConfiguration.addAllowedHeader(securityConfiguration.getCors().getAllowedHeader());
        corsConfiguration.addAllowedMethod(securityConfiguration.getCors().getAllowedMethod());

        urlBasedCorsConfigurationSource
                .registerCorsConfiguration(securityConfiguration.getCors().getPattern(), corsConfiguration);

        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
}