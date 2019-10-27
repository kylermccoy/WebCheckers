package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class that represents the BoardView for checkers, which handles organizing the pieces on the board.
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
        this.rowsBlank = makeRows() ;
        this.rowsRed = fillBoard() ;
        this.rowsWhite = rotateBoard(this.rowsRed) ;
    }

    public ArrayList<Row> getRedBoard() {
        return this.rowsRed;
    }

    public ArrayList<Row> getWhiteBoard() {
        return this.rowsWhite;
    }

    /**
     * makes blank rows of the game board, used to make red and white clones
     * @return array list of rows
     */
    public ArrayList<Row> makeRows(){
        ArrayList<Row> rows = new ArrayList<>();
        for(int i = 0; i < 8; i++){
            rows.add(new Row(i)) ;
        }
        return rows ;
    }
    /**
     * Places pieces on the board red on the bottom white on top
     */
    public ArrayList<Row> fillBoard(){
        ArrayList<Row> board = makeRows() ;
        for(Row row: board ){
            for(Space space: row.getSpaces()){
                if ((space.isValid()) && (row.getIndex() >= 0) && (row.getIndex() <= 2)) {
                    space.placePiece(new Piece(false));
                } else if ((space.isValid()) && (row.getIndex() >= 5) && (row.getIndex() <= 7)) {
                    space.placePiece(new Piece(true));
                }
            }
        }
        return board;
    }

    public void update(ArrayList<Row> update){
        this.rowsRed = update ;
        this.rowsWhite = rotateBoard(rowsRed) ;
    }

    /**
     * method that rotates the board cells for the perspective of the white player
     * @param redBoard red board perspective
     * @return white board perspective
     */
    public ArrayList<Row> rotateBoard(ArrayList<Row> redBoard){
        ArrayList<Row> copy = makeRows() ;
        int redstartrow = 7 ;
        int redstartcolumn = 7 ;
        for(int whitestartrow = 0; whitestartrow < 8; whitestartrow++){
            for(int whitestartcolumn = 0;whitestartcolumn < 8; whitestartcolumn++){
                copy.get(whitestartrow).getSpaces().get(whitestartcolumn).placePiece(redBoard.get(redstartrow).getSpaces().get(redstartcolumn).getPiece()) ;
                redstartcolumn-- ;
            }
            redstartcolumn = 7 ;
            redstartrow-- ;
        }
        return copy ;
    }

    /**
     * Command line printer that displays how the game board appears in each version
     */
    public void boardPrint(){
        Piece red = new Piece(true) ;
        String board = "" ;
        // CHECK BLANK BOARD
        System.out.println("\nBLANK BOARD\n");
        for(Row row: this.rowsBlank){
            for(Space space: row.getSpaces()){
                if(space.getPiece()==null){
                    if(space.isDark()){
                        board = board + "D" ;
                    }else{
                        board = board + "L" ;
                    }
                }else{
                    if(space.getPiece().getColor()==red.getColor()){
                        board = board + "R" ;
                    }else{
                        board = board + "W" ;
                    }
                }
                board = board + " " ;
            }
            board = board + "\n" ;
        }
        System.out.println(board);
        board = "" ;
        // CHECK RED BOARD
        System.out.println("\nRED BOARD\n");
        for(Row row: this.rowsRed){
            for(Space space: row.getSpaces()){
                if(space.getPiece()==null){
                    if(space.isDark()){
                        board = board + "D" ;
                    }else{
                        board = board + "L" ;
                    }
                }else{
                    if(space.getPiece().getColor()==red.getColor()){
                        board = board + "R" ;
                    }else{
                        board = board + "W" ;
                    }
                }
                board = board + " " ;
            }
            board = board + "\n" ;
        }
        System.out.println(board);
        board = "" ;
        //CHECK WHITE BOARD
        System.out.println("\nWHITE BOARD\n");
        for(Row row: this.rowsWhite){
            for(Space space: row.getSpaces()){
                if(space.getPiece()==null){
                    if(space.isDark()){
                        board = board + "D" ;
                    }else{
                        board = board + "L" ;
                    }
                }else{
                    if(space.getPiece().getColor()==red.getColor()){
                        board = board + "R" ;
                    }else{
                        board = board + "W" ;
                    }
                }
                board = board + " " ;
            }
            board = board + "\n" ;
        }
        System.out.println(board);
    }

    /**
     * Stand-in method to satisfy mission requirements, which defines such method named iterator
     * @return nothing
     */
    public Iterator<Row> iterator() {
        return null;

    }

    /**
     * Actual Iterator method to give the board either inverted or not inverted, based on the parameter
     * @param inverted whether or not to invert the board
     * @return either inverted or not inverted board
     */
    public Iterator<Row> iterator(boolean inverted) {
        if(inverted) {
            return this.rowsWhite.iterator();
        }
        return this.rowsRed.iterator();
    }

    /**
     * main function used for testing the methods
     * @param args
     */
    public static void main(String[] args) {
        BoardView boardView = new BoardView() ;
        boardView.boardPrint();
    }
}
