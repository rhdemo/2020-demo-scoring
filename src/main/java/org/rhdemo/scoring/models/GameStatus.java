package org.rhdemo.scoring.models;

public class GameStatus {
    private Game game;
    private Player player;
    private Score score;
    private CurrentRound currentRound;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public CurrentRound getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(CurrentRound currentRound) {
        this.currentRound = currentRound;
    }
}
