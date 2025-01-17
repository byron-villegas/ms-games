package games.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Iterator;

@Slf4j
@Component
public class HttpRequestFilter extends OncePerRequestFilter implements RequestFilter {

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("[START] REQUEST method: {} | operation: {} | {}", request.getMethod(), request.getRequestURI(), getHeaders(request));

        long startTime = System.nanoTime();

        filterChain.doFilter(request, response);

        long totalTime = System.nanoTime() - startTime;

        double seconds = totalTime / (double) 1.0E9F;

        log.info("[FINISH] RESPONSE status-code: {} | execution-time: {} seconds", response.getStatus(), seconds);
    }

    private String getHeaders(HttpServletRequest request) {
        StringBuilder stringBuilder = new StringBuilder();

        Iterator<String> headersNames = request.getHeaderNames().asIterator();

        while (headersNames.hasNext()) {
            String headerName = headersNames.next();
            stringBuilder
                    .append(headerName)
                    .append(": ")
                    .append(request.getHeader(headerName))
                    .append(" | ");
        }

        return stringBuilder.toString();
    }
}