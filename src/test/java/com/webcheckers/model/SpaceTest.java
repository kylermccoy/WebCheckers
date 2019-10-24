package com.webcheckers.model;

import static  org.junit.jupiter.api.Assertions.* ;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-Tier")
public class SpaceTest {

    @Test
    public void test_EmptySpace() {
        final Space space = new Space(0, 0) ;
        final Space spaceValid = new Space(0,1) ;

        // Tests checking for empty space results

        // Test if empty space piece will result to null
        assertNull(space.getPiece()); ;
        // Test if the empty space method works
        assertTrue(space.isOpen());
        // Test if the is occupied method works
        assertFalse(space.isOccupied());

        // add a red piece
        Piece piece = new Piece(true) ;
        space.placePiece(piece);
        spaceValid.placePiece(piece);

        // Tests checking for filled space results

        //Test if empty space will result to non null
        assertNull(space.getPiece());
        assertNotNull(spaceValid.getPiece());
        // Test if the empty space method works
        assertTrue(space.isOpen());
        assertFalse(spaceValid.isOpen());
        // Test if the is occupied method works
        assertFalse(space.isOccupied());
        assertTrue(spaceValid.isOccupied());

        // remove piece
        space.removePiece() ;
        spaceValid.removePiece() ;

        // Test if empty space piece will result to null
        assertNull(space.getPiece()); ;
        assertNull(spaceValid.getPiece());
        // Test if the empty space method works
        assertTrue(space.isOpen());
        assertTrue(spaceValid.isOpen());
        // Test if the is occupied method works
        assertFalse(space.isOccupied());
        assertFalse(spaceValid.isOccupied());
    }

    @Test
    public void test_NonEmptySpace() {
        final Space space = new Space(0,0) ;
        final Space spaceValid = new Space(0,1) ;

        //place piece in space
        Piece piece = new Piece(true) ;
        space.placePiece(piece);
        spaceValid.placePiece(piece);

        // Tests checking for filled space results

        // Test if filled space will result to non null
        assertNull(space.getPiece());
        assertNotNull(spaceValid.getPiece());
        // Test if piece in space is same piece
        assertSame(null, space.getPiece());
        assertEquals(null, space.getPiece());
        assertSame(piece, spaceValid.getPiece());
        assertEquals(piece, spaceValid.getPiece());
    }

    @Test
    public void test_CorrectColor(){
        final Space spaceDark = new Space(0,1) ;
        final Space spaceLight = new Space(0,0) ;

        // Test checking for correct space color
        assertTrue(spaceDark.isDark());
        assertFalse(spaceLight.isDark());
    }

    @Test
    public void test_ValidAndStatus(){
        final Space spaceDark = new Space(0,1) ;
        final Space spaceLight = new Space(0,0) ;

        // Test if spaces are valid
        assertFalse(spaceLight.isValid());
        assertTrue(spaceDark.isValid());

        // add piece
        Piece piece = new Piece(true) ;
        spaceDark.placePiece(piece);
        spaceLight.placePiece(piece);

        // Test if spaces are valid
        assertFalse(spaceLight.isValid());
        assertFalse(spaceDark.isValid());

        // Test status space will red piece
        assertSame("Invalid", spaceLight.getStatus());
        assertSame("Occupied", spaceDark.getStatus());

        // remove pieces
        spaceDark.removePiece() ;
        spaceLight.removePiece() ;

        // Test status empty space
        assertSame("Open", spaceDark.getStatus());
        assertSame("Invalid", spaceLight.getStatus());
    }

}
