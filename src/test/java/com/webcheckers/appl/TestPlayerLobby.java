package com.webcheckers.appl;

import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Name: TestPlayerLobby
 * Description: UNIT Tester for PlayerLobby application tier component
 *
 * @author Justin Yau @ RIT CS Student
 */
public class TestPlayerLobby {

  private GameCenter c;
  private PlayerLobby p;

  /*
    This routine will be used to mock objects to test various methods
   */
  @BeforeEach
  public void initializeLobby() {
    /* This section tests the constructor */
    c = new GameCenter();
    p = new PlayerLobby(c);
  }

  /* This section tests the newPlayerInstance method */
  @Test
  public void testNewPlayerInstance() {
    Player p0 = p.newPlayerInstance("Test");
    assertTrue(p0 instanceof Player);
    assertNull(p.newPlayerInstance("Test"));
    assertNull(p.newPlayerInstance(" "));
    assertNull(p.newPlayerInstance("Test ^^^"));
    assertNull(p.newPlayerInstance("Tes%%^^^"));
    assertNull(p.newPlayerInstance("^^^"));
    assertNull(p.newPlayerInstance(""));
  }

  /* This section tests the lobbySize method */
  @Test
  public void testLobbySize() {
    Player p0 = p.newPlayerInstance("Test");
    assertEquals("<li class='player-item'> There are currently 1 players signed in and ready to play! </li>"
            , p.lobbySize(null));
    assertEquals("<li class='player-item'> There are currently 0 players signed in and ready to play! </li>"
            , p.lobbySize(p0));
    Player p1 = p.newPlayerInstance("Test1");
    assertEquals("<li class='player-item'> There are currently 1 players signed in and ready to play! </li>"
            , p.lobbySize(p1));

    p.playerLoggedOut(p1);
  }

  /* This section tests the giveRoster method */
  @Test
  public void testGiveRoster() {
    Player p0 = p.newPlayerInstance("Test");
    assertEquals("<li class='player-item'> There are no other players available to play at this time </li>", p.giveRoster(p0));
    Player p2 = p.newPlayerInstance("Test1");
    assertNotEquals("<li class='player-item'> There are no other players available to play at this time </li>", p.giveRoster(p0));
    c.startGame(p0, p2);
    assertEquals("", p.giveRoster(p0));
  }

  /* This section tests the getPlayer method */
  @Test
  public void testGetPlayer() {
    Player p2 = p.newPlayerInstance("Test1");
    assertEquals(p2, p.getPlayer("Test1"));
    assertNull(p.getPlayer(""));
    assertNull(p.getPlayer(null));
  }

}
