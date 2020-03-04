package org.rhdemo.scoring.models;

public class GameState {
    private String creationServer;
    private String gameServer;
    private String scoringServer;
    private Game game;
    private Player player;
    private int score;
    private int right;
    private int wrong;
    private CurrentRound currentRound;

    public String getCreationServer() {
        return creationServer;
    }

    public void setCreationServer(String creationServer) {
        this.creationServer = creationServer;
    }

    public String getGameServer() {
        return gameServer;
    }

    public void setGameServer(String gameServer) {
        this.gameServer = gameServer;
    }

    public String getScoringServer() {
        return scoringServer;
    }

    public void setScoringServer(String scoringServer) {
        this.scoringServer = scoringServer;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getWrong() {
        return wrong;
    }

    public void setWrong(int wrong) {
        this.wrong = wrong;
    }

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

    public CurrentRound getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(CurrentRound currentRound) {
        this.currentRound = currentRound;
    }
}
