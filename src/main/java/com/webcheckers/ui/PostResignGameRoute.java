package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.Objects;
import java.util.logging.Logger;

import static spark.Spark.halt;

public class PostResignGameRoute implements Route {

  private static final Logger LOG = Logger.getLogger(PostResignGameRoute.class.getName());

  // Webserver provided information
  private final TemplateEngine templateEngine;
  private final PlayerLobby lobby;
  private final GameCenter center;
  private final Gson gson;

  /**
   * Create the Spark Route (UI controller) to handle all {@code POST /signin} HTTP requests.
   *
   * @param lobby
   *   the PlayerLobby which contains a list of all players
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public PostResignGameRoute(final PlayerLobby lobby, final GameCenter center, final Gson gson, final TemplateEngine templateEngine) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    this.lobby = Objects.requireNonNull(lobby, "playerLobby is required");
    this.center = Objects.requireNonNull(center, "gameCenter is required");
    this.gson = Objects.requireNonNull(gson, "gson is required");
    //
    LOG.config("PostResignGameRoute is initialized.");
  }

  @Override
  public Object handle(Request request, Response response) {
    LOG.fine("PostResignGameRoute is invoked.");

    // retrieve the game object
    final Session session = request.session();
    final Player player = session.attribute(GetHomeRoute.CURRENT_USER_KEY);

    if(player != null && this.center.isPlayerInGame(player)) {
      this.center.playerLeftGame(player);
      return gson.toJson(Message.info("true"));
    }
    return gson.toJson(Message.error("false"));
  }

}
