package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
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

  private static final Message SIGN_IN_ERROR = Message.error("Username is too short/already signed-in! Please try another name.");

  // Values used in the view-model map for rendering the game view after a sign-in request.
  private static final String USERNAME_PARAM = "playerName";

  private final TemplateEngine templateEngine;
  private final PlayerLobby lobby;

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
    LOG.finer("PostSignInRoute is invoked.");
    //
    Map<String, Object> vm = new HashMap<>();
    vm.put(GetSignInRoute.TITLE_ATTR, GetSignInRoute.TITLE);

    String username = request.queryParams(USERNAME_PARAM);
    Player p = this.lobby.newPlayerInstance(username);
    if(p != null) {
      loginSuccess(p);
    } else {
      vm.put("message", SIGN_IN_ERROR);
    }

    // render the View
    return templateEngine.render(new ModelAndView(vm , "signin.ftl"));
  }

  private void loginSuccess(Player p) {

  }

}
