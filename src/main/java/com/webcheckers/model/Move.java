package com.webcheckers.model;

/**
 * Representation of a move on the game board
 * from the starting space to the finishing space
 */
public class Move {

    private Position start;
    private Position end ;

    private CheckersGame.color color ;
    private Player player ;

    public Move(Position start, Position end){
        this.start = start ;
        this.end = end ;
    }

    public Position getStart(){
        return this.start ;
    }

    public Position getEnd(){
        return this.end ;
    }

    public Player getPlayer(){
        return this.player ;
    }

    public CheckersGame.color getColor(){
        return this.color ;
    }

    public void setPieceColor(CheckersGame.color color){
        this.color = color ;
    }

    public void setPlayer(Player player){
        this.player = player ;
    }

    public Position getMidpoint(){
        if (!isSingleSpace()){
            int midRow = getStart().getRow() + (getEnd().getRow() - getStart().getRow()) / 2 ;
            int midCell = getStart().getCell() + (getEnd().getCell() - getStart().getCell()) / 2 ;

            return new Position(midRow, midCell) ;
        }else{
            return start ;
        }
    }

    /**
     * uses math to find the difference in position to find if its a single space move
     * @return
     */
    public boolean isSingleSpace(){
        int deltaX = Math.abs(start.getCell() - end.getCell()) ;
        int deltaY = Math.abs(start.getRow() - end.getRow()) ;

        return (deltaX==1 && deltaY==1) ;
    }

    public boolean isValid(){
        return this.start.isOnBoard() && this.end.isOnBoard() && this.player != null && this.color != null ;
    }

    public boolean isJump(){
        Position diff = Position.absoluteDifference(end, start) ;
        return diff.getCell() == 2 && diff.getRow() == 2 ;
    }

    public String toString(){
        int startCell = start.getCell() ;
        int startRow = start.getRow() ;
        int endCell = end.getCell() ;
        int endRow = end.getRow() ;

        return String.format("<%d,%d> to <%d,%d>",startRow,startCell,endRow,endCell) ;
    }
}
