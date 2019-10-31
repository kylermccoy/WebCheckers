/***
 * Author: Luyang Lin
 */
package com.webcheckers.model;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class TestBoarView {
    private BoardView boardView;

    /*Initialize mocked objects to test with*/
    @BeforeEach
    public void initializeBoardView(){
        boardView = new BoardView();
    }

    /*Test to make sure that makeRows() actually makes rows */
    @Test
    public void test_makeRows(){
        ArrayList<Row> test_rows = boardView.makeRows();
        assertNotNull(test_rows);
    }

    @Test//Test to check the validity of the fillBoard()
    public void test_fillBoard(){
        ArrayList<Row> test_board = boardView.fillBoard();
        assertNotNull(test_board);
        for(Row row: test_board){
            for (Space space: row.getSpaces()){
                if((space.isValid() && row.getIndex() >= 0) && (row.getIndex()<=2)){
                    assertFalse(space.getPiece().isRed());
                }
                else if ((space.isValid()) && (row.getIndex() >= 5) && (row.getIndex() <= 7)) {
                    assertTrue(space.getPiece().isRed());
                }
            }
        }
    }


    /***
     * Method to test and see if rotateBoard returns a valid inverted board. Basically the
     * same as test_fillBoard(), but the boolean states are inverted
     */
    @Test
    public void test_rotateBoard(){
        ArrayList<Row> test_board = boardView.rotateBoard(boardView.getRedBoard());
        assertNotNull(test_board);
        for(Row row: test_board){
            for (Space space: row.getSpaces()){
                if((space.isValid() && row.getIndex() >= 0) && (row.getIndex()<=2)){
                    assertTrue(space.getPiece().isRed());
                }
                else if ((space.isValid()) && (row.getIndex() >= 5) && (row.getIndex() <= 7)) {
                    assertFalse(space.getPiece().isRed());
                }
            }
        }

    }

    /***
     * Method to test iterator()
     */
    @Test
    public void test_iterator_noParam(){
        Iterator test_iterator = boardView.iterator();
    }

    /***
     * Method to test iterator(boolean inverted)
     */
    @Test
    public void test_iterator_wParam(){
        Iterator<Row> Red = boardView.getRedBoard().iterator();
        Iterator<Row> White = boardView.getWhiteBoard().iterator();
        assertSame(White.getClass(),boardView.iterator(true).getClass());
        assertSame(Red.getClass(),boardView.iterator(false).getClass());
    }

}
