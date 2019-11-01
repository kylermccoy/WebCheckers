package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.Session;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Timeout watcher that will automatically log players out after a period of inactivity.
 *
 * @author Justin  Yau
 */
public class SessionTimeoutWatchdog implements HttpSessionBindingListener {

  private static final Logger LOG = Logger.getLogger(SessionTimeoutWatchdog.class.getName());

  // PLAYER SESSION SPECIFIC INFORMATION THAT WILL BE UTILIZED TO LOG PLAYER OUT WHEN TIME EXPIRES
  private final Player p;
  private final PlayerLobby lobby;
  private final Session session;
  private final GameCenter center;

  /**
   * Creates a new watcher that will detect if the user is inactive and sign them out after a period
   * of inactivity
   *
   * @param lobby - The player lobby global instance where all signed-in players are being tracked
   * @param p - The currentUser of the session
   * @param session - The current session
   */
  public SessionTimeoutWatchdog(final PlayerLobby lobby, final GameCenter center, final Player p, final Session session) {
    LOG.fine("Watch dog created.");
    this.p = Objects.requireNonNull(p);
    this.lobby = Objects.requireNonNull(lobby);
    this.center = Objects.requireNonNull(center);
    this.session = Objects.requireNonNull(session);
  }

  @Override
  public void valueBound(HttpSessionBindingEvent event) {
    LOG.fine(this.p + " has signed in!");
  }

  @Override
  public void valueUnbound(HttpSessionBindingEvent event) {
    // the session is being terminated do some cleanup
    this.lobby.playerLoggedOut(p);
    if(center.isPlayerInGame(p)) {
      center.playerLeftGame(p);
      center.getCheckersGame(p).playerResigned();
    }
    if(this.center.getSpectatingGame(p) != null) {
      this.center.stopSpectating(p);
    }
    session.attribute(GetHomeRoute.CURRENT_USER_KEY, null);
    session.attribute(GetGameRoute.CURRENT_OPPONENT_KEY, null);
    //
    LOG.fine(this.p + " has automatically signed out!");
  }
}
