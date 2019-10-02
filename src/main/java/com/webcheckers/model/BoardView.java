package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class that represents the BoardView for checkers
 * @ Kyle McCoy
 */
public class BoardView implements Iterable {

    // ArrayList of rows
    private ArrayList<Row> rows ;

    /**
     * Constructor for the BoardView
     */
    public BoardView(){
        ArrayList<Row> rows = new ArrayList<>();
        for(int i = 0; i < 8; i++){
            rows.add(new Row(i)) ;
        }
        this.rows = rows ;
    }

    /**
     * Places pieces on the board red on the bottom white on top
     */
    public void fillBoard(){
        ArrayList<Row> filled_copy = this.rows ;
        for(Row row: filled_copy ){
            for(Space space: row.getSpaces()){
                if((space.isValid())&&(row.getIndex()>=0)&&(row.getIndex()<=2)){
                    space.placePiece(new Piece(false));
                } else if ((space.isValid()) && (row.getIndex() >= 5) && (row.getIndex() <= 7)) {
                    space.placePiece(new Piece(true));
                }
            }
        }
        this.rows = filled_copy;
    }

    /**
     * iterator for the board, places eight rows
     * @return iterable rows
     */
    public Iterator<Row> iterator() {
        return this.rows.iterator();
    }
}
