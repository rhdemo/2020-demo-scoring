package org.rhdemo.scoring.models;

public class Score {
    public static final String START="START";
    public static final String CORRECT_GUESS="CORRECT_GUESS";
    public static final String BAD_GUESS="BAD_GUESS";
    public static final String RESET="RESET";
    public static final String COMPLETED_ROUND="COMPLETED_ROUND";
    public static final String GAME_OVER="GAME_OVER";

    private String scoringServer;
    private String status;
    private int score;
    private int right;
    private int wrong;
    private int award;

    public String getScoringServer() {
        return scoringServer;
    }

    public void setScoringServer(String scoringServer) {
        this.scoringServer = scoringServer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public int getAward() {
        return award;
    }

    public void setAward(int award) {
        this.award = award;
    }
}
