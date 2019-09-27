package com.webcheckers.model;

/**
 * This is the model for a single player entity.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 * @author <a href='mailto:jrv@se.rit.edu'>Jim Vallino</a>
 */
public class Player {

  // Field where the player username will be stored
  private String username;

  /**
   * Create a new player entity with a reserved username
   *
   * @param username
   *          Username for the player entity
   */
  public Player(String username) {
    this.username = username;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public synchronized String toString() {
    return this.username;
  }

}
