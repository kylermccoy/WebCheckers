package com.webcheckers.model;

/**
 * representation of a position on the board
 * using numeric rows and columns
 */
public class Position {

    private int row ;
    private int cell ;

    public Position(int row, int cell){
        this.row = row ;
        this.cell = cell ;
    }

    public int getRow(){
        return  this.row ;
    }

    public int getCell(){
        return this.cell ;
    }

    public boolean isOnBoard(){
        return (row <= 7 && row >= 0) && (cell <= 7 && cell >= 0) ;
    }
}
