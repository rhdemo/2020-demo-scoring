package org.rhdemo.scoring.models;

import java.util.List;

public class Guess {
    private String game;
    private String player;
    private Item item;
    private List<Answer> answers;
    private int pointsAvailable;

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public int getPointsAvailable() {
        return pointsAvailable;
    }

    public void setPointsAvailable(int pointsAvailable) {
        this.pointsAvailable = pointsAvailable;
    }
}
