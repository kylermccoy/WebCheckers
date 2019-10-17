package com.webcheckers.model;

import java.util.ArrayList;
import java.util.logging.Logger;

public class MoveValidator {

    private  static final Logger LOG = Logger.getLogger(MoveValidator.class.getName()) ;
    private  static final int JUMP_DIFFERENCE = 2 ;

    public static boolean validateMove(ArrayList<Row> boardView, Move move){

        LOG.fine(String.format("Validating move for Player [%s]", move.getPlayer().getName()));

        logMoveCoordinates(move) ;

        LOG.finest(boardView.toString()) ;
        logMove(boardView, move) ;

        boolean isMoveValidOnBoard = move.isValid() &&
                isMoveInRightDirection(boardView, move) &&
                isEndSpaceOpen(boardView, move) &&
                (move.isSingleSpace() || isMoveJumpingAPiece(boardView,move)) &&
                areWeMovingMyPiece(boardView, move) ;

        LOG.fine(String.format("Move validity has been determined to be %s", isMoveValidOnBoard)) ;

        return isMoveValidOnBoard ;
    }

    public static boolean areMovesAvailableForPlayer(BoardView boardView, Player player, CheckersGame.color color){
        boolean movesLeft = false ;

        LOG.fine(String.format("Determining if %s player has any moves left.", color));

        ArrayList<Row> board;
        if(color.equals(CheckersGame.color.RED)){
            board = boardView.getRedBoard() ;
        }else{
            board = boardView.getWhiteBoard() ;
        }
        boardWalk:
        {
            for (int y = 0; y < 8; y++) {
                for (int x = 0; x < 8; x++) {

                    if (board.get(y).getSpaces().get(x).isOccupied() && (color.isRed() && board.get(y).getSpaces().get(x).getPiece().isRed())) {

                        Position currentPosition = new Position(y, x);

                        Move checkMove;

                        for (int row = -2; row <= 2; row++) {
                            for (int col = -2; col <= 2; col++) {

                                int destRow = y + row;
                                int destCell = x + col;

                                LOG.info(String.format("Checking space <%d,%d>", destRow, destCell));

                                if (destRow < 0 || destCell < 0) {
                                    LOG.info("Row or Cell is less than zero.");
                                    continue;
                                }

                                if (destRow >= 8 || destCell >= 8) {
                                    LOG.info("Row or Cell is greater than the number of rows or cells.");
                                    continue;
                                }

                                Position place = new Position(destRow, destCell);
                                checkMove = new Move(currentPosition, place);
                                checkMove.setPlayer(player);
                                checkMove.setPieceColor(color);

                                if (validateMove(board, checkMove)) {
                                    LOG.finest(String.format("Found a valid move! %s", checkMove.toString()));
                                    movesLeft = true;
                                    break boardWalk;
                                }
                            }
                        }
                    }
                }
            }
        }
        String condition = (movesLeft) ? "has" : "does not have" ;
        LOG.fine(String.format("%s Player %s moves left", color, condition));
        return movesLeft ;
    }

    public static boolean playerHasPieces(ArrayList<Row> board, CheckersGame.color color){
        for(int i = 0; i <= 7; i++){
            for(int j = 0; j <= 7; j++){
                if (board.get(i).getSpaces().get(j).isOccupied() && (board.get(i).getSpaces().get(j).getPiece().isRed() && color.isRed())){
                    return true ;
                }
            }
        }
        return false ;
    }

    private static void logMoveCoordinates(Move move){
        LOG.finer(String.format("%s Player [%s] wants to move from %s", move.getColor(), move.getPlayer().getName(), move.toString()));
    }

    private static void logMove(ArrayList<Row> matrix, Move move){
        Space startSpace = matrix.get(move.getStart().getRow()).getSpaces().get(move.getStart().getCell()) ;
        Space endSpace = matrix.get(move.getEnd().getRow()).getSpaces().get(move.getEnd().getCell()) ;

        LOG.finest(String.format("Starting position state is [%s] by a %s Piece", startSpace.getStatus(), startSpace.getPiece().getColor()));
        LOG.finest(String.format("End position state is [%s]", endSpace.getStatus()));

        // is logging even worth it?!?!?!?
        LOG.finest(String.format("Validate move.isValid() - %s", move.isValid()));
        LOG.finest(String.format("Validate     └─ start.isOnBoard() -  %s", move.getStart().isOnBoard()));
        LOG.finest(String.format("Validate     └─ end.isOnBoard() -  %s", move.getEnd().isOnBoard()));
        LOG.finest(String.format("Validate     └─ isSingleSpace() -  %s", move.isSingleSpace()));
        LOG.finest(String.format("Validate     └─ isJump() -  %s", move.isJump()));
    }

    private static boolean isEndSpaceOpen(ArrayList<Row> board, Move move){
        Space endSpace = board.get(move.getEnd().getRow()).getSpaces().get(move.getEnd().getCell()) ;
        boolean conditionTruth = endSpace.isOpen() ;
        LOG.finest(String.format("Validate isEndSpaceOpen(): %s", conditionTruth));
        return conditionTruth ;
    }

