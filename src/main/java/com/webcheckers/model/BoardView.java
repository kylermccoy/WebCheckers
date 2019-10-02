package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class that represents the BoardView for checkers
 * @ Kyle McCoy
 */
public class BoardView implements Iterable {

    // iterable value of the eight rows
    private Iterator<Row> rows ;

    /**
     * Constructor for the BoardView
     */
    public BoardView(){
        this.rows = iterator() ;
    }
    /**
     * iterator for the board, places eight rows
     * @return iterable rows
     */
    public Iterator<Row> iterator() {
        ArrayList<Row> rows = new ArrayList<>();
        for(int i = 0; i < 8; i++){
            rows.add(new Row(i)) ;
        }
        return rows.iterator() ;
    }
}
