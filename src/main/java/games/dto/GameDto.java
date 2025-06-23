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
    @Schema(description = "Game code", example = "SNS-MW-USA")
    private String code;
    @Schema(description = "Game title", example = "Super Mario World")
    private String title;
    @Schema(description = "Game console", example = "SNES")
    private String console;
    @Schema(description = "Game region", example = "USA")
    private String region;
    @Schema(description = "Game developer", example = "Nintendo")
    private String developer;
    @Schema(description = "Game publisher", example = "Nintendo")
    private String publisher;
    @Schema(description = "Game genres", example = "[\"Platform\"]", type = "array")
    private List<String> genres;
    @Schema(description = "Game players", example = "2", type = "integer")
    private byte players;
    @Schema(description = "Game save type", example = "Battery")
    private String saveType;
    @Schema(description = "Game released year", example = "1991", type = "integer")
    private short releasedYear;
}