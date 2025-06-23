package games.service

import games.dto.AuthenticationResponseDto
import games.util.JwtTokenUtil
import spock.lang.Specification

class AuthenticationServiceTest extends Specification {
    JwtTokenUtil jwtTokenUtil = Mock(JwtTokenUtil)
    AuthenticationService authenticationService = new AuthenticationService(jwtTokenUtil)

    def "Authentication"() {
        given: "A request to authenticate a user"
        String token = UUID.randomUUID().toString()
        int expiration = 3600 // 1 hour

        when: "Authenticate user"
        jwtTokenUtil.generateToken(_ as String, _ as String, _ as List) >> token
        jwtTokenUtil.getExpirationFromToken(_ as String) >> expiration
        AuthenticationResponseDto authenticationResponseDto = authenticationService.auth()


        then: "Validate if authentication was successful"
        authenticationResponseDto.accessToken == token
        authenticationResponseDto.expiration == expiration
        authenticationResponseDto.roles == ["AAA"]
    }
}