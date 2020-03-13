package org.rhdemo.scoring;

import org.rhdemo.scoring.models.CurrentRound;
import org.rhdemo.scoring.models.Environment;
import org.rhdemo.scoring.models.Game;
import org.rhdemo.scoring.models.Guess;
import org.rhdemo.scoring.models.GameStatus;
import org.rhdemo.scoring.models.Player;
import org.rhdemo.scoring.models.PlayerLeaderboardTransaction;
import org.rhdemo.scoring.models.Round;
import org.rhdemo.scoring.services.GamingService;
import org.rhdemo.scoring.services.KafkaLeaderboard;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

@Path("/game")
public class ScoringResource {
    public static final int ROUND_POINTS = 100;
    public static final int WRONG_GUESS = 5;
    public static final String GAME_SERVER = "SFO";

    @Inject
    GamingService games;

    @Inject
    KafkaLeaderboard leaderboard;

    @Inject
    Environment env;

    @Path("/join")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public GameStatus join(Player player) {
        Game game = games.joinGame(player);
        Round round = games.firstRound(game);
        CurrentRound current = new CurrentRound(round);
        current.setPointsAvailable(ROUND_POINTS);

        GameStatus status = new GameStatus();
        status.setPlayer(player);
        status.setGame(game);
        status.setStatus("GAME_START");
        status.setCreationServer(env.CLUSTER_NAME());
        status.setGameServer(GAME_SERVER);
        status.setScoringServer(env.CLUSTER_NAME());
        status.setCurrentRound(current);
        return status;
    }


    private void error(String message) {
        Map<String, String> error = new HashMap<>();
        error.put("message", message);
        throw new WebApplicationException(Response.status(400)
                .type(MediaType.APPLICATION_JSON)
                .entity(error).build());

    }

    private GameStatus resetRound(Guess guess, Round updatedRound) {
        return null; // todo
    }

    @Path("/score")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public GameStatus scoring(Guess guess) {
        Game game = games.getGame(guess.getGame().getId());
        // todo check game status

        CurrentRound currentRound = guess.getCurrentRound();
        Round round = games.getRound(game, currentRound.getId());
        if (!round.getVersion().equals(currentRound.getVersion())) {
            return resetRound(guess, round);
        }
        GameStatus status = new GameStatus();
        status.setPlayer(guess.getPlayer());
        status.setCreationServer(guess.getCreationServer());
        status.setGameServer(GAME_SERVER);
        status.setScoringServer(env.CLUSTER_NAME());
        status.setGame(game);
        status.setScore(guess.getScore());

        boolean failed = false;
        int correctIndex = -1;
        for (int i = 0; i < currentRound.getGuess().size(); i++) {
            if (!round.getPrice().get(i).equals(currentRound.getGuess().get(i))) {
                failed = true;
                break;
            }
            correctIndex = i;
        }

        if (failed) {
            failedGuess(guess, game, currentRound, round, status, correctIndex);
        } else {
            correctGuess(guess, game, currentRound, round, status, correctIndex);
        }
        sendLeaderboardTransaction(guess, game, status);
        return status;
    }

    private void sendLeaderboardTransaction(Guess guess, Game game, GameStatus status) {
        PlayerLeaderboardTransaction tx = new PlayerLeaderboardTransaction(guess.getPlayer());
        tx.setGameId(game.getId());
        tx.setCreationServer(guess.getCreationServer());
        tx.setGameServer(GAME_SERVER);
        tx.setScoringServer(env.CLUSTER_NAME());

        tx.setRight(status.getRight());
        tx.setWrong(status.getWrong());
        tx.setScore(status.getScore());
        leaderboard.send(tx);
    }

    private void correctGuess(Guess guess, Game game, CurrentRound currentRound,
                              Round round, GameStatus status, int correctIndex) {
        status.setRight(guess.getRight() + 1);
        status.setWrong(guess.getWrong());
        if (correctIndex + 1 == round.getPrice().size()) { // COMPLETE!
            status.setScore(guess.getScore() + currentRound.getPointsAvailable());
            status.setAward(currentRound.getPointsAvailable());
            status.setStatus(GameStatus.COMPLETED_ROUND);
            // next round
            Round next = games.nextRound(game, currentRound.getId());
            if (next == null) {
                status.getGame().setState(GameStatus.GAME_OVER);
            } else {
                CurrentRound nextCurrent = new CurrentRound(next);
                nextCurrent.setPointsAvailable(ROUND_POINTS);
                status.setCurrentRound(nextCurrent);
            }
        } else {
            CurrentRound nextCurrent = new CurrentRound(round);
            nextCurrent.setPointsAvailable(currentRound.getPointsAvailable());
            status.setStatus(GameStatus.CORRECT_GUESS);
            status.setCurrentRound(nextCurrent);
            buildGuessesAndChoices(guess, correctIndex, nextCurrent);
        }
    }

    private void failedGuess(Guess guess, Game game, CurrentRound currentRound, Round round, GameStatus status, int correctIndex) {
        status.setStatus(GameStatus.BAD_GUESS);
        status.setRight(guess.getRight());
        status.setWrong(guess.getWrong() + 1);
        if (currentRound.getPointsAvailable() - WRONG_GUESS <= 0) {
            // next round
            Round next = games.nextRound(game, currentRound.getId());
            if (next == null) {
                status.getGame().setState(GameStatus.GAME_OVER);
            } else {
                CurrentRound wrong = new CurrentRound(next);
                wrong.setPointsAvailable(ROUND_POINTS);
            }
        } else {
            CurrentRound wrong = new CurrentRound(round);
            status.setCurrentRound(wrong);
            wrong.setPointsAvailable(currentRound.getPointsAvailable() - WRONG_GUESS);
            buildGuessesAndChoices(guess, correctIndex, wrong);
        }
    }

    private void buildGuessesAndChoices(Guess guess, int correctIndex, CurrentRound current) {
        if (correctIndex > -1) {
            LinkedList<Object> guesses = new LinkedList<>();
            for (int i = 0; i <= correctIndex; i++) {
                Object obj = guess.getCurrentRound().getGuess().get(i);
                guesses.add(obj);
                // remove guess from available choices
                if (obj instanceof Integer) {
                    Iterator<Integer> it = current.getChoices().iterator();
                    while (it.hasNext()) {
                        if (it.next().equals(obj)) {
                            it.remove();
                            break;
                        }
                    }
                }

            }
            current.setGuess(guesses);
        }
    }
}