package games.controller

import games.dto.AuthenticationResponseDto
import games.service.AuthenticationService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

class AuthenticationControllerTest extends Specification {
    AuthenticationService authenticationService = Mock(AuthenticationService)
    MockMvc mockMvc

    def setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new AuthenticationController(authenticationService)).build()
    }

    def "Authentication"() {
        given: "A request to authenticate a user"
        AuthenticationResponseDto authenticationResponseDto = AuthenticationResponseDto
                .builder()
                .accessToken(UUID.randomUUID().toString())
                .expiration(3600) // 1 hour
                .roles(["READ", "WRITE"])
                .build()

        when: "Authenticate user"
        authenticationService.auth() >> authenticationResponseDto
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post("/oauth/token")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn()
                .response

        then: "Validate if authentication was successful"
        response.status == HttpStatus.OK.value()
    }

    def "Validate All Role"() {
        given: "A request to validate a role"

        when: "Validate role"
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post("/oauth/all"))
                .andReturn()
                .response

        then: "Validate if role is valid"
        response.status == HttpStatus.OK.value()
    }

    def "Validate Role"() {
        given: "A request to validate a role"

        when: "Validate role"
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post("/oauth/role"))
                .andReturn()
                .response

        then: "Validate if role is valid"
        response.status == HttpStatus.OK.value()
    }
}
