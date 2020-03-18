package org.rhdemo.scoring.models;

import javax.enterprise.inject.Produces;
import java.time.OffsetDateTime;

/**
 * Game
 */
public class Game {
  private String id;
  private GameState state;
  private OffsetDateTime date;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public GameState getState() {
    return state;
  }

  public void setState(GameState state) {
    this.state = state;
  }

  public OffsetDateTime getDate() {
    return date;
  }

  public void setDate(OffsetDateTime date) {
    this.date = date;
  }

}
