package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestPostBackUpMoveRoute {

    // Back up move object
    private PostBackUpMoveRoute backUpMove;

    // Mocked Objects
    private Session session;
    private Request request;
    private GameCenter center;
    private Gson gson;
    private CheckersGame game;

    @BeforeEach
    public void initializeBackUpMove(){
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        gson = new Gson();
        center = new GameCenter();
        game = mock(CheckersGame.class);

        backUpMove = new PostBackUpMoveRoute(gson, center);
    }

    @Test
    public void test_backUpSuccessful(){
        Player player = new Player("Dan");
        Player player2 = new Player("Bob");
        game = center.startGame(player, player2);
        when(session.attribute(GetHomeRoute.CURRENT_USER_KEY)).thenReturn(player);
        Turn turn = center.getPlayerTurn(player);
        Move move = new Move(new Position(5, 2), new Position(4, 3));
        turn.validateMove(move);
        final Response response = mock(Response.class);
        String actualValue = backUpMove.handle(request, response).toString();
        assertEquals("{\"text\":\"BackUp successful.\",\"type\":\"INFO\"}", actualValue);
    }

    @Test
    public void test_backUpUnSuccessful(){
        Player player = new Player("Dan");
        Player player2 = new Player("Bob");
        game = center.startGame(player, player2);
        when(session.attribute(GetHomeRoute.CURRENT_USER_KEY)).thenReturn(player);
        final Response response = mock(Response.class);
        String actualValue = backUpMove.handle(request, response).toString();
        assertEquals("{\"text\":\"BackUp failed.\",\"type\":\"ERROR\"}", actualValue);
    }
}