    private static boolean isMoveInRightDirection(ArrayList<Row> matrix, Move move){
        Piece piece = matrix.get(move.getStart().getRow()).getSpaces().get(move.getStart().getCell()).getPiece() ;
        boolean conditionTruth = false ;

        if (piece.isKing()){
            conditionTruth = true ;
        }else{
            int startRow = move.getStart().getRow() ;
            int endRow = move.getEnd().getRow() ;
            if (move.getColor().isRed()){
                conditionTruth = (endRow < startRow) ;
            }else{
                conditionTruth = (endRow > startRow) ;
            }
        }

        LOG.finest(String.format("Validate isMoveInRightDirection(): %s", conditionTruth)) ;
        return conditionTruth ;
    }

    private static boolean isMoveJumpingAPiece(ArrayList<Row> matrix, Move move){
        boolean conditionTruth = false ;
        if (move.isJump()){
            Position position = move.getMidpoint() ;
            Space space = matrix.get(position.getRow()).getSpaces().get(position.getCell()) ;
            Piece piece = matrix.get(move.getStart().getRow()).getSpaces().get(move.getStart().getCell()).getPiece() ;
            if(space.isOccupied()){
                if (!(space.getPiece().isKing() && piece.isRed())){
                    conditionTruth = true ;
                }
            }
        }
        LOG.finest(String.format("Validate isMoveJumpingAPiece(): %s", conditionTruth));

        return conditionTruth ;
    }

    private static boolean areWeMovingMyPiece(ArrayList<Row> matrix, Move move){
        boolean conditionTruth = false ;

        Space start = matrix.get(move.getStart().getRow()).getSpaces().get(move.getStart().getCell()) ;
        conditionTruth = (start.getPiece().isRed() == move.getColor().isRed()) ;

        LOG.finest(String.format("Validate areWeMovingMyPiece(): %s", conditionTruth));
        return conditionTruth ;
    }

    public static boolean areJumpsAvailableForPlayer(ArrayList<Row> matrix, CheckersGame.color color){
        for (int row = 0; row < 8; row++){
            for (int col = 0; col < 8; col++){
                if (canJump(matrix, row, col, color)){
                    return true ;
                }
            }
        }
        return false ;
    }

    public static boolean canContinueJump(ArrayList<Row> board, Position position, CheckersGame.color color){
        boolean condition ;
        int row = position.getRow() ;
        int cell = position.getCell() ;
        condition = canJump(board, row, cell, color) ;
        LOG.fine(String.format("Can Multi-Jump: %b", condition));
        return condition ;
    }

    private static boolean canJump(ArrayList<Row> board, int row, int cell, CheckersGame.color color){
        Space current = board.get(row).getSpaces().get(cell) ;
        if (current.isOccupied() && (current.getPiece().isRed() && color.isRed())){
            Move testMove ;
            Position start = new Position(row, cell) ;
            if (cell + 2 < 8){
                if (row + 2 < 8){
                    testMove = new Move(start, new Position(row + JUMP_DIFFERENCE, cell + JUMP_DIFFERENCE)) ;
                    if (board.get(testMove.getStart().getRow()).getSpaces().get(testMove.getStart().getCell()).getPiece().isRed()){
                        testMove.setPieceColor(CheckersGame.color.RED);
                    }else{
                        testMove.setPieceColor(CheckersGame.color.WHITE);
                    }
                    if (canJumpValidation(board, testMove)){
                        return true ;
                    }
                }
                if (row - 2 >= 0){
                    testMove = new Move(start, new Position(row - JUMP_DIFFERENCE, cell + JUMP_DIFFERENCE)) ;
                    if (board.get(testMove.getStart().getRow()).getSpaces().get(testMove.getStart().getCell()).getPiece().isRed()){
                        testMove.setPieceColor(CheckersGame.color.RED);
                    }else{
                        testMove.setPieceColor(CheckersGame.color.WHITE);
                    }
                    if (canJumpValidation(board, testMove)){
                        return true ;
                    }
                }
            }
            if (cell - 2 >= 0){
                if (row + 2 < 8){
                    testMove = new Move(start, new Position(row + JUMP_DIFFERENCE, cell - JUMP_DIFFERENCE)) ;
                    if (board.get(testMove.getStart().getRow()).getSpaces().get(testMove.getStart().getCell()).getPiece().isRed()){
                        testMove.setPieceColor(CheckersGame.color.RED);
                    }else{
                        testMove.setPieceColor(CheckersGame.color.WHITE);
                    }
                    if (canJumpValidation(board, testMove)){
                        return true ;
                    }
                }
                if (row - 2 >= 0){
                    testMove = new Move(start, new Position(row - JUMP_DIFFERENCE, cell + JUMP_DIFFERENCE)) ;
                    if (board.get(testMove.getStart().getRow()).getSpaces().get(testMove.getStart().getCell()).getPiece().isRed()){
                        testMove.setPieceColor(CheckersGame.color.RED);
                    }else{
                        testMove.setPieceColor(CheckersGame.color.WHITE);
                    }
                    if (canJumpValidation(board, testMove)){
                        return true ;
                    }
                }
            }
        }
        return false ;
    }

    public static  boolean canJumpValidation(ArrayList<Row> board, Move move){
        return isMoveJumpingAPiece(board, move) && isEndSpaceOpen(board, move) && isMoveInRightDirection(board, move) ;
    }
}
