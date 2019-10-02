package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class that represents the BoardView for checkers
 * @ Kyle McCoy
 */
public class BoardView implements Iterable {

    // ArrayList of blank rows
    private ArrayList<Row> rowsBlank ;

    // ArrayList of filled rows from white view
    private ArrayList<Row> rowsWhite ;

    // ArrayList of filled rows from red view
    private ArrayList<Row> rowsRed ;

    //
    /**
     * Constructor for the BoardView
     */
    public BoardView(){
        ArrayList<Row> rows = new ArrayList<>();
        for(int i = 0; i < 8; i++){
            rows.add(new Row(i)) ;
        }
        this.rowsBlank = rows ;
        this.rowsRed = fillBoard(false) ;
        this.rowsWhite = fillBoard(true) ;
    }

    /**
     * Places pieces on the board red on the bottom white on top
     */
    public ArrayList<Row> fillBoard(boolean redTop){
        ArrayList<Row> filled_copy = this.rowsBlank ;
        for(Row row: filled_copy ){
            for(Space space: row.getSpaces()){
                if(!redTop) {
                    if ((space.isValid()) && (row.getIndex() >= 0) && (row.getIndex() <= 2)) {
                        space.placePiece(new Piece(false));
                    } else if ((space.isValid()) && (row.getIndex() >= 5) && (row.getIndex() <= 7)) {
                        space.placePiece(new Piece(true));
                    }
                }else{
                    if ((space.isValid()) && (row.getIndex() >= 0) && (row.getIndex() <= 2)) {
                        space.placePiece(new Piece(true));
                    } else if ((space.isValid()) && (row.getIndex() >= 5) && (row.getIndex() <= 7)) {
                        space.placePiece(new Piece(false));
                    }
                }
            }
        }
        return filled_copy;
    }

    /**
     * iterator for the red perspective of the board
     * @return iterable rows
     */
    public Iterator<Row> iterator() {
        return this.rowsRed.iterator() ;
    }
}
