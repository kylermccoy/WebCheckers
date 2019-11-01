package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.*;
import spark.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Name: TestPostSignOutRoute
 * Description: UNIT Tester for PostSignOut UI controller tier component
 *
 * @author Justin Yau @ RIT CS Student
 */
public class TestPostSignOutRoute {

  // PostSignOutRoute Object
  private PostSignOutRoute signOut;

  // Mocked Objects
  private Request request;
  private Session session;
  private TemplateEngine engine;
  private PlayerLobby playerLobby;
  private GameCenter center;

  /* This section initializes mocked objects to test with */
  @BeforeEach
  public void initializeSignOut() {
    request = mock(Request.class);
    session = mock(Session.class);
    when(request.session()).thenReturn(session);
    engine = mock(TemplateEngine.class);
    center = new GameCenter();
    playerLobby = new PlayerLobby(center);

    signOut = new PostSignOutRoute(playerLobby, center, engine);
  }

  /* This tests for when the route is subjected to a user that is not signed in */
  @Test
  public void test_notSignedIn() {
    final Response response = mock(Response.class);

    final TemplateEngineTester myModelView = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(myModelView.makeAnswer());

    // Invoke the test
    try{
      signOut.handle(request, response);
    }catch(HaltException e){
      assertTrue(e instanceof HaltException);
    }

    assertNull(myModelView.model);
  }

  /* This tests for when the route is subjected to a user that is signed in */
  @Test
  public void test_signedInNotInGame() {
    Player player = playerLobby.newPlayerInstance("Test");
    when(session.attribute(GetHomeRoute.CURRENT_USER_KEY)).thenReturn(player);
    final Response response = mock(Response.class);

    final TemplateEngineTester myModelView = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(myModelView.makeAnswer());

    // Invoke the test
    try{
      signOut.handle(request, response);
    }catch(HaltException e){
      assertTrue(e instanceof HaltException);
    }

    assertNull(myModelView.model);
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
    try{
      signOut.handle(request, response);
    }catch(HaltException e){
      assertTrue(e instanceof HaltException);
    }

    assertNull(myModelView.model);
  }

  /* This tests for when the route is subjected to a user that is signed in and spectating */
  @Test
  public void test_signedInSpectator() {
    Player player = playerLobby.newPlayerInstance("Test");
    Player player1 = playerLobby.newPlayerInstance("Test1");
    center.startGame(player, player1);
    Player player2 = playerLobby.newPlayerInstance("Test2");
    center.startSpectating(player2, center.getGameByID(1));
    when(session.attribute(GetHomeRoute.CURRENT_USER_KEY)).thenReturn(player2);
    final Response response = mock(Response.class);

    final TemplateEngineTester myModelView = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(myModelView.makeAnswer());

    // Invoke the test
    try{
      signOut.handle(request, response);
    }catch(HaltException e){
      assertTrue(e instanceof HaltException);
    }

    assertNull(myModelView.model);
  }

}
