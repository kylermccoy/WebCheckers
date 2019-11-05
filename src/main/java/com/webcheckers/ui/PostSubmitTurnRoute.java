package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

public class PostSubmitTurnRoute implements Route {

    // Set messages to reference when contacting the server
    public static final Message NO_TURN = Message.error("Not players turn");

    // Used to handle the responses
    private Gson gson;

    private GameCenter gameCenter;

    /**
     * Constructor for the submit turn route
     * @param gson used for handling user responses
     * @param gameCenter used for game-play purposes
     */
    public PostSubmitTurnRoute(Gson gson, GameCenter gameCenter){
        this.gson = gson;
        this.gameCenter = gameCenter;
    }


    /**
     * Handle the ability to submit a turn or not using responses given by Message objects.
     * @param request the player info
     * @param response response given to user
     * @return info body or error message depending on case of turn
     */
    @Override
    public Object handle(Request request, Response response) {
        Session session = request.session();
        Player player = session.attribute(GetHomeRoute.CURRENT_USER_KEY);
        CheckersGame game = gameCenter.getCheckersGame(player);

        if(game.getActiveColor() != game.getPlayerColor(player)) {
            return gson.toJson(NO_TURN);
        }
        return gson.toJson(game.submitTurn(player)) ;
    }
}
