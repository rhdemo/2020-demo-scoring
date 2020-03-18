package org.rhdemo.scoring.services;

import org.rhdemo.scoring.models.Game;
import org.rhdemo.scoring.models.Round;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class GamingService {

    @Inject
    ProductDB products;

    public Round firstRound(Game game) {
        return products.getRounds().get(0);

    }

    public Round nextRound(Game game, int currentRound) {
        if (currentRound + 1 >= products.getRounds().size()) {
            // loop;
            return products.getRounds().get(0);

        } else {
            return products.getRounds().get(currentRound + 1);
        }
    }

    public Round getRound(Game game, int round) {
        return products.getRounds().get(round);
    }
}
