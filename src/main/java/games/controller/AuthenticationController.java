package games.controller;

import games.dto.AuthenticationResponseDto;
import games.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "AuthenticationController", description = "Controller for managing authentication operations")
@Slf4j
@RestController
@RequestMapping("oauth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Operation(summary = "Token", description = "Generate authentication token")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticationResponseDto.class)) })
    })
    @PostMapping(value = "token")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponseDto authentication() {
        log.info("[START] Authentication");

        AuthenticationResponseDto authenticationResponseDto = authenticationService.auth();

        log.info("[FINISH] Authentication [{}]", authenticationResponseDto);

        return authenticationResponseDto;
    }

    @Operation(summary = "All", description = "Test endpoint for all roles")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PostMapping(value = "all")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole(@environment.getProperty('oauth.authorization.oauth.operations.all').split(','))")
    @SecurityRequirement(name = "bearerAuth")
    public void all() {
        log.info("[START] All");

        log.info("[FINISH] All");
    }

    @Operation(summary = "Role", description = "Test endpoint for admin role")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PostMapping(value = "role")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole(@environment.getProperty('oauth.authorization.oauth.operations.role').split(','))")
    @SecurityRequirement(name = "bearerAuth")
    public void role() {
        log.info("[START] Role");

        log.info("[FINISH] Role");
    }
}