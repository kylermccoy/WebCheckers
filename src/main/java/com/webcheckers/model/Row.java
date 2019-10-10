package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class represents the eight rows on a checker board
 * @ Kyle McCoy
 */
public class Row implements Iterable {
    // the row number
    private int index ;
    // iterable value of the eight spaces
    private ArrayList<Space> spaces ;

    /**
     * Constructor for the rwo
     * @param index number of the row
     */
    public Row(int index) {
        this.index = index ;
        ArrayList<Space> spaces = new ArrayList<>() ;
        for(int i = 0; i < 8; i++){
            spaces.add(new Space(index, i)) ;
        }
        this.spaces = spaces;
    }
    // GETTERS

    /***
     * gets the Index of the row.
     * @return index of row (int)
     */
    public int getIndex(){
        return this.index ;
    }

    /***
     * gets an array list of all the spaces contained in this row
     * @return array list of spaces
     */
    public ArrayList<Space> getSpaces(){
        return this.spaces ;
    }

    /**
     * iterator of the rows, places spaces in eight columns of the row
     * @return iterable spaces
     */
    public Iterator<Space> iterator() {
        return this.spaces.iterator() ;
    }
}
