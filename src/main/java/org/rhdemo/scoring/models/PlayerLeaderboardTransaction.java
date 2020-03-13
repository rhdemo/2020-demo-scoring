package org.rhdemo.scoring.models;

public class PlayerLeaderboardTransaction extends Player {
    public PlayerLeaderboardTransaction(Player copy) {
        super(copy);
    }

    private String gameId;
    private int score;
    private int right;
    private int wrong;
    private String creationServer;
    private String gameServer;
    private String scoringServer;

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
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
}
