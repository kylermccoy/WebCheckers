package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import spark.*;

import java.util.Objects;
import java.util.logging.Logger;

import static spark.Spark.halt;

/**
 * The UI controller to GET the Spectator Stop Watching Page
 *
 * @author Justin Yau @ RIT CS Student
 */
public class GetSpectatorStopWatchingRoute implements Route {

  private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());

  // WebServer provided information
  private final TemplateEngine templateEngine;
  private final GameCenter center;

  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /spectator/stopWatching} HTTP requests.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public GetSpectatorStopWatchingRoute(final TemplateEngine templateEngine, final GameCenter center) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    this.center = Objects.requireNonNull(center, "gameCenter is required");
    //
    LOG.config("GetSpectatorGameRoute is initialized.");
  }

  /**
   * Render the WebCheckers Spectate Stop Watch page.
   *
   * @param request
   *   the HTTP request
   * @param response
   *   the HTTP response
   *
   * @return
   *   the rendered HTML for the Spectate Stop Watch page
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

    CheckersGame spectating_game = this.center.getSpectatingGame(player);

    // Player is signed in and gameID matches the spectating game
    if(player != null && spectating_game != null && gameID == spectating_game.getGameID()) {
      this.center.stopSpectating(player);
    }

    response.redirect(WebServer.HOME_URL);
    halt();
    return null;
  }
}
