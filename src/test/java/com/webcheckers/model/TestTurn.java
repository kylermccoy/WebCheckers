/**
 * Unit tests for Turn Class
 * Kyle McCoy
 */
package com.webcheckers.model;

import static  org.junit.jupiter.api.Assertions.* ;

import com.webcheckers.util.Message;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-Tier")
public class TestTurn {

    @Test
    public void test_isFinalized() {
        final Player player = new Player("david") ;
        final Player p2 = new Player("gary") ;
        final CheckersGame board = new CheckersGame(player, p2) ;
        final Turn turn = new Turn(board, player, CheckersGame.color.RED);

        assertEquals(Message.error(Turn.NO_MOVES_MSG).getText(), turn.isFinalized().getText());

        final Move move = new Move(new Position(0,0), new Position(1,1)) ;

        turn.setStateAfterMove(move);

        assertEquals(Message.info(Turn.TURN_FINISHED_MSG).getText(), turn.isFinalized().getText());
    }

    @Test
    public void test_setStateAfterMove() {
        final Player player = new Player("david") ;
        final Player p2 = new Player("gary") ;
        final CheckersGame board = new CheckersGame(player, p2) ;
        final Turn turn = new Turn(board, player, CheckersGame.color.RED);

        final Move move = new Move(new Position(0,0), new Position(2,2)) ;

        assertTrue(move.isJump());
        turn.setStateAfterMove(move);

        final Move move1 = new Move(new Position(0,0), new Position(3,3)) ;
        assertFalse(move1.isJump());
        turn.setStateAfterMove(move1);
    }

    @Test
    public void test_getLatestBoard() {
        final Player player = new Player("david") ;
        final Player p2 = new Player("gary") ;
        final CheckersGame board = new CheckersGame(player, p2) ;
        final Turn turn = new Turn(board, player, CheckersGame.color.WHITE);

        assertNotEquals(null, turn.getLatestBoard());

        final Move move = new Move(new Position(2,1), new Position(3,0)) ;

        turn.recordMove(move) ;

        assertNotEquals(null, turn.getLatestBoard());
    }

    @Test
    public void test_recordMove() {
        final Player player = new Player("david") ;
        final Player p2 = new Player("gary") ;
        final CheckersGame board = new CheckersGame(player, p2) ;
        final Turn turn = new Turn(board, player, CheckersGame.color.WHITE);

        assertNotEquals(null, turn.getLatestBoard());

        final Move move = new Move(new Position(0,0), new Position(2,2)) ;

        turn.recordMove(move) ;

        assertNotEquals(null, turn.getLatestBoard());
    }

    @Test
    public void test_backUpMove() {
        final Player player = new Player("david") ;
        final Player p2 = new Player("gary") ;
        final CheckersGame board = new CheckersGame(player, p2) ;
        final Turn turn = new Turn(board, player, CheckersGame.color.WHITE);

        final Move move = new Move(new Position(0,0), new Position(2,2)) ;

        turn.recordMove(move) ;

        final Move move1 = new Move(new Position(2,2), new Position(4,4)) ;

        turn.recordMove(move) ;

        turn.backUpMove() ;
    }

    @Test
    public void test_validateMove() {
        Player player = new Player("david") ;
        Player p2 = new Player("gary") ;
        CheckersGame board = new CheckersGame(player, p2) ;
        Turn turn = new Turn(board, player, CheckersGame.color.WHITE);

        Move move = new Move(new Position(0,0), new Position(1,1)) ;

        turn.setStateAfterMove(move);

        assertEquals(Message.error(Turn.SINGLE_MOVE_ONLY_MSG).getText(), turn.validateMove(move).getText());
    }
}
