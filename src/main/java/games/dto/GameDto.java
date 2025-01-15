package games.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Schema(description = "Game information")
public class GameDto {
    @Schema(description = "Game code", example = "SNS-MW-USA", pattern = "aA-zZ0-9-")
    private String code;
    @Schema(description = "Game title", example = "Super Mario World", pattern = "aA-zZ0-9-")
    private String title;
    @Schema(description = "Game console", example = "SNES", pattern = "aA-zZ0-9-")
    private String console;
    @Schema(description = "Game region", example = "USA", pattern = "aA-zZ0-9-")
    private String region;
    @Schema(description = "Game developer", example = "Nintendo", pattern = "aA-zZ0-9-")
    private String developer;
    @Schema(description = "Game publisher", example = "Nintendo", pattern = "aA-zZ0-9-")
    private String publisher;
    @Schema(description = "Game genres", example = "[\"Platform\"]", type = "array")
    private List<String> genres;
    @Schema(description = "Game players", example = "2", type = "integer", pattern = "0-9")
    private byte players;
    @Schema(description = "Game save type", example = "Battery", pattern = "aA-zZ0-9-")
    private String saveType;
    @Schema(description = "Game released year", example = "1991", pattern = "0-9")
    private short releasedYear;
}