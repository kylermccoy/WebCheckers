/**
 * Unit tests for Position Class
 * Kyle McCoy
 */
package com.webcheckers.model;

import static  org.junit.jupiter.api.Assertions.* ;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-Tier")
public class TestPosition {

    @Test
    public void test_getRow() {
        final Position p1 = new Position(0,0) ;
        assertEquals(0, p1.getRow());
    }

    @Test
    public void test_getCell() {
        final Position p1 = new Position(0,0) ;
        assertEquals(0, p1.getCell());
    }

    @Test
    public void test_onBoard() {
        final Position p1 = new Position(0,0) ;
        assertTrue(p1.isOnBoard());

        final Position p2 = new Position(8, 8) ;
        assertFalse(p2.isOnBoard());

        final Position p3 = new Position(0, 8) ;
        assertFalse(p3.isOnBoard());

        final Position p4 = new Position(8, 0) ;
        assertFalse(p4.isOnBoard());

        final Position p5 = new Position(0, -8) ;
        assertFalse(p5.isOnBoard());

        final Position p6 = new Position(-8, 0) ;
        assertFalse(p6.isOnBoard());

        final Position p7 = new Position(-8, -8) ;
        assertFalse(p7.isOnBoard());
    }

    @Test
    public void test_absDiff() {
        final Position p1 = new Position(0 ,0) ;
        final Position p2 = new Position(2, 2) ;

        assertEquals(2, Position.absoluteDifference(p1, p2).getCell());
        assertEquals(2, Position.absoluteDifference(p1, p2).getRow());
    }
}
