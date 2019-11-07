package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.*;
import spark.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Name: TestGetSpectatorStopWatchingRoute
 * Description: UNIT Tester for GetSpectatorStopWatching UI controller tier component
 *
 * @author Justin Yau @ RIT CS Student
 */
public class TestGetSpectatorStopWatchingRoute {

  // GetSpectatorStopWatching object
  private GetSpectatorStopWatchingRoute spectatorGame;

  // Mocked Objects
  private Request request;
  private Session session;
  private TemplateEngine engine;
  private PlayerLobby playerLobby;
  private GameCenter center;

  /* This section initializes mocked objects to test with */
  @BeforeEach
  public void initializeRoute() {
    request = mock(Request.class);
    session = mock(Session.class);
    when(request.session()).thenReturn(session);
    engine = mock(TemplateEngine.class);
    center = new GameCenter();
    playerLobby = new PlayerLobby(center);

    spectatorGame = new GetSpectatorStopWatchingRoute(engine, center);
  }

  /* This test simulates when the user is not signed in */
  @Test
  public void test_notSignedIn() {
    final Response response = mock(Response.class);

    final TemplateEngineTester myModelView = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(myModelView.makeAnswer());

    // Invoke the test
    try{
      spectatorGame.handle(request, response);
    }catch(HaltException e){
      assertTrue(e instanceof HaltException);
    }

    assertNull(myModelView.model);
  }

  /* This test simulates when the user is signed in and the game id is valid and is the game they're spectating */
  @Test
  public void test_signedInGameValid() {
    Player player = playerLobby.newPlayerInstance("Test");
    when(session.attribute(GetHomeRoute.CURRENT_USER_KEY)).thenReturn(player);

    Player player1 = playerLobby.newPlayerInstance("Test1");
    Player player2 = playerLobby.newPlayerInstance("Test2");

    center.startGame(player1, player2);
    center.startSpectating(player, center.getGameByID(1));

    when(request.queryParams("gameID")).thenReturn("1");

    final Response response = mock(Response.class);

    final TemplateEngineTester myModelView = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(myModelView.makeAnswer());

    // Invoke the test
    try{
      spectatorGame.handle(request, response);
    }catch(HaltException e){
      assertTrue(e instanceof HaltException);
    }

    assertNull(myModelView.model);
  }

  /* This test simulates when the user is signed in and the game id is not valid */
  @Test
  public void test_signedInGameInValid() {
    Player player = playerLobby.newPlayerInstance("Test");
    when(session.attribute(GetHomeRoute.CURRENT_USER_KEY)).thenReturn(player);

    Player player1 = playerLobby.newPlayerInstance("Test1");
    Player player2 = playerLobby.newPlayerInstance("Test2");

    center.startGame(player1, player2);
    center.startSpectating(player, center.getGameByID(1));

    when(request.queryParams("gameID")).thenReturn("2");

    final Response response = mock(Response.class);

    final TemplateEngineTester myModelView = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(myModelView.makeAnswer());

    // Invoke the test
    try{
      spectatorGame.handle(request, response);
    }catch(HaltException e){
      assertTrue(e instanceof HaltException);
    }

    assertNull(myModelView.model);
  }

  /* This test simulates when the user is signed in and the game ID is valid but not the right one */
  @Test
  public void test_signedInGameInValid1() {
    Player player = playerLobby.newPlayerInstance("Test");
    when(session.attribute(GetHomeRoute.CURRENT_USER_KEY)).thenReturn(player);

    Player player1 = playerLobby.newPlayerInstance("Test1");
    Player player2 = playerLobby.newPlayerInstance("Test2");

    center.startGame(player1, player2);
    center.startGame(player1, player2);
    center.startSpectating(player, center.getGameByID(1));

    when(request.queryParams("gameID")).thenReturn("2");

    final Response response = mock(Response.class);

    final TemplateEngineTester myModelView = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(myModelView.makeAnswer());

    // Invoke the test
    try{
      spectatorGame.handle(request, response);
    }catch(HaltException e){
      assertTrue(e instanceof HaltException);
    }

    assertNull(myModelView.model);
  }

}
