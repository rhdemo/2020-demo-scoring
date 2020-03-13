package org.rhdemo.scoring.services;

import org.rhdemo.scoring.models.Game;
import org.rhdemo.scoring.models.Player;
import org.rhdemo.scoring.models.Round;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Date;

@ApplicationScoped
public class GamingService {

    @Inject
    ProductDB products;

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
        return products.getRounds().get(0);

    }

    public Round nextRound(Game game, int currentRound) {
        return products.getRounds().get(currentRound + 1);
    }

    public Round getRound(Game game, int round) {
        return products.getRounds().get(round);
    }
}
