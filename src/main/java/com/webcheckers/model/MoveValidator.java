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


}
