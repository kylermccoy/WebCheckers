package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.*;
import spark.*;

import javax.servlet.http.HttpSessionBindingEvent;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Name: TestSessionTimeoutWatchdog
 * Description: UNIT Tester for SessionTimeoutWatchdog UI controller tier component
 *
 * @author Justin Yau @ RIT CS Student
 */
public class TestSessionTimeoutWatchdog {

  // Watchdog Object
  private SessionTimeoutWatchdog watchdog;

  // Mocked objects
  private PlayerLobby lobby;
  private Player p;
  private GameCenter center;
  private Session session;

  /* Creates a watchdog instance */
  @BeforeEach
  public void initializeWatchdog() {
    session = mock(Session.class);
    p = mock(Player.class);
    center = new GameCenter();
    lobby = new PlayerLobby(center);

    watchdog = new SessionTimeoutWatchdog(lobby, center, p, session);
  }

  /* Tests watchdog value bound event */
  @Test
  public void testValueBound() {
    HttpSessionBindingEvent event = mock(HttpSessionBindingEvent.class);
    watchdog.valueBound(event);
  }

  /* Tests watchdog value unbound event */
  @Test
  public void testValueUnbound() {
    HttpSessionBindingEvent event = mock(HttpSessionBindingEvent.class);
    watchdog.valueUnbound(event);
    assertNull(session.attribute(GetHomeRoute.CURRENT_USER_KEY));
  }

  /* Tests watchdog value unbound event when player is signed in and in-game*/
  @Test
  public void testValueUnboundInGame() {
    HttpSessionBindingEvent event = mock(HttpSessionBindingEvent.class);
    Player p1 = mock(Player.class);
    center.startGame(p, p1);
    watchdog.valueUnbound(event);
    assertNull(session.attribute(GetHomeRoute.CURRENT_USER_KEY));
  }

  /* Tests watchdog value unbound event when player is signed in and in-game*/
  @Test
  public void testValueUnboundSpectator() {
    HttpSessionBindingEvent event = mock(HttpSessionBindingEvent.class);
    Player p1 = mock(Player.class);
    Player p2 = mock(Player.class);
    center.startGame(p2, p1);
    center.startSpectating(p, center.getGameByID(1));
    watchdog.valueUnbound(event);
    assertNull(session.attribute(GetHomeRoute.CURRENT_USER_KEY));
  }

}
