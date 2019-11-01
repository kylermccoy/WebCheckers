package com.webcheckers.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class TestCheckersGame {
    private Player p1;
    private Player p2;
    private CheckersGame checkersGame;

    @BeforeEach
    public void initializeTest(){
       p1 = new Player("tester1");
       p2 = new Player("tester2");
      checkersGame = new CheckersGame(p1,p2);
    }


    /***
     * Testing getboard()
     */
    @Test
    public void test_getBoard(){
        BoardView board = checkersGame.getBoard();
        assertNotNull(board);
    }

    @Test
    /***
     * Test getGameID()
     */
    public void test_getGameID(){
        assertNotNull(checkersGame.getGameID());
    }

    @Test
    /***
     * Test getRedplayer()
     */
    public void test_getRedPlayer(){
        assertNotNull(checkersGame.getRedPlayer());
    }

    @Test
    /***
     * Test getWhiteplayer()
     */
    public void test_getWhitePlayer(){
        assertNotNull(checkersGame.getWhitePlayer());
    }

    @Test
    /***
     * Test getPlayerColor(Player player)
     */
    public void test_getPlayerColor(){
        assertEquals("RED",checkersGame.getPlayerColor(p1).toString());
        assertEquals("WHITE",checkersGame.getPlayerColor(p2).toString());
    }

    @Test
    /***
     * Test getOpponent(Player player)
     */
    public void test_getOpponent(){
        assertEquals(p1,checkersGame.getOpponent(p2));
        assertEquals(p2,checkersGame.getOpponent(p1));
    }

    @Test
    /***
     * Test swapActiveColor()
     */
    public void test_swapActiveColor(){
        //checkersGame.swapActiveColor();
        //assertEquals("WHITE",checkersGame.getActiveColor().toString());
    }
}
