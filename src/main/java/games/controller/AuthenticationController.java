package games.controller;

import games.dto.AuthenticationResponseDto;
import games.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("oauth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(value = "token")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponseDto auth() {
        AuthenticationResponseDto authenticationResponseDto = authenticationService.auth();

        return authenticationResponseDto;
    }

    @PostMapping(value = "security")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole(@environment.getProperty('oauth.authorization.operations.security').split(','))")
    public String security() {
        return "OK";
    }
}