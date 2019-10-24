package com.webcheckers.model;

/**
 * Class that represents the Checkers Piece
 * @ Kyle McCoy
 */
public class Piece {
    /**
     * enum type represents the regular checker or the king checker
     */
    private enum type{
        SINGLE, KING
    }

    /**
     * enum color represents the color for the team
     */
    private enum color{
        RED, WHITE
    }
    // enum that represents the piece's type
    private type pieceType ;
    // enum that represents the piece's team color
    private color pieceColor ;

    /**
     * constructor for the piece
     * @param isRed if true the piece is red else the piece is white
     */
    public Piece(boolean isRed) {
        this.pieceType = Piece.type.SINGLE ;
        if(isRed){
            this.pieceColor = color.RED ;
        }else{
            this.pieceColor = color.WHITE ;
        }
    }

    /**
     * returns the enum type of the piece
     * @return piece type
     */
    public type getType(){
        return this.pieceType ;
    }

    /**
     * returns the enum color of the piece
     * @return piece color
     */
    public color getColor(){
        return this.pieceColor ;
    }

    public boolean isRed(){
        return this.pieceColor.equals(color.RED) ;
    }

    public boolean isKing(){
        return this.pieceType == type.KING ;
    }

    public void makeKing(){
        this.pieceType = type.KING ;
    }

    @Override
    public boolean equals(Object object){
        if (object instanceof Piece){
            Piece temp = (Piece)(object) ;
            return this.pieceColor==temp.pieceColor && this.pieceType==temp.pieceType ;
        }
        return false ;
    }
}
