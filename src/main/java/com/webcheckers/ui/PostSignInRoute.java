package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static spark.Spark.halt;

/**
 * The UI controller to POST the SIGN-IN Page
 *
 * @author Justin Yau
 */
public class PostSignInRoute implements Route {

  private static final Logger LOG = Logger.getLogger(PostSignInRoute.class.getName());

  public static final Message SIGN_IN_ERROR = Message.error("Username is illegal/already signed-in! Please try another name.");
  public static final String MESSAGE_ATTR = "message";

  // Values used in the view-model map for rendering the game view after a sign-in request.
  public static final String USERNAME_PARAM = "playerName";

  // Webserver provided information
  private final TemplateEngine templateEngine;
  private final PlayerLobby lobby;

  /**
   * Create the Spark Route (UI controller) to handle all {@code POST /signin} HTTP requests.
   *
   * @param lobby
   *   the PlayerLobby which contains a list of all players
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public PostSignInRoute(final PlayerLobby lobby, final TemplateEngine templateEngine) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");

    this.lobby = lobby;
    //
    LOG.config("PostSignInRoute is initialized.");
  }

  /**
   * Updates the WebCheckers Sign-In page.
   *
   * @param request
   *   the HTTP request
   * @param response
   *   the HTTP response
   *
   * @return
   *   the rendered HTML for the Sign-In page
   */
  @Override
  public Object handle(Request request, Response response) {
    LOG.fine("PostSignInRoute is invoked.");

    // retrieve the game object
    final Session session = request.session();
    final Player player = session.attribute(GetHomeRoute.CURRENT_USER_KEY);

    // if user is logged-in already, redirect to home page
    if(player != null) {
      response.redirect(WebServer.HOME_URL);
      halt();
      return null;
    }

    // Create the view map to insert objects into
    Map<String, Object> vm = new HashMap<>();
    vm.put(GetSignInRoute.TITLE_ATTR, GetSignInRoute.TITLE);

    String username = request.queryParams(USERNAME_PARAM);
    Player p = this.lobby.newPlayerInstance(username);
    if(p != null) {
      return loginSuccess(session, vm, p);
    } else {
      vm.put(MESSAGE_ATTR, SIGN_IN_ERROR);
    }

    // render the View
    return templateEngine.render(new ModelAndView(vm , "signin.ftl"));
  }

  /**
   * Routine called to handle the success of a new user and redirect them to the logged-in homepage.
   *
   * CONDITIONS:
   *      - Username is unique and the player object has been created
   *
   * @param s - The current session
   * @param vm - The view map that will be passed to the model
   * @param p - The currentUser object of this session
   * @return
   *      A view of the home page with all the updated information regarding the newly signed-in player
   */
  private Object loginSuccess(Session s, Map<String, Object> vm, Player p) {
    s.attribute(GetHomeRoute.CURRENT_USER_KEY, p);
    if(s.attribute(GetHomeRoute.TIMEOUT_SESSION_KEY) == null) {
      // Session timeout routine. The valueUnbound() method in the SessionTimeoutWatchdog will
      // be called when the session is invalidated.
      s.attribute(GetHomeRoute.TIMEOUT_SESSION_KEY, new SessionTimeoutWatchdog(this.lobby, p, s));
      s.maxInactiveInterval(GetHomeRoute.SESSION_TIMEOUT_PERIOD);
    }
    vm.put(GetHomeRoute.TITLE_ATTR, GetHomeRoute.TITLE);
    vm.put(GetHomeRoute.MESSAGE_ATTR, GetHomeRoute.WELCOME_MSG);
    vm.put(GetHomeRoute.CURRENT_USER_ATTR, p);
    vm.put(GetHomeRoute.PLAYER_LIST_ATTR, lobby.giveRoster(p));
    return templateEngine.render(new ModelAndView(vm , GetHomeRoute.VIEW_NAME));
  }

}
