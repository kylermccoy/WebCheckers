package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.util.Message;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import com.webcheckers.model.Turn;
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

        final Session httpSession = request.session();
        LOG.finer("PostValidateMoveRoute invoked");

        try {
            String positionAsJSON = request.queryParams("actionData");
            Move requestedMove = gson.fromJson(positionAsJSON, Move.class) ;


            if(positionAsJSON.contains("null")){
                throw new Error("The move is invalid") ;
            }
            final Player player = httpSession.attribute(GetHomeRoute.CURRENT_USER_KEY);
            Turn turn = gameCenter.getPlayerTurn(player) ;
            LOG.finest(String.format("JSON body: [%s]", positionAsJSON));

            if (positionAsJSON.isEmpty()) {
                return formatMessageJson(NO_POSITION_PROVIDED_MSG) ;
            }

            return gson.toJson(turn.validateMove(requestedMove)) ;

        }catch(Error e){
            LOG.warning(e.getMessage());
            return formatMessageJson("You can't move to a space occupied by a piece!") ;
        }
    }

    public Object formatMessageJson(String messageText){
        return gson.toJson(Message.error(messageText)) ;
    }
}
