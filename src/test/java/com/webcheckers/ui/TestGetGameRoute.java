package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.junit.jupiter.api.Assertions.assertTrue;
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
  private Player user;
  private Player opponent;
  private TemplateEngine engine;
  private Request request;
  private Session session;

  /* This section initializes mocked objects to test with */
  @BeforeEach
  public void initializeGameRoute() {
    request = mock(Request.class);
    session = mock(Session.class);
    when(request.session()).thenReturn(session);
    response = mock(Response.class);
    engine = mock(TemplateEngine.class);
    gson = new Gson();
    gameCenter = new GameCenter();
    playerLobby = new PlayerLobby(gameCenter);
    user = new Player("Brian");
    opponent = new Player("Kyle");

    gameRoute = new GetGameRoute(engine, gameCenter, gson, playerLobby);
  }

  @Test
  public void test_GameFax() {
    final TemplateEngineTester engineTester = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(engineTester.makeAnswer());
    // Return the current user when asked
    when(session.attribute(GetHomeRoute.CURRENT_USER_KEY)).thenReturn(user);

    gameCenter.startGame(user, opponent);

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

  @Test
  public void test_nullPlayer(){
    user = null;
    when(session.attribute(GetHomeRoute.CURRENT_USER_KEY)).thenReturn(user);
    when(session.attribute(GetGameRoute.CURRENT_OPPONENT_KEY)).thenReturn(null);
    try{
      gameRoute.handle(request, response);
    }catch(HaltException e){
      assertTrue(e instanceof HaltException);
    }

  }

  @Test void test_getGame(){
    final TemplateEngineTester engineTester = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(engineTester.makeAnswer());

    gameCenter.startGame(user, opponent);
    CheckersGame game = gameCenter.getCheckersGame(user);
    gameCenter.playerLeftGame(opponent);
    game.playerResigned();

    when(session.attribute(GetHomeRoute.CURRENT_USER_KEY)).thenReturn(user);
    when(session.attribute(GetGameRoute.CURRENT_OPPONENT_KEY)).thenReturn(opponent);

    try{
      gameRoute.handle(request, response);
    }catch(HaltException e){
      assertTrue(e instanceof HaltException);
    }
  }

  @Test void test_endGameWon(){
    final TemplateEngineTester engineTester = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(engineTester.makeAnswer());

    gameCenter.startGame(user, opponent);
    CheckersGame game = gameCenter.getCheckersGame(user);
    game.recordEndGame(user, opponent);

    when(session.attribute(GetHomeRoute.CURRENT_USER_KEY)).thenReturn(user);
    when(session.attribute(GetGameRoute.CURRENT_OPPONENT_KEY)).thenReturn(opponent);
    gameRoute.handle(request, response);
  }

  @Test void test_endGameLost(){
    final TemplateEngineTester engineTester = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(engineTester.makeAnswer());

    gameCenter.startGame(user, opponent);
    CheckersGame game = gameCenter.getCheckersGame(user);
    game.recordEndGame(opponent, user);

    when(session.attribute(GetHomeRoute.CURRENT_USER_KEY)).thenReturn(user);
    when(session.attribute(GetGameRoute.CURRENT_OPPONENT_KEY)).thenReturn(opponent);
    gameRoute.handle(request, response);
  }
}