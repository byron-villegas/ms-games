package games.util;

import games.configuration.JwtConfiguration;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenUtil {
    private final JwtConfiguration jwtConfiguration;
    private static final String USERNAME = "username";
    private static final String ROLES = "roles";
    private static final String RUT = "rut";
    private static final int MIL = 1000;
    private static final String EXPIRATION = "exp";

    public String generateToken(final String rutCliente, final String username, final List<String> roles) {
        return createToken(rutCliente, username, roles);
    }

    private String createToken(final String rutCliente, final String username, final List<String> roles) {
        final Map<String, Object> claims = new HashMap<>();
        claims.put(RUT, rutCliente);
        claims.put(USERNAME, username);
        claims.put(ROLES, roles);

        return Jwts
                .builder()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + (jwtConfiguration.getDuration() * jwtConfiguration.getDuration()) * MIL))
                .claims(claims)
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtConfiguration.getSecret())))
                .compact();
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts
                .parser()
                .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtConfiguration.getSecret())))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getRutFromToken(String token) {
        return getClaimFromToken(token, RUT).toString();
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, USERNAME).toString();
    }

    public String[] getRolesFromToken(String token) {
        return ((List<String>) getAllClaimsFromToken(token)
                .get(ROLES)).toArray(new String[0]);
    }

    public Object getClaimFromToken(String token, String key) {
        return getAllClaimsFromToken(token)
                .get(key);
    }

    public int getExpirationFromToken(String token) {
        return Integer.parseInt(getAllClaimsFromToken(token)
                .get(EXPIRATION)
                .toString());
    }

    public boolean isValidToken(String token) {
        try {
            Jwts
                    .parser()
                    .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtConfiguration.getSecret())))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}