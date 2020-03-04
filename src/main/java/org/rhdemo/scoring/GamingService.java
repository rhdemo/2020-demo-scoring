package org.rhdemo.scoring;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.runtime.StartupEvent;
import org.rhdemo.scoring.models.Game;
import org.rhdemo.scoring.models.Player;
import org.rhdemo.scoring.models.Round;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class GamingService {
    private List<Round> rounds;

    public void init(@Observes StartupEvent ev) throws Exception {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("rounds.json");
        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        rounds = objectMapper.readValue(is, new TypeReference<List<Round>>() {});
    }

    // todo need better state
    public Game joinGame(Player player) {
        Game game = new Game();
        game.setId("1");
        game.setDate(new Date().toString());
        game.setState("active");
        return game;
    }
    public Game getGame(String id) {
        Game game = new Game();
        game.setId("1");
        game.setDate(new Date().toString());
        game.setState("active");
        return game;
    }

    public Round firstRound(Game game) {
        return rounds.get(0);

    }

    public Round nextRound(Game game, int currentRound) {
        return rounds.get(currentRound + 1);
    }

    public Round getRound(Game game, int round) {
        return rounds.get(round);
    }
}
