package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.*;
import spark.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

/**
 * Name: TestGetHomeRoute
 * Description: UNIT Tester for GetHomeRoute UI controller tier component
 *
 * @author Justin Yau @ RIT CS Student
 */
public class TestGetHomeRoute {

  // Mocked Route
  private GetHomeRoute home;

  // Mocked Objects
  private Request request;
  private Session session;
  private TemplateEngine engine;
  private PlayerLobby playerLobby;
  private GameCenter center;
  private SessionTimeoutWatchdog watchdog;

  /* This section initializes mocked objects to test with */
  @BeforeEach
  public void initializeHomeRoute() {
    request = mock(Request.class);
    session = mock(Session.class);
    when(request.session()).thenReturn(session);
    engine = mock(TemplateEngine.class);
    center = new GameCenter();
    playerLobby = new PlayerLobby(center);
    watchdog = mock(SessionTimeoutWatchdog.class);

    home = new GetHomeRoute(playerLobby, center, engine);
  }

  /* Tests to make sure that everything is normal when user is not signed in */
  @Test
  public void test_notSignedIn() {
    final Response response = mock(Response.class);

    final TemplateEngineTester myModelView = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(myModelView.makeAnswer());

    // Invoke the test
    home.handle(request, response);

    myModelView.assertViewModelExists();
    myModelView.assertViewModelIsaMap();
    myModelView.assertViewName(GetHomeRoute.VIEW_NAME);

    myModelView.assertViewModelAttribute(GetHomeRoute.CURRENT_USER_ATTR, null);
    myModelView.assertViewModelAttribute(GetHomeRoute.MESSAGE_ATTR, GetHomeRoute.WELCOME_MSG);
    myModelView.assertViewModelAttribute(GetHomeRoute.TITLE_ATTR, GetHomeRoute.TITLE);
    myModelView.assertViewModelAttribute(GetHomeRoute.PLAYER_LIST_ATTR, "<li class='player-item'> There are currently 0 players signed in and ready to play! </li>");
    assertNull(session.attribute(GetHomeRoute.TIMEOUT_SESSION_KEY));
  }

  /* Tests to make sure that everything is normal when user is signed in */
  @Test
  public void test_signedInAloneNoWatchdog() {
    Player player = playerLobby.newPlayerInstance("Test");
    when(session.attribute(GetHomeRoute.CURRENT_USER_KEY)).thenReturn(player);
    when(session.attribute(GetHomeRoute.TIMEOUT_SESSION_KEY)).thenReturn(null);
    final Response response = mock(Response.class);

    final TemplateEngineTester myModelView = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(myModelView.makeAnswer());

    // Invoke the test
    home.handle(request, response);

    myModelView.assertViewModelExists();
    myModelView.assertViewModelIsaMap();
    myModelView.assertViewName(GetHomeRoute.VIEW_NAME);

    myModelView.assertViewModelAttribute(GetHomeRoute.CURRENT_USER_ATTR, player);
    myModelView.assertViewModelAttribute(GetHomeRoute.MESSAGE_ATTR, GetHomeRoute.WELCOME_MSG);
    myModelView.assertViewModelAttribute(GetHomeRoute.TITLE_ATTR, GetHomeRoute.TITLE);
    myModelView.assertViewModelAttribute(GetHomeRoute.PLAYER_LIST_ATTR, "<li class='player-item'> There are no other players available to play at this time </li>");
    assertNull(session.attribute(GetHomeRoute.TIMEOUT_SESSION_KEY));
  }

  /* Tests to make sure that everything is normal when user is signed in */
  @Test
  public void test_signedInAloneWatchdog() {
    Player player = playerLobby.newPlayerInstance("Test");
    when(session.attribute(GetHomeRoute.CURRENT_USER_KEY)).thenReturn(player);
    when(session.attribute(GetHomeRoute.TIMEOUT_SESSION_KEY)).thenReturn(watchdog);
    final Response response = mock(Response.class);

    final TemplateEngineTester myModelView = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(myModelView.makeAnswer());

    // Invoke the test
    home.handle(request, response);

    myModelView.assertViewModelExists();
    myModelView.assertViewModelIsaMap();
    myModelView.assertViewName(GetHomeRoute.VIEW_NAME);

    myModelView.assertViewModelAttribute(GetHomeRoute.CURRENT_USER_ATTR, player);
    myModelView.assertViewModelAttribute(GetHomeRoute.MESSAGE_ATTR, GetHomeRoute.WELCOME_MSG);
    myModelView.assertViewModelAttribute(GetHomeRoute.TITLE_ATTR, GetHomeRoute.TITLE);
    myModelView.assertViewModelAttribute(GetHomeRoute.PLAYER_LIST_ATTR, "<li class='player-item'> There are no other players available to play at this time </li>");
    assertNotNull(session.attribute(GetHomeRoute.TIMEOUT_SESSION_KEY));
  }

  /* Tests to make sure that everything is normal when user is signed in and in-game */
  @Test
  public void test_signedInGame() {
    Player player = playerLobby.newPlayerInstance("Test");
    Player player1 = playerLobby.newPlayerInstance("Test1");
    when(session.attribute(GetHomeRoute.CURRENT_USER_KEY)).thenReturn(player);
    center.startGame(player, player1);

    final Response response = mock(Response.class);

    final TemplateEngineTester myModelView = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(myModelView.makeAnswer());

    // Invoke the test
    try{
      home.handle(request, response);
    }catch(HaltException e){
      assertTrue(e instanceof HaltException);
    }

    assertNull(myModelView.model);
  }

  /* Tests to make sure that everything is normal when user is signed in and spectating */
  @Test
  public void test_signedInSpectator() {
    Player player = playerLobby.newPlayerInstance("Test");
    Player player1 = playerLobby.newPlayerInstance("Test1");
    Player player2 = playerLobby.newPlayerInstance("Test2");
    when(session.attribute(GetHomeRoute.CURRENT_USER_KEY)).thenReturn(player);
    center.startGame(player2, player1);
    center.startSpectating(player, center.getGameByID(1));

    final Response response = mock(Response.class);

    final TemplateEngineTester myModelView = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(myModelView.makeAnswer());

    // Invoke the test
    try{
      home.handle(request, response);
    }catch(HaltException e){
      assertTrue(e instanceof HaltException);
    }

    assertNull(myModelView.model);
  }
}
