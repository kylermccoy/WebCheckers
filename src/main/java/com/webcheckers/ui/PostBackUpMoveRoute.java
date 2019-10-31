package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Player;
import com.webcheckers.model.Turn;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Objects;
import java.util.logging.Logger;

public class PostBackUpMoveRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostBackUpMoveRoute.class.getName()) ;

    private final GameCenter gameCenter ;
    private final Gson gson ;

    public PostBackUpMoveRoute(final Gson gson, final GameCenter gameCenter) {
        Objects.requireNonNull(gameCenter) ;
        Objects.requireNonNull(gson) ;
        this.gson = gson ;
        this.gameCenter = gameCenter ;

        LOG.config("PostBackUpMoveRoute initialized");
    }

    @Override
    public Object handle(Request request, Response response){
        LOG.finer("PostBackUpMoveRoute invoked");
        final Player player = request.session().attribute(GetHomeRoute.CURRENT_USER_KEY);

        Turn turn = gameCenter.getPlayerTurn(player) ;

        if (turn.backUpMove()){
            return gson.toJson(Message.info("BackUp successful.")) ;
        }else{
            return gson.toJson(Message.error("BackUp failed.")) ;
        }
    }
}
