package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.BoardView;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.halt;

/**
 * The {@code GET /game} route handler .
 *
 * @author <a href='mailto:bjm9265@rit.edu'>Brian Mirabito</a>
 * @author JUSTIN YAU @ RIT CS STUDENT
 */
public class GetGameRoute implements Route {

  private final GameCenter gameCenter;
  private final TemplateEngine templateEngine;
  private final PlayerLobby playerLobby;
  private final Gson gson;

  public static final String CURRENT_OPPONENT_KEY = "CURRENT_OPPONENT_KEY";
  public static final String GAME_TITLE = "Checkers";

  /***
   * Constructor.
   * @param templateEngine expected Template engine
   * @param gameCenter GameCenter object
   * @param playerLobby PlayerLobby (the web handler)
   */
  public GetGameRoute(TemplateEngine templateEngine, GameCenter gameCenter, Gson gson, PlayerLobby playerLobby ) {
    this.templateEngine = templateEngine;
    this.gameCenter = gameCenter;
    this.playerLobby = playerLobby;
    this.gson = gson;
  }

  @Override
  public Object handle(Request request, Response response) {

    final Session httpSession = request.session();
    final Player player = httpSession.attribute(GetHomeRoute.CURRENT_USER_KEY);
    Player opponent = httpSession.attribute(GetGameRoute.CURRENT_OPPONENT_KEY);
    final Player extractedPlayer = this.playerLobby.getPlayer(request.queryParams("opponentName"));

    CheckersGame game = null;

    final Map<String, Object> modeOptions = new HashMap<>(2);
    modeOptions.put("isGameOver", false);
    modeOptions.put("gameOverMessage", "");

    if(gameCenter.getCheckersGame(player) == null) { // Game has not been initiated or a user logged out
      if(extractedPlayer == null || player == null) {
        response.redirect(WebServer.HOME_URL);
        halt();
        return null;
      }
      this.gameCenter.addInGamePlayers(player, extractedPlayer);
      this.gameCenter.startGame(player, extractedPlayer);
      game = gameCenter.getCheckersGame(player);
      httpSession.attribute(GetGameRoute.CURRENT_OPPONENT_KEY, extractedPlayer);
    } else { // Game is initiated just retrieve for state
      game = gameCenter.getCheckersGame(player);
      if(opponent == null) { // User just joined the game
        httpSession.attribute(GetGameRoute.CURRENT_OPPONENT_KEY, game.getOpponent(player));
        opponent = httpSession.attribute(GetGameRoute.CURRENT_OPPONENT_KEY);
      }
      else if(!gameCenter.isPlayerInGame(opponent)) {
        modeOptions.put("isGameOver", true);
        modeOptions.put("gameOverMessage", opponent.getName() + " has resigned.");
        httpSession.attribute(GetGameRoute.CURRENT_OPPONENT_KEY, null);
        game = gameCenter.getCheckersGame(player);
        gameCenter.playerLeftGame(player);
      }
    }

    Map<String, Object> vm = new HashMap<>();
    if(game != null) { // Safety net
      vm.put("title", GAME_TITLE);
      vm.put("currentUser", player);
      vm.put("redPlayer", game.getRedPlayer());
      vm.put("whitePlayer", game.getWhitePlayer());
      vm.put("activeColor", game.getActiveColor());
      vm.put("board", game.getBoard());
      vm.put("modeOptionsAsJSON", gson.toJson(modeOptions));
      vm.put("invertedView", this.gameCenter.isPlayerViewInverted(player));
      vm.put("viewMode", "PLAY");
    }

    return templateEngine.render(new ModelAndView(vm , "game.ftl"));
  }
}