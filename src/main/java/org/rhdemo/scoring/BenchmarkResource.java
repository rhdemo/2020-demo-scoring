package org.rhdemo.scoring;

import org.rhdemo.scoring.models.Avatar;
import org.rhdemo.scoring.models.Game;
import org.rhdemo.scoring.models.GameState;
import org.rhdemo.scoring.models.GameStatus;
import org.rhdemo.scoring.models.Player;
import org.rhdemo.scoring.models.Score;
import org.rhdemo.scoring.services.KafkaLeaderboard;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.time.OffsetDateTime;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

@Path("benchmark")
@ApplicationScoped
public class BenchmarkResource {

    Executor executor;
    AtomicBoolean running = new AtomicBoolean();

    @Inject
    KafkaLeaderboard leaderboard;

    @GET
    @Path("abort")
    public String abort() {
        running.set(false);
        return "aborted";
    }

    @GET
    @Produces("text/html")
    public String benchmark(@QueryParam("players") @DefaultValue("1000") int players,
                            @QueryParam("num") @DefaultValue("10") int numTransactions,
                            @QueryParam("delay") @DefaultValue("1") int delay) {
        if (executor == null) executor = Executors.newSingleThreadExecutor();
        if (running.get()) {
            return "<h1>Benchmark is already running</h1>";
        }
        running.set(true);

        Game game = new Game();
        game.setState(GameState.active);
        game.setId("benchmark:" + System.currentTimeMillis());
        game.setDate(OffsetDateTime.now());
        Avatar avatar = new Avatar();
        Score score = new Score();
        score.setScoringServer("SFO");
        Player player = new Player();
        player.setCreationServer("SFO");
        player.setGameServer("SFO");
        player.setAvatar(avatar);
        GameStatus status = new GameStatus();
        status.setPlayer(player);
        status.setScore(score);
        status.setGame(game);
        executor.execute(() -> {

            for (int tx = 0; tx < numTransactions; tx++) {
                if (!running.get()) return;
                if (tx % 3 == 0) {
                    score.setAward(100);
                    score.setScore(score.getScore() + 100);
                }
                score.setRight(score.getScore() + 1);
                for (int p = 0; p < players; p++) {
                    if (!running.get()) return;
                    player.setId(Integer.toString(p));
                    player.setUsername("user" + p);
                    leaderboard.send(status);
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                    }
                }
            }

        });
        return "<h1>Executed Benchmark</h1>";
    }
}
