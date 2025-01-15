package games.controller;

import games.dto.GameDto;
import games.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@Tag(name = "GameController", description = "Controller for managing games operations")
@Slf4j
@RestController
@RequestMapping("games")
@AllArgsConstructor
public class GameController {
    private final GameService gameService;

    @Operation(summary = "Get all games", description = "Returns a list of games")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = GameDto.class))) })
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<GameDto> findAll() {
        log.info("START Find All");

        List<GameDto> games = gameService.findAll();

        log.info("FINISH Find All [{}]", games);

        return games;
    }
}