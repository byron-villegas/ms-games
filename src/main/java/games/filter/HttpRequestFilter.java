package games.filter;

import games.util.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Iterator;
import java.util.UUID;

@Slf4j
@Component
@AllArgsConstructor
public class HttpRequestFilter extends OncePerRequestFilter implements RequestFilter {
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("[START] REQUEST method: {} | operation: {} | {}", request.getMethod(), request.getRequestURI(), getHeaders(request));

        long startTime = System.nanoTime();

        // Get authorization header and validate
        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (ObjectUtils.isEmpty(authorization) || !authorization.toUpperCase().startsWith("BEARER ")) {
            filterChain.doFilter(request, response);
            logResponse(response, startTime);
            return;
        }

        // Get jwt token and validate
        final String token = authorization.split(" ")[1].trim();

        if (!jwtTokenUtil.isValidToken(token)) {
            filterChain.doFilter(request, response);
            logResponse(response, startTime);
            return;
        }

        final String rut = jwtTokenUtil.getRutFromToken(token);
        final String username = jwtTokenUtil.getUsernameFromToken(token);
        final String[] roles = jwtTokenUtil.getRolesFromToken(token);

        log.info("rut {}, username {}, roles {}", rut, username, roles);

        UserDetails userDetails = User
                .withUsername(username)
                .password(UUID.randomUUID().toString())
                .roles(roles)
                .build();

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        filterChain.doFilter(request, response);

        logResponse(response, startTime);
    }

    private String getHeaders(HttpServletRequest request) {
        StringBuilder stringBuilder = new StringBuilder();

        Iterator<String> headersNames = request.getHeaderNames().asIterator();

        while (headersNames.hasNext()) {
            String headerName = headersNames.next();

            if(!headerName.equalsIgnoreCase("authorization")) {
                stringBuilder
                        .append(headerName)
                        .append(": ")
                        .append(request.getHeader(headerName))
                        .append(" | ");
            }
        }

        return stringBuilder.toString();
    }

    private void logResponse(HttpServletResponse response, long startTime) {
        long totalTime = System.nanoTime() - startTime;

        double seconds = totalTime / (double) 1.0E9F;

        log.info("[FINISH] RESPONSE status-code: {} | execution-time: {} seconds", response.getStatus(), seconds);
    }
}