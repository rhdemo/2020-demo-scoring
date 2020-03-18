package org.rhdemo.scoring.models.leaderboard;

import org.rhdemo.scoring.models.Game;

/**
 * GameMessage
 */
public class GameMessage {

  private Player player;
  private Game game;
  private Transaction transaction;

  public Player getPlayer() {
    return player;
  }

  public Transaction getTransaction() {
    return transaction;
  }

  public void setTransaction(Transaction transaction) {
    this.transaction = transaction;
  }

  public void setPlayer(Player player) {
    this.player = player;
  }

  public Game getGame() {
    return game;
  }

  public void setGame(Game game) {
    this.game = game;
  }

}
