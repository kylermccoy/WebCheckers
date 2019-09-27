package com.webcheckers.ui;

import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * The UI controller to POST the SIGN-IN Page
 *
 * @author Justin Yau
 */
public class PostSignInRoute implements Route {

  private static final Logger LOG = Logger.getLogger(PostSignInRoute.class.getName());

  private final TemplateEngine templateEngine;

  public PostSignInRoute(final TemplateEngine templateEngine) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
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
    LOG.finer("PostSignInRoute is invoked.");
    //
    Map<String, Object> vm = new HashMap<>();

    // render the View
    return templateEngine.render(new ModelAndView(vm , "signin.ftl"));
  }

}
