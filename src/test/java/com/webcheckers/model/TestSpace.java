/***
 * Author: Luyang Lin
 */
package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestSpace {
    public Space dark_oddRow;
    public Space dark_evenRow;
    public Space light_oddRow;
    public Space light_evenRow;
    public Piece white_piece;



    @BeforeEach
    public void initializeTest(){
        this.dark_oddRow = new Space(1,2);
        this.dark_evenRow = new Space(2,1);
        this.light_evenRow = new Space(2,2);
        this.light_oddRow = new Space(1,1);
        this.white_piece = new Piece(false);
    }

    @Test
    public void test_isValid(){
        assertTrue(dark_evenRow.isValid());
        assertTrue(dark_oddRow.isValid());
        assertFalse(light_evenRow.isValid());
        assertFalse(light_oddRow.isValid());
    }


    @Test
    public void test_placePiece(){
        dark_evenRow.placePiece(white_piece);
        assertNotNull(dark_evenRow.getPiece());
    }

    @Test
    public void test_getPiece(){
        dark_evenRow.placePiece(white_piece);
        assertNotNull(dark_evenRow.getPiece());
        assertNull(dark_oddRow.getPiece());
    }

    @Test
    public void test_isOccupied(){
        dark_evenRow.placePiece(white_piece);
        assertFalse(dark_oddRow.isOccupied());
        assertTrue(dark_evenRow.isOccupied());
    }

    @Test
    public void test_equals(){
        Space equal_light_odd_row = new Space(1,1);
        assertTrue(equal_light_odd_row.equals(light_oddRow));
    }

}
