package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Name: TestGetGameRoute
 * Description: UNIT Tester for GetGame UI controller tier component
 *
 * @author Brian Mirabito @ RIT CS Student
 */


public class TestGetGameRoute {

    // Sign-in Route
    private GetGameRoute gameRoute;

    // Mocked Objects
    private GameCenter gameCenter;
    private Gson gson;
    private Response response;
    private PlayerLobby playerLobby;
    private CheckersGame checkersGame;
    private Player user;
    private Player opponent;
    private TemplateEngine engine;
    private Request request;
    private Session session;
    private Player nullPlayer;

    /* This section initializes mocked objects to test with */
    @BeforeEach
    public void initializeGameRoute() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);
        gson = new Gson();
        gameCenter = mock(GameCenter.class);
        playerLobby = mock(PlayerLobby.class);
        checkersGame = mock(CheckersGame.class);
        user = new Player("Brian");
        opponent = new Player("Kyle");
        nullPlayer = null;

        gameRoute = new GetGameRoute(engine, gameCenter, gson, playerLobby);
    }

    @Test
    public void test_GameFax() {
        final TemplateEngineTester engineTester = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(engineTester.makeAnswer());
        // Return the current user when asked
        when(session.attribute(GetHomeRoute.CURRENT_USER_KEY)).thenReturn(user);
        // Return the opponent as white
        when(checkersGame.getWhitePlayer()).thenReturn(opponent);
        // Return the user as red
        when(checkersGame.getRedPlayer()).thenReturn(user);
        // Return red as active
        when(checkersGame.getActiveColor()).thenReturn(CheckersGame.color.RED);
        // Return the mocked game when referenced
        when(gameCenter.getCheckersGame(any(Player.class))).thenReturn(checkersGame);

        when(gameCenter.getCheckersGame(nullPlayer)).thenReturn(null);

        // Test statements
        gameRoute.handle(request, response);
        engineTester.assertViewModelExists();
        engineTester.assertViewModelIsaMap();
        engineTester.assertViewName("game.ftl");
        engineTester.assertViewModelAttribute("currentUser", user);
        engineTester.assertViewModelAttribute("gameID", null);
        engineTester.assertViewModelAttribute("redPlayer", user);
        engineTester.assertViewModelAttribute("whitePlayer", opponent);
        engineTester.assertViewModelAttribute("activeColor", CheckersGame.color.RED);
    }
}
