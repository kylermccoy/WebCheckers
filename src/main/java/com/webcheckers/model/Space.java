package com.webcheckers.model;

/**
 * Class that represents the spaces on the checker board
 * @ Kyle McCoy
 */
public class Space {
    // the column of the space
    private int cellIdx ;
    // the color of the space on the board
    private spaceColor color ;
    // the piece on that space, null if empty
    private Piece piece ;
    // enum represents the color of the spaces on the board
    public enum spaceColor{
        DARK, LIGHT
    }
    public enum State {
        OPEN, OCCUPIED, INVALID
    }
    private State state ;

    /**
     * Constructor for Space
     * @param row the row number to determine the space color
     * @param cellIdx the column number to determine space color
     */
    public Space(int row, int cellIdx){
        this.cellIdx = cellIdx ;
        this.piece = null ;
        this.state = State.OPEN ;
        if(row%2==0){
            if(cellIdx%2==0){
                this.color = spaceColor.LIGHT ;

            }else{
                this.color = spaceColor.DARK ;
            }
            this.state = State.OCCUPIED ;
        }else{
            if(cellIdx%2==0){
                this.color = spaceColor.DARK ;
            }else{
                this.color = spaceColor.LIGHT ;
            }
            this.state = State.OCCUPIED ;
        }
    }

    /**
     * is the space Dark and empty?
     * @return boolean
     */
    public boolean isValid(){
        return this.color.equals(spaceColor.DARK) && (this.piece==null) ;
    }

    /**
     * places a piece on the space
     * @param piece piece to be placed
     */
    public void placePiece(Piece piece) {
        if(this.isValid()){
            this.piece = piece ;
        }
    }

    public String getStatus(){
        if (this.isOccupied() && this.isDark()){
            return "Occupied" ;
        }else if (!this.isOccupied() && this.isDark()){
            return "Open" ;
        }else{
            return "Invalid" ;
        }
    }

    public boolean isOpen(){
        return this.piece == null ;
    }

    public Piece removePiece(){
        Piece temp = this.piece ;
        this.piece = null ;
        return temp ;
    }

    public boolean movePieceFrom(Space source) {
        if (source == null){
            return false ;
        }
        if (state != State.OPEN) {
            return false ;
        }
        if (source.getPiece() == null){
            return false ;
        }
        addPiece(source.getPiece()) ;
        source.removePiece() ;
        return true ;
    }

    public State addPiece(Piece piece){
        if (state == State.OPEN) {
            this.piece = piece ;
            state = State.OCCUPIED ;
            return state ;
        }else {
            return state ;
        }
    }

    /**
     * is the space tile Dark?
     * @return boolean
     */
    public boolean isDark(){
        return this.color==spaceColor.DARK ;
    }

    /**
     * get the space column number
     * @return int
     */
    public int getCellIdx() {
        return this.cellIdx ;
    }

    /**
     * get the piece occupying the space, null if empty
     * @return Piece if occupied, null if empty
     */
    public Piece getPiece(){
        return this.piece ;
    }

    public boolean isOccupied(){
        return this.getPiece() != null ;
    }

    @Override
    public boolean equals(Object object){
        if (object instanceof Space){
            Space temp = (Space)(object) ;
            return temp.cellIdx==this.cellIdx && temp.color==this.color && temp.piece==this.piece ;
        }
        return false ;
    }
}
