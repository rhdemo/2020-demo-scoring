package org.rhdemo.scoring;

import org.rhdemo.scoring.models.Answer;
import org.rhdemo.scoring.models.Guess;
import org.rhdemo.scoring.models.Item;
import org.rhdemo.scoring.models.Score;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/scores")
public class ScoringResource {

    private void error(String message) {
        Map<String, String> error = new HashMap<>();
        error.put("message", message);
        throw new WebApplicationException(Response.status(400)
                .type(MediaType.APPLICATION_JSON)
                .entity(error).build());

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Score scoring(Guess guess) {
        String game = guess.getGame();
        String player = guess.getPlayer();
        Item item = guess.getItem();
        List<Answer> answers = guess.getAnswers();
        int pointsAvailable = guess.getPointsAvailable();
        if (game == null || player == null || item == null || answers == null) {
            error("Malformed request");
        }
        if (item.getPrice().size() != answers.size()) {
            error("Answer size not equal to price size");

        }

        boolean correct = true;

        for (int i = 0; i < answers.size(); i++) {
            Answer attempt = answers.get(i);
            if (!item.getAnswers().get(i).getFormat().equals(attempt.getFormat())) {
                error("Guess index " + i + " format mismatch");
            }
            if ("decimal".equals(attempt.getFormat())) continue;
            if ("correct".equals(attempt.getResult())) continue;
            if (attempt.getResult() != null && !"pending".equals(attempt.getResult())) {
                correct = false;
                continue;
            }
            if (!(attempt.getNumber() instanceof Integer)) {
                correct = false;
                continue;
            }

            if (answerEquals(attempt.getNumber(), item.getPrice().get(i))) {
                attempt.setResult("correct");
            } else {
                correct = false;
                attempt.setResult("incorrect");
                if (pointsAvailable > 0) {
                    pointsAvailable -= 5;
                }
            }
        }
        Score score = new Score();
        score.setAnswers(answers);
        score.setCorrect(correct);
        if (correct) {
            score.setPoints(pointsAvailable);
        } else {
            score.setPointsAvailable(pointsAvailable);
        }
        return score;
    }

    private static boolean answerEquals(Object num, Object priceIndex) {
        if (!(priceIndex instanceof Integer)) return false;
        int price = (Integer)priceIndex;
        int number = (Integer)num;
        return price == number;
    }
}