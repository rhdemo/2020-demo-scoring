package org.rhdemo.scoring.models;

import java.util.LinkedList;
import java.util.List;

public class CurrentRound extends Round {
    private int pointsAvailable;
    private List<Object> guess;

    public CurrentRound() {
    }

    public CurrentRound(Round round) {
        super(round);
        setPrice(null);
    }

    public CurrentRound(CurrentRound current) {
        this.pointsAvailable = current.pointsAvailable;
        this.guess = new LinkedList<>(current.guess);
    }

    public int getPointsAvailable() {
        return pointsAvailable;
    }

    public void setPointsAvailable(int pointsAvailable) {
        this.pointsAvailable = pointsAvailable;
    }

    public List<Object> getGuess() {
        return guess;
    }

    public void setGuess(List<Object> guess) {
        this.guess = guess;
    }
}
