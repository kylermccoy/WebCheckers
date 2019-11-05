package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Move;
import com.webcheckers.model.MoveValidator;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Session;
import spark.TemplateEngine;

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
        center = mock(GameCenter.class);
        playerLobby = new PlayerLobby(center);
        player = new Player("Dan");
        validator = mock(MoveValidator.class);

        validateMove = new PostValidateMoveRoute(gson, center);
    }
/**
    @Test
    public void test_validate(){
        when(request.session().attribute(GetHomeRoute.CURRENT_USER_KEY)).thenReturn(player);

        when(center.getCheckersGame(player)).thenReturn(game);
        when(validator.validateMove())
        when(game.(any())).thenReturn(new InfoMessage("test"));
    }
    **/
}
