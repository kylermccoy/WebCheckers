package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.BoardView;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.halt;

/**
 * The {@code GET /game} route handler.
 *
 * @author <a href='mailto:bjm9265@rit.edu'>Brian Mirabito</a>
 */
public class GetGameRoute implements Route {

    private final GameCenter gameCenter;
    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;

    public static final String GAME_CENTER_KEY = "CURRENT_GAME_CENTER";
    public static final String GAME_TITLE = "Checkers";

    /***
     * Constructor.
     * @param templateEngine expected Template engine
     * @param gameCenter GameCenter object
     * @param playerLobby PlayerLobby (the web handler)
     */
    public GetGameRoute(TemplateEngine templateEngine, GameCenter gameCenter, PlayerLobby playerLobby ) {
        this.templateEngine = templateEngine;
        this.gameCenter = gameCenter;
        this.playerLobby = playerLobby;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {

        final Session httpSession = request.session();
        final Player player = httpSession.attribute(GetHomeRoute.CURRENT_USER_KEY);
        final Player opponent = this.playerLobby.getPlayer(request.queryParams("opponentName"));

        CheckersGame game = null;

        if(gameCenter.getCheckersGame(player) == null) { // Game has not been initiated or a user logged out
            if(opponent == null) {
                response.redirect(WebServer.HOME_URL);
                halt();
                return null;
            }
            this.gameCenter.addInGamePlayers(player, opponent);
            this.gameCenter.startGame(player, opponent);
            game = gameCenter.getCheckersGame(player);
        } else { // Game is initiated just retrieve for state
            game = gameCenter.getCheckersGame(player);
        }

        Map<String, Object> vm = new HashMap<>();
        if(game != null) { // Safety net
            vm.put("title", GAME_TITLE);
            vm.put("currentUser", player);
            vm.put("redPlayer", game.getRedPlayer());
            vm.put("whitePlayer", game.getWhitePlayer());
            vm.put("activeColor", game.getActiveColor());
            vm.put("board", game.getBoard());
            vm.put("invertedView", this.gameCenter.isPlayerViewInverted(player));
            vm.put("viewMode", "PLAY");
        }

        return templateEngine.render(new ModelAndView(vm , "game.ftl"));
    }
}
