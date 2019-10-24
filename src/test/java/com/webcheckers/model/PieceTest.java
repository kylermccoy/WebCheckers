/**
 * Unit Test for Piece Class
 * Author: Kyle McCoy
 */
package com.webcheckers.model;

import static  org.junit.jupiter.api.Assertions.* ;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-Tier")
public class PieceTest {

    @Test
    public void test_ColorCreation() {
        final Piece pieceRed = new Piece(true);
        final Piece pieceWhite = new Piece(false);

        // check color type values
        assertTrue(pieceRed.isRed()) ;
        assertFalse(pieceWhite.isRed());
    }

    @Test
    public void test_KingCreation() {
        final Piece pieceRed = new Piece(true);
        final Piece pieceWhite = new Piece(false);

        // check single values
        assertFalse(pieceRed.isKing());
        assertFalse(pieceWhite.isKing());

        // make King
        pieceRed.makeKing();
        pieceWhite.makeKing();

        // check king value
        assertTrue(pieceRed.isKing());
        assertTrue(pieceWhite.isKing());
    }

    @Test
    public void test_SamePieces() {
        final Piece pieceRed = new Piece(true);
        final Piece pieceWhite = new Piece(false);
        final Piece pieceRedTwo = new Piece(true);
        final Piece pieceWhiteTwo = new Piece(false);

        Space space = new Space(0,0) ;

        // check pieces are equal
        assertEquals(pieceRed, pieceRedTwo);
        assertEquals(pieceWhite, pieceWhiteTwo);
        assertNotEquals(pieceRed, pieceWhite);
        assertNotEquals(pieceRed, pieceWhiteTwo);
        assertNotEquals(pieceRedTwo, pieceWhite);
        assertNotEquals(pieceRedTwo, pieceWhiteTwo);

        assertFalse(pieceRed.equals(space));
        assertTrue(pieceRed.equals(pieceRedTwo));

        //make kings
        pieceRedTwo.makeKing();
        pieceWhiteTwo.makeKing();

        // check pieces are not equal
        assertNotEquals(pieceRed, pieceRedTwo);
        assertNotEquals(pieceWhite, pieceWhiteTwo);
        assertNotEquals(pieceRed, pieceWhite);
        assertNotEquals(pieceRedTwo,pieceWhiteTwo);
    }
}
