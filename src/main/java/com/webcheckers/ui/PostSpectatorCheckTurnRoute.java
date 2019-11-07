package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.Objects;
import java.util.logging.Logger;

import static spark.Spark.halt;

/**
 * The UI controller to POST the Spectator Check Turn Page
 *
 * @author Justin Yau @ RIT CS Student
 */
public class PostSpectatorCheckTurnRoute implements Route {

  private static final Logger LOG = Logger.getLogger(PostSpectatorCheckTurnRoute.class.getName());

  // WebServer provided information
  private final TemplateEngine templateEngine;
  private final GameCenter center;
  private final Gson gson;

  /**
   * Create the Spark Route (UI controller) to handle all {@code POST /spectator/checkTurn} HTTP requests.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public PostSpectatorCheckTurnRoute(final TemplateEngine templateEngine, final Gson gson, final GameCenter center) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    this.center = Objects.requireNonNull(center, "gameCenter is required");
    this.gson = Objects.requireNonNull(gson, "gson is required");
    //
    LOG.config("GetSpectatorGameRoute is initialized.");
  }

  /**
   * Render the WebCheckers Spectator Check Turn page.
   *
   * @param request
   *   the HTTP request
   * @param response
   *   the HTTP response
   *
   * @return
   *   the rendered HTML for the Spectator Check Turn page
   */
  @Override
  public Object handle(Request request, Response response) {
    LOG.fine("PostSpectatorCheckTurnRoute is invoked.");

    // retrieve the game object
    final Session session = request.session();
    // retrieve the current player
    final Player player = session.attribute(GetHomeRoute.CURRENT_USER_KEY);
    final CheckersGame.color color = session.attribute(GetSpectatorGameRoute.LAST_COLOR_KEY);

    // Player is signed in
    if(player != null) {
      CheckersGame spectating_game = this.center.getSpectatingGame(player);
      if(spectating_game != null) {
        if(color != null && spectating_game.isResigned() || spectating_game.isWon()) {
          return gson.toJson(Message.info("true"));
        } else if(color != null && color != spectating_game.getActiveColor()) {
          return gson.toJson(Message.info("true"));
        }
      }
    }

    return gson.toJson(Message.info("false"));
  }
}
