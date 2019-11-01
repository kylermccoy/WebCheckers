package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.*;
import spark.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Name: TestPostSpectatorCheckTurnRoute
 * Description: UNIT Tester for PostSpectatorCheckTurn UI controller tier component
 *
 * @author Justin Yau @ RIT CS Student
 */
public class TestPostSpectatorCheckTurnRoute {

  // PostSpectatorCheckTurn object
  private PostSpectatorCheckTurnRoute spectatorGame;

  // Mocked Objects
  private Request request;
  private Session session;
  private TemplateEngine engine;
  private PlayerLobby playerLobby;
  private GameCenter center;
  private Gson gson;

  /* This section initializes mocked objects to test with */
  @BeforeEach
  public void initializeRoute() {
    request = mock(Request.class);
    session = mock(Session.class);
    when(request.session()).thenReturn(session);
    engine = mock(TemplateEngine.class);
    center = new GameCenter();
    gson = new Gson();
    playerLobby = new PlayerLobby(center);

    spectatorGame = new PostSpectatorCheckTurnRoute(engine, gson, center);
  }

  /* This test simulates when the user is not signed in */
  @Test
  public void test_notSignedIn() {
    final Response response = mock(Response.class);

    final TemplateEngineTester myModelView = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(myModelView.makeAnswer());

    // Invoke the test
    assertEquals(gson.toJson(Message.info("false")), spectatorGame.handle(request, response));
  }

  /* This test simulates when the user is signed in and is NOT spectating a game*/
  @Test
  public void test_signedInGameInValid() {
    Player player = playerLobby.newPlayerInstance("Test");
    when(session.attribute(GetHomeRoute.CURRENT_USER_KEY)).thenReturn(player);

    final Response response = mock(Response.class);

    final TemplateEngineTester myModelView = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(myModelView.makeAnswer());

    // Invoke the test
    assertEquals(gson.toJson(Message.info("false")), spectatorGame.handle(request, response));
  }

  /* This test simulates when the user is signed in and is spectating a game*/
  @Test
  public void test_signedInGameValid() {
    Player player = playerLobby.newPlayerInstance("Test");
    when(session.attribute(GetHomeRoute.CURRENT_USER_KEY)).thenReturn(player);

    Player player1 = playerLobby.newPlayerInstance("Test1");
    Player player2 = playerLobby.newPlayerInstance("Test2");

    center.startGame(player1, player2);
    center.startSpectating(player, center.getGameByID(1));

    when(request.queryParams("gameID")).thenReturn("1");
    when(session.attribute(GetSpectatorGameRoute.LAST_COLOR_KEY)).thenReturn(CheckersGame.color.WHITE);

    final Response response = mock(Response.class);

    final TemplateEngineTester myModelView = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(myModelView.makeAnswer());

    // Invoke the test
    assertEquals(gson.toJson(Message.info("true")), spectatorGame.handle(request, response));

    when(session.attribute(GetSpectatorGameRoute.LAST_COLOR_KEY)).thenReturn(CheckersGame.color.RED);

    // Invoke the test
    assertEquals(gson.toJson(Message.info("false")), spectatorGame.handle(request, response));

    center.getGameByID(1).playerResigned();

    assertEquals(gson.toJson(Message.info("true")), spectatorGame.handle(request, response));

    when(request.queryParams("gameID")).thenReturn("2");
    center.startGame(player1, player2);
    center.startGame(player1, player2);
    center.getGameByID(2).recordEndGame(player1, player2);
    assertEquals(gson.toJson(Message.info("true")), spectatorGame.handle(request, response));
  }

}
