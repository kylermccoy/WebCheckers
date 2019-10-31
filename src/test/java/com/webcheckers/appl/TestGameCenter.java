package com.webcheckers.appl;

import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
 * Name: TestGameCenter
 * Description: UNIT Tester for GameCenter Application tier component
 *
 * @author Brian Mirabito @ RIT CS Student
 */

public class TestGameCenter {
    private Player p1;
    private Player p2;
    private Player p3;
    private Player p4;

    private GameCenter gameCenter;

    @BeforeEach
    public void initializeSignInRoute() {
        p1 = new Player("Brian");
        p2 = new Player("Kyle");
        p3 = new Player("Justin");
        p4 = null;
    }

    @Test
    public void test_notNullDoesExist(){
        GameCenter gameCenter = new GameCenter();
        assertNotNull(gameCenter);
        assertTrue(gameCenter instanceof GameCenter);
    }

    @Test
    public void test_startGame(){
        GameCenter gameCenter = new GameCenter();
        gameCenter.startGame(p1, p2);
        assertEquals(gameCenter.getCheckersGame(p1), gameCenter.getCheckersGame(p2));
    }

    @Test
    public void test_playersInGame(){
        GameCenter gameCenter = new GameCenter();
        gameCenter.startGame(p1, p2);
        assertTrue(gameCenter.isPlayerInGame(p1));
        assertTrue(gameCenter.isPlayerInGame(p2));
        assertFalse(gameCenter.isPlayerInGame(p3));
    }

    @Test
    public void test_playersLeftGame(){
        GameCenter gameCenter = new GameCenter();
        gameCenter.startGame(p1, p2);
        gameCenter.playerLeftGame(p1);
        assertFalse(gameCenter.isPlayerInGame(p1));
        assertNull(gameCenter.getCheckersGame(p1));
    }
}
