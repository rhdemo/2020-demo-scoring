package org.rhdemo.scoring.models;

import javax.enterprise.inject.Produces;

/**
 * Avatar
 */
public class Avatar {
  public int body;
  public int eyes;
  public int ears;
  public int mouth;
  public int nose;
  public int color;

  public Avatar() {}

  @Produces
  public static Avatar newAvatar() {
    return new Avatar();
  }

  public int getBody() {
    return body;
  }

  public Avatar body(int body) {
    this.body = body;
    return this;
  }

  public int getEyes() {
    return eyes;
  }

  public Avatar eyes(int eyes) {
    this.eyes = eyes;
    return this;
  }

  public int getEars() {
    return ears;
  }

  public Avatar ears(int ears) {
    this.ears = ears;
    return this;
  }

  public int getMouth() {
    return mouth;
  }

  public Avatar mouth(int mouth) {
    this.mouth = mouth;
    return this;
  }

  public int getNose() {
    return nose;
  }

  public Avatar nose(int nose) {
    this.nose = nose;
    return this;
  }

  public int getColor() {
    return color;
  }

  public Avatar color(int color) {
    this.color = color;
    return this;
  }

}
