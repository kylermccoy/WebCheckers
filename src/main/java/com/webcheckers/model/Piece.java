package com.webcheckers.model;

/**
 * Class that represents the Checkers Piece
 * @ Kyle McCoy
 */
public class Piece {
    /**
     * enum type represents the regular checker or the king checker
     */
    public enum type{
        SINGLE, KING
    }

    /**
     * enum color represents the color for the team
     */
    public enum color{
        RED, WHITE
    }
    // enum that represents the piece's type
    private type pieceType ;
    // enum that represents the piece's team color
    private color pieceColor ;

    /**
     * constructor for the piece
     * @param redWhite if true the piece is red else the piece is white
     */
    public Piece(boolean redWhite) {
        this.pieceType = Piece.type.SINGLE ;
        if(redWhite){
            this.pieceColor = color.RED ;
        }else{
            this.pieceColor = color.WHITE ;
        }
    }

    /**
     * returns the enum type of the piece
     * @return
     */
    public type getType(){
        return this.pieceType ;
    }

    /**
     * returns the enum color of the piece
     * @return
     */
    public color getColor(){
        return this.pieceColor ;
    }
}
