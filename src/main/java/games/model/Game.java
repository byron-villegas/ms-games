package games.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document("games")
public class Game {
    @Id
    private ObjectId id;

    private String code;
    private String title;
    private String console;
    private String region;
    private String developer;
    private String publisher;
    private List<String> genres;
    private byte players;
    private String saveType;
    private short releasedYear;
}