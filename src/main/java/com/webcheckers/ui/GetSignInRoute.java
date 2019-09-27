package com.webcheckers.ui;

import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * The UI controller to GET the SIGN-IN Page
 *
 * @author Justin Yau @ RIT CS Student
 */
public class GetSignInRoute implements Route {

  private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());

  private static final Message SIGN_IN_REQUEST = Message.info("To sign in, please enter a unique username!");

  public static final String TITLE_ATTR = "title";
  public static final String TITLE = "Sign in!";

  private final TemplateEngine templateEngine;

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
    LOG.finer("GetSignInRoute is invoked.");
    //
    Map<String, Object> vm = new HashMap<>();

    vm.put(TITLE_ATTR, TITLE);
    vm.put("message", SIGN_IN_REQUEST);

    // render the View
    return templateEngine.render(new ModelAndView(vm , "signin.ftl"));
  }
}
