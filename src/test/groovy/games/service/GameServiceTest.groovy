package games.service

import games.dto.GameDto
import games.model.Game
import games.repository.GameRepository
import org.bson.types.ObjectId
import spock.lang.Specification

class GameServiceTest extends Specification {
    GameRepository gameRepository = Mock(GameRepository)
    GameService gameService

    def setup() {
        gameService = new GameService(gameRepository)
    }

    def "Find All"() {
        given: "A request to obtain games"

        List<Game> games = Collections.singletonList(Game
                .builder()
                .id(new ObjectId())
                .code("SNS-MW-USA")
                .title("Super Mario World")
                .build())

        when: "Obtain games"
        gameRepository.findAll() >> games
        List<GameDto> response = gameService.findAll()

        then: "Validate if it was successful"
        response != null
        !response.isEmpty()
    }
}