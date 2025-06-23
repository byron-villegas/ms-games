package games.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.List;

@Schema(description = "Authentication response data")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponseDto {
    @Schema(description = "Access Token", example = "eyJhbGciOiJIUzI1NiJ9")
    @ToString.Exclude
    private String accessToken;
    @Schema(description = "Expiration", example = "3600")
    private int expiration;
    @Schema(description = "Roles", example = "[\"READ\", \"ADMIN\"]", type = "array")
    private List<String> roles;
}