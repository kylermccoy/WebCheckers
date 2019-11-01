package com.webcheckers.appl;

import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * The object of this class is to coordinate the state of the Web Application and keep track of active players.
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
  private GameCenter center; // The gamecenter that keeps track the game state of players

  //
  // Constructors
  //
  public PlayerLobby(GameCenter center) {
    players = new HashMap<String, Player>();
    this.center = center;
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
    if(this.players.containsKey(username) || username.length() < 1 || !username.matches("[a-zA-Z0-9]+")) {
      return null;
    } else {
      Player p = new Player(username);
      players.put(username, p);
      return p;
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
    int count = 0;
    for(String entry : this.players.keySet()) {
      Player play = this.players.get(entry);
      CheckersGame game = center.getCheckersGame(play);
      CheckersGame spectatingGame = center.getSpectatingGame(play);
      if(play != p && (game == null || game.isResigned()) && spectatingGame == null) { // Player is not the current player and not in-game and not spectating
        lobbyList += "<li class='player-item'> <a class='player' href='/game?opponentName=" +
                      play.getName() + "'>" + entry + " </a></li>";
        count++;
      } else if(game != null && !game.isResigned()) { // Active game to spectate here
        int gameID = game.getGameID();
        lobbyList += "<li class='player-item'> <a class='player' href='/spectate/game?gameID=" + gameID + "'> Spectate " + play.getName()+ "</a> </li>";
        count++;
      }
    }
    if(count == 0) {
      return "<li class='player-item'> There are no other players available to play at this time </li>";
    }
    return lobbyList;
  }

  /**
   * Method for getting a player instance from string value
   * @param name username of player
   * @return player instance
   */
  public Player getPlayer(String name){
    if(name == null || name.length() == 0) {
      return null;
    }
    return players.get(name);
  }

}
