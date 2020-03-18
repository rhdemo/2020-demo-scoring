package org.rhdemo.scoring.models.leaderboard;

/**
 * Transaction
 */
public class Transaction {

    private int points;
    private boolean correct;

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }
}