package org.rhdemo.scoring.services;

import org.rhdemo.scoring.models.Round;

import javax.enterprise.context.ApplicationScoped;
import java.util.LinkedList;
import java.util.List;

import static org.rhdemo.scoring.models.AnswerFormat.DECIMAL;
import static org.rhdemo.scoring.models.AnswerFormat.NUMBER;

@ApplicationScoped
public class ProductDB {

    public static List<Round> rounds = new LinkedList<>();

    static {
        Round round;

        round = new Round()
                .id(0)
                .name("Dollar bill")
                .description("One United States dollar and no cents")
                .price(1, ".", 0, 0)
                .choices(9, 1, 0, 5, 0, 1)
                .answers(NUMBER, DECIMAL, NUMBER, NUMBER)
                .image("/static/images/0.png");
        rounds.add(round);

        round = new Round()
                .id(1)
                .name("Kernel of truth t-shirt")
                .description("This 4.3 ounce, 60% combed ringspun cotton/40% polyester jersey t- shirt features a slightly heathered appearance. The fabric laundered for reduced shrinkage. Next Level brand apparel. Printed. Imported.\"")
                .price(8, ".", 5, 5)
                .choices(5, 4, 8, 5, 7, 8)
                .answers(NUMBER, DECIMAL, NUMBER, NUMBER)
                .image("/static/images/1.png");
        rounds.add(round);

        round = new Round()
                .id(2)
                .name("Speckled tumbler")
                .description("11 oz Travel tumbler, or drink insulator with double- wall construction, vacuum insulated. It comes with clear, press-on lid with a slide closure. Hand wash recommended. Imprinted. Imported.")
                .price(8, ".", 5, 8)
                .choices(9, 0, 5, 8, 3, 8)
                .answers(NUMBER, DECIMAL, NUMBER, NUMBER)
                .image("/static/images/2.png");
        rounds.add(round);

        round = new Round()
                .id(3)
                .name("Patagonia Refugio pack 28L")
                .description("High quality backpack")
                .price(9,3, ".", 7, 5)
                .choices(0, 9, 5, 3, 7, 5)
                .answers(NUMBER, NUMBER, DECIMAL, NUMBER, NUMBER)
                .image("/static/images/3.png");
        rounds.add(round);

        round = new Round()
                .id(4)
                .name("Waist pack")
                .description("Its not a purse its a satchel")
                .price(2,9, ".", 0, 0)
                .choices(0, 9, 2, 3, 0, 9)
                .answers(NUMBER, NUMBER, DECIMAL, NUMBER, NUMBER)
                .image("/static/images/4.png");
        rounds.add(round);

        round = new Round()
                .id(5)
                .name("Beanie")
                .description("Keep that brain warm")
                .price(8, ".", 9, 5)
                .choices(0, 6, 8, 5, 0, 9)
                .answers(NUMBER, DECIMAL, NUMBER, NUMBER)
                .image("/static/images/5.png");
        rounds.add(round);

        round = new Round()
                .id(6)
                .name("Baseball cap")
                .description("Black hat")
                .price(1, 3, ".", 7, 5)
                .choices(0, 1, 8, 5, 3, 7)
                .answers(NUMBER, NUMBER, DECIMAL, NUMBER, NUMBER)
                .image("/static/images/6.png");
        rounds.add(round);

        round = new Round()
                .id(7)
                .name("Hoodie")
                .description("Unisex sueded full zip hoodie")
                .price(3, 0, ".", 2, 5)
                .choices(0, 1, 4, 5, 3, 2)
                .answers(NUMBER, NUMBER, DECIMAL, NUMBER, NUMBER)
                .image("/static/images/7.png");
        rounds.add(round);

        round = new Round()
                .id(8)
                .name("Vest")
                .description("Puffer Vest")
                .price(4, 9, ".", 9, 5)
                .choices(0, 9, 4, 5, 9, 0)
                .answers(NUMBER, NUMBER, DECIMAL, NUMBER, NUMBER)
                .image("/static/images/8.png");
        rounds.add(round);

        round = new Round()
                .id(9)
                .name("Grey Polo")
                .description("Digi heather polo")
                .price(2, 0, ".", 8, 0)
                .choices(0, 9, 3, 0, 2, 8)
                .answers(NUMBER, NUMBER, DECIMAL, NUMBER, NUMBER)
                .image("/static/images/9.png");
        rounds.add(round);

        round = new Round()
                .id(10)
                .name("Longsleeve Shirt")
                .description("Calvin Klein cotton shirt")
                .price(4, 7, ".", 8, 5)
                .choices(7, 8, 3, 4, 5, 8)
                .answers(NUMBER, NUMBER, DECIMAL, NUMBER, NUMBER)
                .image("/static/images/10.png");
        rounds.add(round);

        round = new Round()
                .id(11)
                .name("Sunglasses")
                .description("You are so cool")
                .price(3, ".", 1, 0)
                .choices(0, 5, 3, 4, 0, 1)
                .answers(NUMBER, DECIMAL, NUMBER, NUMBER)
                .image("/static/images/11.png");
        rounds.add(round);

        round = new Round()
                .id(12)
                .name("Socks")
                .description("Red Socks")
                .price(4, ".", 1, 5)
                .choices(0, 5, 3, 4, 0, 1)
                .answers(NUMBER, DECIMAL, NUMBER, NUMBER)
                .image("/static/images/12.png");
        rounds.add(round);

        round = new Round()
                .id(13)
                .name("Umbrella")
                .description("46\" vented auto open folding umbrella")
                .price(1, 3, ".", 3, 0)
                .choices(0, 5, 3, 3, 0, 1)
                .answers(NUMBER, NUMBER, DECIMAL, NUMBER, NUMBER)
                .image("/static/images/13.png");
        rounds.add(round);

        round = new Round()
                .id(14)
                .name("Flask")
                .description("Stainless steel tumbler")
                .price(1, 3, ".", 0, 0)
                .choices(0, 9, 3, 7, 0, 1)
                .answers(NUMBER, NUMBER, DECIMAL, NUMBER, NUMBER)
                .image("/static/images/14.png");
        rounds.add(round);

        round = new Round()
                .id(15)
                .name("Scrunchie")
                .description("Stainless steel tumbler")
                .price(3, ".", 8, 0)
                .choices(0, 8, 3, 2, 0, 1)
                .answers(NUMBER, DECIMAL, NUMBER, NUMBER)
                .image("/static/images/15.png");
        rounds.add(round);

        round = new Round()
                .id(16)
                .name("Clip")
                .description("Lanyard- single clip")
                .price(1, ".", 6, 0)
                .choices(0, 6, 9, 9, 0, 1)
                .answers(NUMBER, DECIMAL, NUMBER, NUMBER)
                .image("/static/images/16.png");
        rounds.add(round);

        round = new Round()
                .id(17)
                .name("Stickers")
                .description("Stickers")
                .price(3, ".", 0, 0)
                .choices(0, 3, 9, 9, 0, 1)
                .answers(NUMBER, DECIMAL, NUMBER, NUMBER)
                .image("/static/images/17.png");
        rounds.add(round);

        round = new Round()
                .id(18)
                .name("4 Pens")
                .description("4 pk jotter pens")
                .price(3, ".", 4, 0)
                .choices(3, 3, 9, 4, 0, 9)
                .answers(NUMBER, DECIMAL, NUMBER, NUMBER)
                .image("/static/images/18.png");
        rounds.add(round);

        round = new Round()
                .id(19)
                .name("Lapel Pin")
                .description("Lapel pin")
                .price(1, ".", 3, 5)
                .choices(1, 3, 9, 5, 0, 9)
                .answers(NUMBER, DECIMAL, NUMBER, NUMBER)
                .image("/static/images/19.png");
        rounds.add(round);

    }

    public List<Round> getRounds() {
        return rounds;
    }
}
