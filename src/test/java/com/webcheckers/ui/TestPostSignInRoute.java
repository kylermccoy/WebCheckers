package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.*;
import spark.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Name: TestPostSignInRoute
 * Description: UNIT Tester for PostSignIn UI controller tier component
 *
 * @author Justin Yau @ RIT CS Student
 */
public class TestPostSignInRoute {

  // PostSignInRoute Object
  private PostSignInRoute signIn;

  // Mocked Objects
  private Request request;
  private Session session;
  private GameCenter center;
  private PlayerLobby lobby;
  private TemplateEngine engine;
  private SessionTimeoutWatchdog watchdog;

  /* This section initializes mocked objects to test with */
  @BeforeEach
  public void initializePSignInRoute() {
    request = mock(Request.class);
    session = mock(Session.class);
    when(request.session()).thenReturn(session);
    engine = mock(TemplateEngine.class);
    center = new GameCenter();
    lobby = new PlayerLobby(center);
    watchdog = mock(SessionTimeoutWatchdog.class);

    signIn = new PostSignInRoute(lobby, center, engine);
  }

  /* This section tests the route for when the player is signed in */
  @Test
  public void test_signedInRequest() {
    Player player = new Player("Test");
    when(session.attribute(GetHomeRoute.CURRENT_USER_KEY)).thenReturn(player);

    final Response response = mock(Response.class);

    final TemplateEngineTester myModelView = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(myModelView.makeAnswer());

    // Invoke the test
    try{
      signIn.handle(request, response);
    }catch(HaltException e){
      assertTrue(e instanceof HaltException);
    }

    assertNull(myModelView.model);
  }

  /* This section tests the route for when the player is not signed in */
  @Test
  public void test_normalSignInRequestError() {
    when(request.queryParams(PostSignInRoute.USERNAME_PARAM)).thenReturn("");
    final Response response = mock(Response.class);

    final TemplateEngineTester myModelView = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(myModelView.makeAnswer());

    // Invoke the test
    signIn.handle(request, response);

    myModelView.assertViewModelExists();
    myModelView.assertViewModelIsaMap();
    myModelView.assertViewName(GetSignInRoute.VIEW_NAME);

    myModelView.assertViewModelAttribute(PostSignInRoute.MESSAGE_ATTR, PostSignInRoute.SIGN_IN_ERROR);
  }

  /* This section tests the route for when the player is not signed in */
  @Test
  public void test_normalSignInRequestNoWatchdog() {
    when(request.queryParams(PostSignInRoute.USERNAME_PARAM)).thenReturn("Test");
    when(session.attribute(GetHomeRoute.TIMEOUT_SESSION_KEY)).thenReturn(null);
    final Response response = mock(Response.class);

    final TemplateEngineTester myModelView = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(myModelView.makeAnswer());

    // Invoke the test
    signIn.handle(request, response);

    myModelView.assertViewModelExists();
    myModelView.assertViewModelIsaMap();
    myModelView.assertViewName(GetHomeRoute.VIEW_NAME);

    myModelView.assertViewModelAttribute(GetHomeRoute.MESSAGE_ATTR, GetHomeRoute.WELCOME_MSG);
    myModelView.assertViewModelAttribute(GetHomeRoute.TITLE_ATTR, GetHomeRoute.TITLE);
    myModelView.assertViewModelAttribute(GetHomeRoute.PLAYER_LIST_ATTR, "<li class='player-item'> There are no other players available to play at this time </li>");
    assertNull(session.attribute(GetHomeRoute.TIMEOUT_SESSION_KEY));
  }

  /* This section tests the route for when the player is not signed in */
  @Test
  public void test_normalSignInRequestWatchdog() {
    when(request.queryParams(PostSignInRoute.USERNAME_PARAM)).thenReturn("Test");
    when(session.attribute(GetHomeRoute.TIMEOUT_SESSION_KEY)).thenReturn(watchdog);
    final Response response = mock(Response.class);

    final TemplateEngineTester myModelView = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(myModelView.makeAnswer());

    // Invoke the test
    signIn.handle(request, response);

    myModelView.assertViewModelExists();
    myModelView.assertViewModelIsaMap();
    myModelView.assertViewName(GetHomeRoute.VIEW_NAME);

    myModelView.assertViewModelAttribute(GetHomeRoute.MESSAGE_ATTR, GetHomeRoute.WELCOME_MSG);
    myModelView.assertViewModelAttribute(GetHomeRoute.TITLE_ATTR, GetHomeRoute.TITLE);
    myModelView.assertViewModelAttribute(GetHomeRoute.PLAYER_LIST_ATTR, "<li class='player-item'> There are no other players available to play at this time </li>");
    assertNotNull(session.attribute(GetHomeRoute.TIMEOUT_SESSION_KEY));
  }

}
