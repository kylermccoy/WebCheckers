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
 * The UI controller to GET the SIGN-IN Page
 *
 * @author Justin Yau @ RIT CS Student
 */
public class GetSignInRoute implements Route {

  private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());

  // CONSTANT KEYS TO KEEP TRACK OF VIEW ATTRIBUTES AND IMPORTANT INFORMATION
  public static final Message SIGN_IN_REQUEST = Message.info("To sign in, please enter a unique username!");
  public static final String VIEW_NAME = "signin.ftl";
  public static final String TITLE_ATTR = "title";
  public static final String TITLE = "Sign in!";
  public static final String MESSAGE_ATTR = "message";

  // WebServer provided information
  private final TemplateEngine templateEngine;

  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /signin} HTTP requests.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public GetSignInRoute(final TemplateEngine templateEngine) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    //
    LOG.config("GetSignInRoute is initialized.");
  }

  /**
   * Render the WebCheckers Sign-In page.
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
    LOG.fine("GetSignInRoute is invoked.");

    // retrieve the game object
    final Session session = request.session();
    final Player player = session.attribute(GetHomeRoute.CURRENT_USER_KEY);

    // Create the view map to insert objects into
    Map<String, Object> vm = new HashMap<>();

    // update the currentPlayer attribute so the page can dynamically change accordingly
    vm.put(GetHomeRoute.CURRENT_USER_ATTR, player);

    // if user is logged-in already, redirect to home page
    if(player != null) {
      response.redirect(WebServer.HOME_URL);
      halt();
      return null;
    } else {

      vm.put(TITLE_ATTR, TITLE);
      vm.put(MESSAGE_ATTR, SIGN_IN_REQUEST);

      // render the View
      return templateEngine.render(new ModelAndView(vm , VIEW_NAME));
    }
  }
}
