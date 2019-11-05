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

    public static Position absoluteDifference(Position end, Position start){
        int x = Math.abs(end.row - start.row) ;
        int y = Math.abs(end.cell - start.cell) ;

        return new Position(x,y) ;
    }
}
