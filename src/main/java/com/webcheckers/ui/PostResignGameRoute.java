package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.Objects;
import java.util.logging.Logger;

/**
 * The UI controller to POST the resignGame Page
 *
 * @author Justin Yau @ RIT CS Student
 */
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
     * @param center
     *    the GameCenter which contains all the game information and active players
     * @param gson
     *    the Gson object to convert objects into JSON
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

    /**
     * Updates the WebCheckers Game page.
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   AJAX Response with message Body containing a string of whether the player successfully resigned or not
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.fine("PostResignGameRoute is invoked.");

        // retrieve the game object
        final Session session = request.session();
        final Player player = session.attribute(GetHomeRoute.CURRENT_USER_KEY);

        if(player != null && this.center.isPlayerInGame(player)) {
            CheckersGame game = this.center.getCheckersGame(player);
            this.center.playerLeftGame(player);
            game.playerResigned();
            session.attribute(GetGameRoute.CURRENT_OPPONENT_KEY, null);
            return gson.toJson(Message.info("true"));
        }
        return gson.toJson(Message.error("false"));
    }

}