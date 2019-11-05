package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.CheckersGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * Name: TestSubmitTurnRoute
 * Description: UNIT Tester for SubmitTurn UI controller tier component
 *
 * @author Brian Mirabito @ RIT CS Student
 */

public class TestSubmitTurnRoute {

    // Sign-in Route
    private PostSubmitTurnRoute submitTurnRoute;

    // Mocked Objects
    private Request request;
    private Session session;
    private GameCenter gameCenter;
    private CheckersGame game;
    private Gson gson;

    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        gameCenter = mock(GameCenter.class);
        game = mock(CheckersGame.class);
        when(request.session()).thenReturn(session);
        when(session.attribute(any())).thenReturn(null);
        when(gameCenter.getCheckersGame(any())).thenReturn(game);
        gson = new Gson();
        submitTurnRoute = new PostSubmitTurnRoute(gson, gameCenter);
    }

    @Test
    public void test_notPlayersTurnMsg(){
        when(game.getActiveColor()).thenReturn(CheckersGame.color.RED.RED);
        when(game.getPlayerColor(any())).thenReturn(CheckersGame.color.RED.WHITE);
        String expectedValue = gson.toJson(PostSubmitTurnRoute.NO_TURN);
        String actualValue = (String) submitTurnRoute.handle(request, null);
        assertEquals(expectedValue, actualValue);
    }
}
