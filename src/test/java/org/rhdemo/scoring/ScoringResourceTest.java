package org.rhdemo.scoring;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.rhdemo.scoring.models.GameState;
import org.rhdemo.scoring.models.GameStatus;
import org.rhdemo.scoring.models.Guess;
import org.rhdemo.scoring.models.Score;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
public class ScoringResourceTest {

    static final String PLAYER = "{\n" +
            "\"player\": {" +
            "    \"id\": \"1\",\n" +
            "    \"username\": \"Emerald Wanderer\",\n" +
            "    \"creationServer\": \"SFO\",\n" +
            "    \"gameServer\": \"SFO\",\n" +
            "    \"avatar\": {\n" +
            "      \"body\": 1,\n" +
            "      \"eyes\": 3,\n" +
            "      \"mouth\": 0,\n" +
            "      \"ears\": 2,\n" +
            "      \"nose\": 1,\n" +
            "      \"color\": 3\n" +
            "    }\n" +
            "  },\n" +
            "\"game\": {" +
            "    \"id\": \"1\",\n" +
            "    \"state\": \"active\",\n" +
            "    \"date\": \"2020-03-02T13:57:18.000Z\", " +
            "    \"configuration\": {}" +
            "  }\n" +
            "}\n";

    @Test
    public void testMarshalling() throws Exception {
        ObjectMapper mapper = getObjectMapper();
        ObjectReader reader = mapper.readerFor(GameStatus.class);
        GameStatus status = reader.readValue(PLAYER);
    }

    private ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

