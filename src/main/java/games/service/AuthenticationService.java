package games.service;


import games.dto.AuthenticationResponseDto;
import games.util.JwtTokenUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class AuthenticationService {
    private final JwtTokenUtil jwtTokenUtil;

    public AuthenticationResponseDto auth() {
        List<String> roles = List.of("AAA");

        String rut = "11111111-1";
        String username = "user_example";

        final String token = jwtTokenUtil.generateToken(rut, username, roles);
        final int expiration = jwtTokenUtil.getExpirationFromToken(token);

        return AuthenticationResponseDto
                .builder()
                .accessToken(token)
                .expiration(expiration)
                .roles(roles)
                .build();
    }
}