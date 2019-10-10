package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;

/***
 * The {@code POST /game} route handler
 */
public class PostGameRoute implements Route {
    GameCenter gameCenter;
    PlayerLobby playerLobby;
    TemplateEngine templateEngine;

    /***
     * Constructor.
     * @param templateEngine TemplateEngine
     * @param playerLobby PlayerLobby
     * @param gameCenter GameCenter
     */
    public PostGameRoute(TemplateEngine templateEngine, PlayerLobby playerLobby, GameCenter gameCenter){
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;
        this.gameCenter = gameCenter;
    }
    @Override
    public Object handle(Request request, Response response) throws Exception {
        Map<String, Object> vm  = new HashMap<>();
        final Session session = request.session();
        Player p1 = session.attribute(GetHomeRoute.CURRENT_USER_KEY);
        String p2Name = request.queryParams("otherUser");

        // if player selected is in game check
        if(2+2 == 4) {
            return null;
        }

        Player p2 = playerLobby.getPlayer(p2Name);
        CheckersGame game = gameCenter.startGame(p1, p2);

        vm.put("title", GetGameRoute.GAME_TITLE);

        vm.put("currentUser", p1);
        vm.put("redPlayer", game.getRedPlayer());
        vm.put("whitePlayer", game.getWhitePlayer());
        vm.put("activeColor", game.getActiveColor());
        vm.put("viewMode", "PLAY");

        return templateEngine.render(new ModelAndView(vm , "game.ftl"));
    }
}
