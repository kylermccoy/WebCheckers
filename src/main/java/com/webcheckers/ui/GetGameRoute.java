package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.BoardView;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;

/**
 * The {@code GET /game} route handler.
 *
 * @author <a href='mailto:bjm9265@rit.edu'>Brian Mirabito</a>
 */
public class GetGameRoute implements Route {

    private final GameCenter gameCenter;
    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;

    public static final String GAME_TITLE = "Checkers";

    public GetGameRoute(TemplateEngine templateEngine, GameCenter gameCenter, PlayerLobby playerLobby ) {
        this.templateEngine = templateEngine;
        this.gameCenter = gameCenter;
        this.playerLobby = playerLobby;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {

        final Session httpSession = request.session();
        final Player player = httpSession.attribute(GetHomeRoute.CURRENT_USER_KEY);

        CheckersGame game = gameCenter.getCheckersGame(player);

        Map<String, Object> vm = new HashMap<>();

        vm.put("title", GAME_TITLE);
        vm.put("currentUser", player);
        vm.put("redPlayer", game.getRedPlayer());
        vm.put("whitePlayer", game.getWhitePlayer());
        vm.put("activeColor", game.getActiveColor());
        vm.put("viewMode", "PLAY");


        return templateEngine.render(new ModelAndView(vm , "game.ftl"));
    }
}