    @Test
    public void testEndpoint() throws Exception {
        InputStream is = given()
                .when().contentType("application/json").body(PLAYER).post("/game/join")
                .then()
                .statusCode(200)
                .body("player.creationServer", equalTo("SFO"))
                .body("player.gameServer", equalTo("SFO"))
                .body("score.scoringServer", equalTo("SFO"))
                .body("game.id", equalTo("1"))
                .body("game.state", equalTo("active"))
                .body("game.date", notNullValue())
                .body("player.id", equalTo("1"))
                .body("player.username", equalTo("Emerald Wanderer"))
                .body("score.status", equalTo("START"))
                .body("score.score", equalTo(0))
                .body("score.right", equalTo(0))
                .body("score.wrong", equalTo(0))
                .body("score.award", equalTo(0))
                .body("currentRound.id", equalTo(0))
                .body("currentRound.pointsAvailable", equalTo(100))
                .body("currentRound.version", equalTo("1"))
                .body("currentRound.name", equalTo("Dollar bill"))
                .body("currentRound.choices[0]", equalTo(9))
                .body("currentRound.choices[1]", equalTo(1))
                .body("currentRound.choices[2]", equalTo(0))
                .body("currentRound.choices[3]", equalTo(5))
                .body("currentRound.choices[4]", equalTo(0))
                .body("currentRound.choices[5]", equalTo(1))
        .extract().asInputStream();

        ObjectMapper mapper = getObjectMapper();
        ObjectReader guessReader = mapper.readerFor(GameStatus.class);
        ObjectWriter guessWriter = mapper.writerFor(GameStatus.class);
        GameStatus guess = guessReader.readValue(is);
        List<Object> guesses = new LinkedList<>();
        guesses.add((Integer)1);
        guesses.add(".");
        guesses.add("");
        guesses.add("");
        guess.getCurrentRound().setGuess(guesses);
        guess.getCurrentRound().getChoices().set(1, "guess");

        String json = guessWriter.writeValueAsString(guess);

        is = given().when().contentType("application/json").body(json).post("/game/score")
                .then()
                .statusCode(200)
                .body("player.creationServer", equalTo("SFO"))
                .body("player.gameServer", equalTo("SFO"))
                .body("score.scoringServer", equalTo("SFO"))
                .body("score.status", equalTo(Score.CORRECT_GUESS))
                .body("game.id", equalTo("1"))
                .body("game.state", equalTo("active"))
                .body("game.date", notNullValue())
                .body("player.id", equalTo("1"))
                .body("player.username", equalTo("Emerald Wanderer"))
                .body("score.score", equalTo(0))
                .body("score.right", equalTo(1))
                .body("score.wrong", equalTo(0))
                .body("currentRound.id", equalTo(0))
                .body("currentRound.pointsAvailable", equalTo(100))
                .body("currentRound.version", equalTo("1"))
                .body("currentRound.name", equalTo("Dollar bill"))
                .body("currentRound.guess[0]", equalTo(1))
                .body("currentRound.guess[1]", equalTo("."))
                .body("currentRound.guess[2]", equalTo(""))
                .body("currentRound.guess[3]", equalTo(""))
                .body("currentRound.choices[0]", equalTo(9))
                .body("currentRound.choices[1]", equalTo("correct"))
                .body("currentRound.choices[2]", equalTo(0))
                .body("currentRound.choices[3]", equalTo(5))
                .body("currentRound.choices[4]", equalTo(0))
                .body("currentRound.choices[5]", equalTo(1))
                .extract().asInputStream();
        // wrong guess
        guess = guessReader.readValue(is);
        guesses = new LinkedList<>();
        guesses.add((Integer)1);
        guesses.add(".");
        guesses.add(9);
        guesses.add("");
        guess.getCurrentRound().setGuess(guesses);
        guess.getCurrentRound().getChoices().set(0, "guess");

        json = guessWriter.writeValueAsString(guess);

        is = given().when().contentType("application/json").body(json).post("/game/score")
                .then()
                .statusCode(200)
                .body("player.creationServer", equalTo("SFO"))
                .body("player.gameServer", equalTo("SFO"))
                .body("score.scoringServer", equalTo("SFO"))
                .body("score.status", equalTo(Score.BAD_GUESS))
                .body("game.id", equalTo("1"))
                .body("game.state", equalTo("active"))
                .body("game.date", notNullValue())
                .body("player.id", equalTo("1"))
                .body("player.username", equalTo("Emerald Wanderer"))
                .body("score.score", equalTo(0))
                .body("score.right", equalTo(1))
                .body("score.wrong", equalTo(1))
                .body("currentRound.id", equalTo(0))
                .body("currentRound.pointsAvailable", equalTo(95))
                .body("currentRound.version", equalTo("1"))
                .body("currentRound.name", equalTo("Dollar bill"))
                .body("currentRound.guess[0]", equalTo(1))
                .body("currentRound.guess[1]", equalTo("."))
                .body("currentRound.guess[2]", equalTo(""))
                .body("currentRound.guess[3]", equalTo(""))
                .body("currentRound.choices[0]", equalTo(9))
                .body("currentRound.choices[1]", equalTo("correct"))
                .body("currentRound.choices[2]", equalTo(0))
                .body("currentRound.choices[3]", equalTo(5))
                .body("currentRound.choices[4]", equalTo(0))
                .body("currentRound.choices[5]", equalTo(1))
                .extract().asInputStream();

        guess = guessReader.readValue(is);
        guesses = new LinkedList<>();
        guesses.add((Integer)1);
        guesses.add(".");
        guesses.add((Integer)0);
        guesses.add("");
        guess.getCurrentRound().setGuess(guesses);
        guess.getCurrentRound().getChoices().set(4, "guess");

        json = guessWriter.writeValueAsString(guess);

        is = given().when().contentType("application/json").body(json).post("/game/score")
                .then()
                .statusCode(200)
                .body("player.creationServer", equalTo("SFO"))
                .body("player.gameServer", equalTo("SFO"))
                .body("score.scoringServer", equalTo("SFO"))
                .body("score.status", equalTo(Score.CORRECT_GUESS))
                .body("game.id", equalTo("1"))
                .body("game.state", equalTo("active"))
                .body("game.date", notNullValue())
                .body("player.id", equalTo("1"))
                .body("player.username", equalTo("Emerald Wanderer"))
                .body("score.score", equalTo(0))
                .body("score.right", equalTo(2))
                .body("score.wrong", equalTo(1))
                .body("currentRound.id", equalTo(0))
                .body("currentRound.pointsAvailable", equalTo(95))
                .body("currentRound.version", equalTo("1"))
                .body("currentRound.name", equalTo("Dollar bill"))
                .body("currentRound.guess[0]", equalTo(1))
                .body("currentRound.guess[1]", equalTo("."))
                .body("currentRound.guess[2]", equalTo(0))
                .body("currentRound.guess[3]", equalTo(""))
                .body("currentRound.choices[0]", equalTo(9))
                .body("currentRound.choices[1]", equalTo("correct"))
                .body("currentRound.choices[2]", equalTo(0))
                .body("currentRound.choices[3]", equalTo(5))
                .body("currentRound.choices[4]", equalTo("correct"))
                .body("currentRound.choices[5]", equalTo(1))
               .extract().asInputStream();

        // FINISHED

        guess = guessReader.readValue(is);
        guesses = new LinkedList<>();
        guesses.add((Integer)1);
        guesses.add(".");
        guesses.add((Integer)0);
        guesses.add((Integer)0);
        guess.getCurrentRound().setGuess(guesses);
        guess.getCurrentRound().getChoices().set(2, "guess");

        json = guessWriter.writeValueAsString(guess);

        is = given().when().contentType("application/json").body(json).post("/game/score")
                .then()
                .statusCode(200)
                .body("player.creationServer", equalTo("SFO"))
                .body("player.gameServer", equalTo("SFO"))
                .body("score.scoringServer", equalTo("SFO"))
                .body("score.status", equalTo(Score.COMPLETED_ROUND))
                .body("game.id", equalTo("1"))
                .body("game.state", equalTo("active"))
                .body("game.date", notNullValue())
                .body("player.id", equalTo("1"))
                .body("player.username", equalTo("Emerald Wanderer"))
                .body("score.score", equalTo(95))
                .body("score.right", equalTo(3))
                .body("score.wrong", equalTo(1))
                .body("score.award", equalTo(95))
                .body("game.id", equalTo("1"))
                .body("game.state", equalTo("active"))
                .body("game.date", notNullValue())
                .body("player.id", equalTo("1"))
                .body("player.username", equalTo("Emerald Wanderer"))
                .body("currentRound.id", equalTo(1))
                .body("currentRound.pointsAvailable", equalTo(100))
                .body("currentRound.version", equalTo("1"))
                .body("currentRound.name", equalTo("Kernel of truth t-shirt"))
                .body("currentRound.choices[0]", equalTo(5))
                .body("currentRound.choices[1]", equalTo(4))
                .body("currentRound.choices[2]", equalTo(8))
                .body("currentRound.choices[3]", equalTo(5))
                .body("currentRound.choices[4]", equalTo(7))
                .body("currentRound.choices[5]", equalTo(8))
                .extract().asInputStream();

    }

}