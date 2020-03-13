package org.rhdemo.scoring;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.rhdemo.scoring.models.AnswerFormat;
import org.rhdemo.scoring.models.Round;
import org.rhdemo.scoring.services.ProductDB;

public class ProductTest {
    @Test
    public void testFormatPrice() {
        for (Round round : ProductDB.rounds) {
            Assertions.assertEquals(round.getPrice().size(), round.getAnswers().size());
            for (int i = 0; i < round.getPrice().size(); i++) {
                if (round.getAnswers().get(i).equals(AnswerFormat.NUMBER)) {
                    Assertions.assertTrue(round.getPrice().get(i) instanceof Integer);
                } else {
                    Assertions.assertTrue(round.getPrice().get(i).equals("."));
                }
            }
        }
    }
}
