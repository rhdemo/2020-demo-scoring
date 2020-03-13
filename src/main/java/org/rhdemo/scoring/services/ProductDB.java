package org.rhdemo.scoring.services;

import org.rhdemo.scoring.models.Round;

import javax.enterprise.context.ApplicationScoped;
import java.util.LinkedList;
import java.util.List;

import static org.rhdemo.scoring.models.AnswerFormat.DECIMAL;
import static org.rhdemo.scoring.models.AnswerFormat.NUMBER;

@ApplicationScoped
public class ProductDB {

    private static List<Round> rounds = new LinkedList<>();

    static {
        Round round;

        round = new Round()
                .id(0)
                .name("Dollar bill")
                .description("One United States dollar and no cents")
                .price(1, ".", 0, 0)
                .choices(9, 1, 0, 5, 0, 1)
                .answers(NUMBER, DECIMAL, NUMBER, NUMBER)
                .image("/static/images/0.jpg");
        rounds.add(round);

        round = new Round()
                .id(1)
                .name("Kernel of truth t-shirt")
                .description("This 4.3 ounce, 60% combed ringspun cotton/40% polyester jersey t- shirt features a slightly heathered appearance. The fabric laundered for reduced shrinkage. Next Level brand apparel. Printed. Imported.\"")
                .price(8, ".", 5, 5)
                .choices(5, 4, 8, 5, 7, 8)
                .answers(NUMBER, DECIMAL, NUMBER, NUMBER)
                .image("/static/images/1.jpg");
        rounds.add(round);

        round = new Round()
                .id(2)
                .name("Speckled tumbler")
                .description("11 oz Travel tumbler, or drink insulator with double- wall construction, vacuum insulated. It comes with clear, press-on lid with a slide closure. Hand wash recommended. Imprinted. Imported.")
                .price(8, ".", 5, 8)
                .choices(9, 0, 5, 8, 3, 8)
                .answers(NUMBER, DECIMAL, NUMBER, NUMBER)
                .image("/static/images/2.jpg");
        rounds.add(round);

    }

    public List<Round> getRounds() {
        return rounds;
    }
}
