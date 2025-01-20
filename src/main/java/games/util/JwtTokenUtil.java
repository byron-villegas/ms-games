package games.util;

import games.configuration.JwtConfiguration;
import games.constants.Constants;
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

    public String generateToken(final String rut, final String username, final List<String> roles) {
        return createToken(rut, username, roles);
    }

    private String createToken(final String rut, final String username, final List<String> roles) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.RUT, rut);
        claims.put(Constants.USERNAME, username);
        claims.put(Constants.ROLES, roles);

        return Jwts
                .builder()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + (jwtConfiguration.getDuration() * jwtConfiguration.getDuration()) * 1000))
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
        return getClaimFromToken(token, Constants.RUT).toString();
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Constants.USERNAME).toString();
    }

    @SuppressWarnings("unchecked")
    public String[] getRolesFromToken(String token) {
        return ((List<String>) getAllClaimsFromToken(token)
                .get(Constants.ROLES)).toArray(new String[0]);
    }

    public Object getClaimFromToken(String token, String key) {
        return getAllClaimsFromToken(token)
                .get(key);
    }

    public int getExpirationFromToken(String token) {
        return Integer.parseInt(getAllClaimsFromToken(token)
                .get(Claims.EXPIRATION)
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