package org.rhdemo.scoring.models.leaderboard;

import org.rhdemo.scoring.models.Avatar;

/**
 * Player
 */
public class Player {

  public String id;
  public String username;
  public int right;
  public int wrong;
  public int score;
  public String creationServer;
  public String gameServer;
  public String scoringServer;
  public Avatar avatar;
  public String gameId;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
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

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
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

  public Avatar getAvatar() {
    return avatar;
  }

  public void setAvatar(Avatar avatar) {
    this.avatar = avatar;
  }

  public String getGameId() {
    return gameId;
  }

  public void setGameId(String gameId) {
    this.gameId = gameId;
  }
}
