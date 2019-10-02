package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
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

    public GetGameRoute(TemplateEngine templateEngine, GameCenter gameCenter ) {
        this.templateEngine = templateEngine;
        this.gameCenter = gameCenter;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session httpSession = request.session();
        final Player player = httpSession.attribute(GetHomeRoute.CURRENT_USER_KEY);
        Map<String, Object> vm = new HashMap<>();

        return null;
    }
}
