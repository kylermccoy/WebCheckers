package com.webcheckers.appl;

import com.webcheckers.model.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * The object of is to coordinate the state of the Web Application and keep track of active playerse.
 *
 *
 * @author Justin Yau @ RIT CS STUDENT
 */
public class PlayerLobby {

  //
  // Constants
  //

  private static final Logger LOG = Logger.getLogger(PlayerLobby.class.getName());


  //
  // Attributes
  //

  private Map<String, Player> players; // This keeps a list of all the players in the lobby

  //
  // Constructors
  //
  public PlayerLobby() {
    players = new HashMap<String, Player>();
  }

  //
  // Public methods
  //

  /**
   * Handles requests to sign-in using a username and determines if the sign in is to be authenticated
   *
   * @param username
   *             The username of the new player sign-in request
   * @return
   *      The player instance if the request was successful
   */
  public Player newPlayerInstance(String username) {
    if(this.players.containsKey(username) || username.length() < 1 || username.isBlank()) {
      return null;
    } else {
      Player p = new Player(username);
      players.put(username, p);
      return p;
    }
  }

  /**
   * Handles requests to query the current game state of a player
   * @param username - The username of the player that you want to query
   * @return - Whether or not the player is in-game or not
   */
  public boolean isPlayerInGame(String username) {
    if(this.players.containsKey(username)){
      return this.players.get(username).isInGame();
    }
    return true;
  }

  /**
   * Handles a change in the current game state of the player
   * @param username - The username of the player whose state has changed
   * @param state - The new game state of the player
   */
  public void togglePlayerInGame(String username, boolean state) {
    if(this.players.containsKey(username)) {
      this.players.get(username).toggleGame(state);
    }
  }

  /**
   * Routine called when the player signs out of their session
   *
   * @param p
   *          The player whose session ended.
   */
  public void playerLoggedOut(Player p) {
    this.players.remove(p.getName());
  }

  /**
   * Routine used to determine the current number of signed-in players
   *
   * CONDITIONS:
   *    User is not logged in
   *
   * @param p
   *        The currentUser object for the session
   *
   * @return
   *      The current number of signed-in users excluding the currentPlayer if they're signed in
   */
  public String lobbySize(Player p) {
    String ret = "<li class='player-item'> There are currently ";
    if(p == null) {
      ret += this.players.size(); // User is not logged in so we can display the full number of players
    } else {
      ret += (this.players.size() - 1); // User is logged in so we need to subtract one
    }
    return (ret + " players signed in and ready to play! </li>");
  }

  /**
   * Use the PlayerLobby Global Instance to display the current list of players
   *
   * @return
   *    The full list of all signed-in players excluding the currentUser
   */
  public String giveRoster(Player p) {
    if(this.players.size() <= 1) {
      return "<li class='player-item'> There are no other players available to play at this time </li>";
    }
    String lobbyList = "";
    for(String entry : this.players.keySet()) {
      if(this.players.get(entry) != p) {
        lobbyList += "<li class='player-item'> <button class='player'>" + entry + " </button> </li>";
      }
    }
    return lobbyList;
  }

}
