package games.controller

import games.dto.GameDto
import games.service.GameService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

class GameControllerTest extends Specification {
    GameService gameService = Mock(GameService)
    MockMvc mockMvc

    def setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new GameController(gameService)).build()
    }

    def "Find All"() {
        given: "A request to obtain games"
        List<GameDto> games = Collections.singletonList(GameDto
                .builder()
                .code("SNS-MW-USA")
                .title("Super Mario World")
                .build())

        when: "Obtain games"
        gameService.findAll() >> games
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/games")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn()
                .response

        then: "Validate if it was successful"
        response.status == HttpStatus.OK.value()
    }
}