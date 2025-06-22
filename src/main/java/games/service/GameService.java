package games.service;

import games.dto.GameDto;
import games.model.Game;
import games.repository.GameRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class GameService {
    private final GameRepository gameRepository;

    public List<GameDto> findAll() {
        log.info("[START] Find All");

        List<Game> games = gameRepository.findAll();

        log.info("Find All - Games [{}]", games);

        List<GameDto> mappedGames = games
                .stream()
                .map(game -> GameDto
                        .builder()
                        .code(game.getCode())
                        .title(game.getTitle())
                        .console(game.getConsole())
                        .region(game.getRegion())
                        .developer(game.getDeveloper())
                        .publisher(game.getPublisher())
                        .genres(game.getGenres())
                        .players(game.getPlayers())
                        .saveType(game.getSaveType())
                        .releasedYear(game.getReleasedYear())
                        .build())
                .collect(Collectors.toList());

        log.info("[FINISH] Find All [{}]", mappedGames);

        return mappedGames;
    }
}