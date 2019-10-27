package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Message;
import com.webcheckers.model.Player;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Objects;
import java.util.logging.Logger;

public class PostCheckTurnRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostCheckTurnRoute.class.getName());
    private final Gson gson ;
    private final GameCenter gameCenter ;

    private final String opponentResigned = "true/"/*"Your opponent has resigned from the game"*/ ;
    private final String thisPlayersTurn = "true" ;
    private final String otherPlayersTurn = "false" ;
    private final String GAME_ENDED_STRING = "The game has ended!" ;

    public PostCheckTurnRoute(GameCenter gameCenter, Gson gson) {
        Objects.requireNonNull(gameCenter,"GameCenter must not be null") ;
        Objects.requireNonNull(gson, "gson must not be null") ;

        this.gameCenter = gameCenter ;
        this.gson = gson ;

        LOG.config("PostCheckTurnRoute is initialized");
    }

    @Override
    public Object handle(Request request, Response response){
        LOG.finer("PostCheckTurnRoute is invoked.");
        Player currentPlayer = request.session().attribute("Player");

        CheckersGame game = gameCenter.getCheckersGame(currentPlayer) ;

        if (game==null){
            return formatMessageJson(GAME_ENDED_STRING) ;
        }

        if (game.isResigned()) {
            request.session().attribute("message", new Message(String.format("%s has resigned, %s has won the game!", game.getLoser().getName(), game.getWinner().getName()), Message.MessageType.info)) ;
            return formatMessageJson(opponentResigned) ;
        }else if (game.isWon()){
            return new Message(String.format("Game won by %s", game.getWinner().getName()), Message.MessageType.error).toJson() ;
        }else if (currentPlayer.equals(game.getPlayerActive())) {
            return formatMessageJson(thisPlayersTurn) ;
        }else {
            return formatMessageJson(otherPlayersTurn) ;
        }
    }

    public Object formatMessageJson(String messageText){
        return gson.toJson(new Message(messageText, Message.MessageType.info)) ;
    }
}
