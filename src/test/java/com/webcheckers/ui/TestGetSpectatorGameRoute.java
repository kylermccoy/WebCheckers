package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.*;
import spark.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Name: TestSpectatorGameRoute
 * Description: UNIT Tester for TestSpectatorGame UI controller tier component
 *
 * @author Justin Yau @ RIT CS Student
 */
public class TestGetSpectatorGameRoute {

  // GetSpectatorGame object
  private GetSpectatorGameRoute spectatorGame;

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
    gson = new Gson();
    center = new GameCenter();
    playerLobby = new PlayerLobby(center);

    spectatorGame = new GetSpectatorGameRoute(engine, gson, center);
  }

  /* This tests the route for when the user is not signed in */
  @Test
  public void test_NotSignedIn() {
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

  /* This tests the route for when the user is signed in but in-game*/
  @Test
  public void test_SignedInGame() {
    Player player = playerLobby.newPlayerInstance("Test");
    when(session.attribute(GetHomeRoute.CURRENT_USER_KEY)).thenReturn(player);

    center.startGame(player, player);

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

  /* This tests the route for when the user is signed in and can spectate but gave no ID*/
  @Test
  public void test_SpectateIncorrect() {
    Player player = playerLobby.newPlayerInstance("Test");
    when(session.attribute(GetHomeRoute.CURRENT_USER_KEY)).thenReturn(player);

    Player player1 = playerLobby.newPlayerInstance("Test1");
    Player player2 = playerLobby.newPlayerInstance("Test2");

    center.startGame(player1, player2);

    when(request.queryParams("gameID")).thenReturn("");

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

  /* This tests the route for when the user is signed in and there is a game to view as well as a refresh */
  /* Also tests for when the game is resigned */
  @Test
  public void test_spectator_legal_resigned() {
    Player player = playerLobby.newPlayerInstance("Test");
    when(session.attribute(GetHomeRoute.CURRENT_USER_KEY)).thenReturn(player);

    Player player1 = playerLobby.newPlayerInstance("Test1");
    Player player2 = playerLobby.newPlayerInstance("Test2");

    center.startGame(player1, player2);

    when(request.queryParams("gameID")).thenReturn("1");

    final Response response = mock(Response.class);

    final TemplateEngineTester myModelView = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(myModelView.makeAnswer());

    spectatorGame.handle(request, response);

    when(request.queryParams("gameID")).thenReturn("");

    center.getGameByID(1).playerResigned();

    spectatorGame.handle(request, response);

    myModelView.assertViewModelExists();
    myModelView.assertViewModelIsaMap();
    myModelView.assertViewName("game.ftl");
  }

  /* Also tests for when user is spectating a game where the game has a victor*/
  @Test
  public void test_spectator_legal_victor() {
    Player player = playerLobby.newPlayerInstance("Test");
    when(session.attribute(GetHomeRoute.CURRENT_USER_KEY)).thenReturn(player);

    Player player1 = playerLobby.newPlayerInstance("Test1");
    Player player2 = playerLobby.newPlayerInstance("Test2");

    center.startGame(player1, player2);

    when(request.queryParams("gameID")).thenReturn("1");

    center.getGameByID(1).recordEndGame(player2, player1);

    final Response response = mock(Response.class);

    final TemplateEngineTester myModelView = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(myModelView.makeAnswer());

    spectatorGame.handle(request, response);

    center.getGameByID(1).recordEndGame(player1, player2);

    spectatorGame.handle(request, response);

    myModelView.assertViewModelExists();
    myModelView.assertViewModelIsaMap();
    myModelView.assertViewName("game.ftl");
  }

  /* Also tests for when user is spectating a game where the game has a victor*/
  @Test
  public void test_spectator_legal_victor1() {
    Player player = playerLobby.newPlayerInstance("Test");
    when(session.attribute(GetHomeRoute.CURRENT_USER_KEY)).thenReturn(player);

    Player player1 = playerLobby.newPlayerInstance("Test1");
    Player player2 = playerLobby.newPlayerInstance("Test2");

    center.startGame(player2, player1);

    when(request.queryParams("gameID")).thenReturn("1");

    center.getGameByID(1).recordEndGame(player2, player1);

    final Response response = mock(Response.class);

    final TemplateEngineTester myModelView = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(myModelView.makeAnswer());

    spectatorGame.handle(request, response);

    center.getGameByID(1).recordEndGame(player1, player2);

    spectatorGame.handle(request, response);

    myModelView.assertViewModelExists();
    myModelView.assertViewModelIsaMap();
    myModelView.assertViewName("game.ftl");
  }

}
