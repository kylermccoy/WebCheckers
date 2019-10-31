package com.webcheckers.ui;

import com.webcheckers.model.Player;
import org.junit.jupiter.api.*;
import spark.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Name: TestGetSignInRoute
 * Description: UNIT Tester for GetSignIn UI controller tier component
 *
 * @author Justin Yau @ RIT CS Student
 */
public class TestGetSignInRoute {

  // Sign-in Route
  private GetSignInRoute signIn;

  // Mocked Objects
  private TemplateEngine engine;
  private Request request;
  private Session session;

  /* This section initializes mocked objects to test with */
  @BeforeEach
  public void initializeSignInRoute() {
    request = mock(Request.class);
    session = mock(Session.class);
    when(request.session()).thenReturn(session);
    engine = mock(TemplateEngine.class);

    signIn = new GetSignInRoute(engine);
  }

  /* This section tests the route for when the player is signed in */
  @Test
  public void test_playerSignedIn() {
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
  public void test_playerNotSignedIn() {
    final Response response = mock(Response.class);

    final TemplateEngineTester myModelView = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(myModelView.makeAnswer());

    // Invoke the test
    signIn.handle(request, response);

    myModelView.assertViewModelExists();
    myModelView.assertViewModelIsaMap();
    myModelView.assertViewName(GetSignInRoute.VIEW_NAME);

    myModelView.assertViewModelAttribute(GetSignInRoute.TITLE_ATTR, GetSignInRoute.TITLE);
    myModelView.assertViewModelAttribute(GetSignInRoute.MESSAGE_ATTR, GetSignInRoute.SIGN_IN_REQUEST);
  }

}
