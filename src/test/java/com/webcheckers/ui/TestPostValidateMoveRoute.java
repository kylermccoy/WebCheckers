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

public class TestPostValidateMoveRoute {

    // Validate Move object
    private PostValidateMoveRoute validateMove;

    // Mocked Objects
    private Session session;
    private Request request;
    private Move move;
    private CheckersGame game;
    private TemplateEngine engine;
    private PlayerLobby playerLobby;
    private GameCenter center;
    private Gson gson;
    private Player player;
    private MoveValidator validator;

    @BeforeEach
    public void initializeValidateMove(){
        request = mock(Request.class);
        session = mock(Session.class);
        move = mock(Move.class);
        game = mock(CheckersGame.class);
        when(request.session()).thenReturn(session);
        engine = mock(TemplateEngine.class);
        gson = new Gson();
        center = new GameCenter();
        playerLobby = new PlayerLobby(center);
        player = new Player("Dan");
        validator = mock(MoveValidator.class);

        validateMove = new PostValidateMoveRoute(gson, center);
    }

    @Test
    public void test_validate(){
        Player d = new Player("Dan");
        Player player2 = new Player("Bob");
        Response response = mock(Response.class);
        game = center.startGame(d, player2);
        when(request.session().attribute(GetHomeRoute.CURRENT_USER_KEY)).thenReturn(d);
        Turn turn = center.getPlayerTurn(d);
        Move move = new Move(new Position(5, 2), new Position(4, 3));
        when(request.queryParams("actionData")).thenReturn(gson.toJson(move));
        String expected = "{\"text\":\"Valid move! Submit your turn.\",\"type\":\"INFO\"}";
        String actualValue = (String) validateMove.handle(request, response);
        assertEquals(expected, actualValue);

    }
}
