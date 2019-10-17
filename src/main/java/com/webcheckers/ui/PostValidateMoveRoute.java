package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Player;
import spark.*;

import java.util.Objects;
import java.util.logging.Logger;

/**
 * my continuing failure of an attempt to implement routes for
 * move validation
 */
public class PostValidateMoveRoute implements Route {

    private static final Logger LOG = Logger.getLogger(PostValidateMoveRoute.class.getName()) ;

    private final String NO_POSITION_PROVIDED_MSG = "No position was provided for validation." ;
    private final String INVALID_MOVE_MSG = "The move requested is not valid!" ;

    private final Gson gson ;
    private final GameCenter gameCenter ;

    public PostValidateMoveRoute(final Gson gson, final GameCenter gameCenter){
        Objects.requireNonNull(gson, "gson must not be null") ;
        Objects.requireNonNull(gameCenter, "gameCenter must not be null") ;

        this.gson = gson ;
        this.gameCenter = gameCenter ;

        LOG.config("PostValidateMoveRoute initialized");
    }

    @Override
    public Object handle(Request request, Response response){
        LOG.finer("PostValidateMoveRoute invoked");

        try {
            if(request.body().contains("null")){
                throw new Error("The move is invalid") ;
            }
            Player player = request.session().attribute(GetHomeRoute.CURRENT_USER_ATTR);

        }catch(Error e){
            e.printStackTrace();
        }
        return null ;
    }
}
