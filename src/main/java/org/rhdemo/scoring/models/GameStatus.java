package org.rhdemo.scoring.models;

import java.util.List;

/**
 {
 "creationServer": "SFO",
 "gameServer": "SFO",
 "scoringServer": "NY",
 "status": "CORRECT_GUESS",
 "score": 0,
 "award": 0,
 "right": 1,
 "wrong": 0,
 "game": {
 "id": "new-game-1583157438",
 "state": "active",
 "date": "2020-03-02T13:57:18.000Z",
 "configuration": {}
 },
 "player": {
 "id": "Emerald Wanderer",
 "username": "Emerald Wanderer",
 "avatar": {
 "body": 1,
 "eyes": 3,
 "mouth": 0,
 "ears": 2,
 "nose": 1,
 "color": 3
 }
 },
 "currentRound": {
 "id": 0,
 "version": "1",
 "pointsAvailable": 100,
 "name": "Dollar bill",
 "description": "One United States dollar and no cents",
 "guess": [1, "."],
 "choices": [9, 0, 5, 0, 1],
 "answers": [{"format": "number"}, {"format": "decimal"}, {"format": "number"}, {"format": "number"}],
 "image": "/static/images/0.jpg"
 }
 }
 */
public class GameStatus extends GameState {
    public static final String CORRECT_GUESS="CORRECT_GUESS";
    public static final String BAD_GUESS="BAD_GUESS";
    public static final String RESET="RESET";
    public static final String COMPLETED_ROUND="COMPLETED_ROUND";
    public static final String GAME_OVER="GAME_OVER";

    private int award;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getAward() {
        return award;
    }

    public void setAward(int award) {
        this.award = award;
    }

}
