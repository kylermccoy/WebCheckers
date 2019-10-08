package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static spark.Spark.halt;

/**
 * The UI controller to POST the SIGN-OUT Page
 *
 * @author Justin Yau
 */
public class PostSignOutRoute implements Route {

  private static final Logger LOG = Logger.getLogger(PostSignOutRoute.class.getName());

  // Webserver provided information
  private final TemplateEngine templateEngine;
  private final PlayerLobby lobby;
  private final GameCenter center;

  /**
   * Create the Spark Route (UI controller) to handle all {@code POST /signin} HTTP requests.
   *
   * @param lobby
   *   the PlayerLobby which contains a list of all players
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public PostSignOutRoute(final PlayerLobby lobby, final GameCenter center, final TemplateEngine templateEngine) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    this.center = center;
    this.lobby = lobby;
    //
    LOG.config("PostSignInRoute is initialized.");
  }

  /**
   * Handles the WebCheckers sign-out requests.
   *
   * @param request
   *   the HTTP request
   * @param response
   *   the HTTP response
   *
   * @return
   *   the rendered HTML for the Sign-Out page
   */
  @Override
  public Object handle(Request request, Response response) {
    LOG.fine("PostSignOutRoute is invoked.");

    // retrieve the game object
    final Session session = request.session();
    final Player player = session.attribute(GetHomeRoute.CURRENT_USER_KEY);

    // Create the view map to insert objects into
    Map<String, Object> vm = new HashMap<>();

    // if user is not logged-in, redirect to home page. There is no sign-out action to do
    if(player == null) {
      response.redirect(WebServer.HOME_URL);
      halt();
      return null;
    } else {
      lobby.playerLoggedOut(player);
      if(this.center.getCheckersGame(player) != null) { // Remove from active games
          this.center.playerLeftGame(player);
      }
      session.attribute(GetHomeRoute.CURRENT_USER_KEY, null);
      response.redirect(WebServer.HOME_URL);
      halt();
      return null;
    }
  }
}
