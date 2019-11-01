package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static spark.Spark.halt;

/**
 * The UI controller to GET the Spectator Game Page
 *
 * @author Justin Yau @ RIT CS Student
 */
public class GetSpectatorGameRoute implements Route {

  private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());

  // CONSTANT KEYS TO KEEP TRACK OF VIEW ATTRIBUTES AND IMPORTANT INFORMATION
  public static final String VIEW_NAME = "game.ftl";
  public static final String TITLE_ATTR = "title";
  public static final String MESSAGE_ATTR = "message";

  // WebServer provided information
  private final TemplateEngine templateEngine;
  private final GameCenter center;
  private final Gson gson;

  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /signin} HTTP requests.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public GetSpectatorGameRoute(final TemplateEngine templateEngine, final Gson gson, final GameCenter center) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    this.center = Objects.requireNonNull(center, "gameCenter is required");
    this.gson = Objects.requireNonNull(gson, "gson is required");
    //
    LOG.config("GetSpectatorGameRoute is initialized.");
  }

  /**
   * Render the WebCheckers Spectate Game page.
   *
   * @param request
   *   the HTTP request
   * @param response
   *   the HTTP response
   *
   * @return
   *   the rendered HTML for the Spectate Game page
   */
  @Override
  public Object handle(Request request, Response response) {
    LOG.fine("GetSpectatorGameRoute is invoked.");

    // retrieve the game object
    final Session session = request.session();
    // retrieve the current player
    final Player player = session.attribute(GetHomeRoute.CURRENT_USER_KEY);
    // retrieve the gameID and game
    final String extractedID = request.queryParams("gameID");
    final int gameID = (extractedID == null ? -1 : Integer.parseInt(extractedID));
    CheckersGame game = (gameID == -1 ? null : this.center.getGameByID(gameID));

    // Player is not signed in so go back to the homepage or the player is signed in but inputted the wrong gameID
    if((player != null && game == null && gameID == -1) || player == null) {
      response.redirect(WebServer.HOME_URL);
      halt();
      return null;
    }

    // Retrieve spectating game if already visited page
    CheckersGame spectating_game = this.center.getSpectatingGame(player);

    // We need to add user to spectate list. First time here
    if(spectating_game == null) {
      this.center.startSpectating(player, game);
    } else {
      game = spectating_game;
    }

    final Map<String, Object> modeOptions = new HashMap<>(2);
    modeOptions.put("isGameOver", false);
    modeOptions.put("gameOverMessage", "");

    if(game.isResigned()) {
      modeOptions.put("isGameOver", true);
      modeOptions.put("gameOverMessage", "A player has resigned!");
    } else if(game.isWon()) {
      modeOptions.put("isGameOver", true);
      if(game.getWinner() == player) {
        modeOptions.put("gameOverMessage", game.getWinner() + " won the game!");
      } else {
        modeOptions.put("gameOverMessage", game.getLoser() + " lost the game!");
      }
    }

    // Create the view map to insert objects into
    Map<String, Object> vm = new HashMap<>();

    vm.put(TITLE_ATTR, game.getRedPlayer() + " vs. " + game.getWhitePlayer());
    vm.put("currentUser", player);
    vm.put("redPlayer", game.getRedPlayer());
    vm.put("whitePlayer", game.getWhitePlayer());
    vm.put("activeColor", game.getActiveColor());
    vm.put("board", game.getBoard());
    vm.put("gameID", game.getGameID());
    vm.put("modeOptionsAsJSON", gson.toJson(modeOptions));
    vm.put("invertedView", false);
    vm.put("viewMode", "SPECTATOR");


    // render the View
    return templateEngine.render(new ModelAndView(vm , VIEW_NAME));
  }
}
