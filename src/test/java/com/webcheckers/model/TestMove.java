/**
 * Unit tests for Move Class
 * Kyle McCoy
 */
package com.webcheckers.model;

import static  org.junit.jupiter.api.Assertions.* ;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-Tier")
public class TestMove {

    @Test
    public void test_getStart() {
        final Position p1 = new Position(0,0) ;
        final Position p2 = new Position(1,1) ;

        final Move move = new Move(p1, p2);

        assertEquals(p1.getCell(), move.getStart().getCell());
        assertEquals(p1.getRow(), move.getStart().getRow());
    }

    @Test
    public void test_getEnd() {
        final Position p1 = new Position(0,0) ;
        final Position p2 = new Position(1,1) ;

        final Move move = new Move(p1, p2);

        assertEquals(p2.getRow(), move.getEnd().getRow());
        assertEquals(p2.getCell(), move.getEnd().getCell());
    }

    @Test
    public void test_Player() {
        final Position p1 = new Position(0,0) ;
        final Position p2 = new Position(1,1) ;

        final Move move = new Move(p1, p2);

        final Player player = new Player("david") ;

        move.setPlayer(player);

        assertEquals(player, move.getPlayer());
    }

    @Test
    public void test_Color() {
        final Position p1 = new Position(0,0) ;
        final Position p2 = new Position(1,1) ;

        final Move move = new Move(p1, p2);

        final CheckersGame.color color = CheckersGame.color.RED ;

        move.setPieceColor( color);

        assertEquals(color, move.getColor());
    }

    @Test
    public void test_Midpoint() {
        final Position p1 = new Position(0,0) ;
        final Position p2 = new Position(1,1) ;

        final Move move = new Move(p1, p2);

        assertEquals(p1, move.getStart());
        assertEquals(p1.getCell(), move.getMidpoint().getCell());
        assertEquals(p1.getRow(), move.getMidpoint().getRow());

        final Position p3 = new Position(2, 2) ;

        final Move move2 = new Move(p1, p3) ;

        assertEquals(p2.getCell(), move2.getMidpoint().getCell());
        assertEquals(p2.getRow(), move2.getMidpoint().getRow());
    }

    @Test
    public void test_SingleSpace() {
        final Position p1 = new Position(0,0) ;
        final Position p2 = new Position(1,1) ;

        final Move move = new Move(p1, p2);

        assertTrue(move.isSingleSpace());
        assertFalse(move.isJump());

        final Position p3 = new Position(0,0) ;
        final Position p4 = new Position(0,1) ;

        final Move move2 = new Move(p3, p4);

        assertFalse(move2.isSingleSpace());
        assertFalse(move2.isJump());
    }

    @Test
    public void test_JumpMove() {
        final Position p1 = new Position(0,0) ;
        final Position p3 = new Position(2, 2) ;

        final Move move = new Move(p1, p3) ;

        assertTrue(move.isJump());
        assertFalse(move.isSingleSpace());

        final Position p2 = new Position(0,0) ;
        final Position p4 = new Position(3, 3) ;

        final Move move2 = new Move(p2, p4) ;

        assertFalse(move2.isJump());
        assertFalse(move2.isSingleSpace());

        final Position p5 = new Position(0,1) ;
        final Position p6 = new Position(3, 3) ;

        final Move move3 = new Move(p5, p6) ;

        assertFalse(move3.isJump());
        assertFalse(move3.isSingleSpace());
    }

    @Test
    public void test_Valid() {
        final Position p1 = new Position(0,0) ;
        final Position p3 = new Position(2, 2) ;

        final Move move = new Move(p1, p3) ;

        assertFalse(move.isValid());
        final Player player = new Player("david") ;
        final CheckersGame.color color = CheckersGame.color.RED ;
        move.setPieceColor(color);
        assertFalse(move.isValid());
        move.setPlayer(player);

        assertTrue(move.isValid());

        final Position p2 = new Position(-2,-2) ;
        final Position p4 = new Position(-2, -2) ;

        final Move move2 = new Move(p2, p4) ;

        assertFalse(move2.isValid());
        final Player player2 = new Player("david") ;
        final CheckersGame.color color2 = CheckersGame.color.RED ;
        move2.setPieceColor(color2);
        assertFalse(move2.isValid());
        move2.setPlayer(player2);

        assertFalse(move2.isValid());

        final Position p5 = new Position(0,2) ;
        final Position p6 = new Position(0, 3) ;

        final Move move3 = new Move(p5, p6) ;

        assertFalse(move3.isValid());
        final Player player3 = new Player("david") ;
        final CheckersGame.color color3 = CheckersGame.color.RED ;
        move3.setPieceColor(color3);
        assertFalse(move3.isValid());
        move3.setPlayer(player3);

        assertTrue(move3.isValid());
    }
}
