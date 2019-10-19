package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.*;
import spark.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Name: TestPostResignGameRoute
 * Description: UNIT Tester for PostResignGame UI controller tier component
 *
 * @author Justin Yau @ RIT CS Student
 */
public class TestPostResignGameRoute {

  // PostResignGameRoute object
  private PostResignGameRoute resignGame;

  // Mocked Objects
  private Request request;
  private Session session;
  private TemplateEngine engine;
  private PlayerLobby playerLobby;
  private GameCenter center;
  private Gson gson;

  /* This section initializes mocked objects to test with */
  @BeforeEach
  public void initializeResignGame() {
    request = mock(Request.class);
    session = mock(Session.class);
    when(request.session()).thenReturn(session);
    engine = mock(TemplateEngine.class);
    gson = new Gson();
    center = new GameCenter();
    playerLobby = new PlayerLobby(center);

    resignGame = new PostResignGameRoute(playerLobby, center, gson, engine);
  }

  /* This tests for when the route is subjected to a user that is signed in and not in-game */
  @Test
  public void test_signedInNoGame() {
    Player player = playerLobby.newPlayerInstance("Test");
    when(session.attribute(GetHomeRoute.CURRENT_USER_KEY)).thenReturn(player);
    final Response response = mock(Response.class);

    final TemplateEngineTester myModelView = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(myModelView.makeAnswer());

    // Invoke the test
    assertEquals(gson.toJson(Message.error("false")), resignGame.handle(request, response));
  }

  /* This tests for when the route is subjected to a user that is signed in and in-game */
  @Test
  public void test_signedInGame() {
    Player player = playerLobby.newPlayerInstance("Test");
    Player player1 = playerLobby.newPlayerInstance("Test1");
    center.startGame(player, player1);
    when(session.attribute(GetHomeRoute.CURRENT_USER_KEY)).thenReturn(player);
    final Response response = mock(Response.class);

    final TemplateEngineTester myModelView = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(myModelView.makeAnswer());

    // Invoke the test
    assertEquals(gson.toJson(Message.info("true")), resignGame.handle(request, response));
  }

  /* This tests for when the route is subjected to a user that is not signed in */
  @Test
  public void test_notSignedIn() {
    final Response response = mock(Response.class);

    final TemplateEngineTester myModelView = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(myModelView.makeAnswer());

    // Invoke the test
    assertEquals(gson.toJson(Message.error("false")), resignGame.handle(request, response));
  }

}
