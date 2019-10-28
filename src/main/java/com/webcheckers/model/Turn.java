package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Stack;
import java.util.logging.Logger;
import com.webcheckers.util.Message ;

/**
 * Turn handles the lifecycle of a player's turn
 * from validating moves to backing up those moves
 * to submitting a list of moves and completing their turn
 */
public class Turn {
    private static final Logger LOG = Logger.getLogger(Turn.class.getName()) ;

    public static final String NO_MOVES_MSG = "You have not made any moves this turn." ;
    public static final String JUMP_MOVE_AVAILABLE_MSG = "You have a jump move available and must take it." ;
    public static final String JUMP_MOVE_PARTIAL_MSG = "You can continue your jump." ;
    public static final String JUMP_MOVE_ONLY_MSG = "You can only make jumps during a jump-move." ;
    public static final String SINGLE_MOVE_ONLY_MSG = "You can only make one move in a turn." ;

    public static final String VALID_SINGLE_MOVE_MSG = "Valid move! Submit your turn." ;
    public static final String VALID_JUMP_MOVE_MSG = "Valid jump move!" ;

    public static final String TURN_FINISHED_MSG = "Turn has been finalized." ;

    public enum State{
        EMPTY_TURN, SINGLE_MOVE, JUMP_MOVE, SUBMITTED
    }

    private CheckersGame board ;
    private Player player ;
    private CheckersGame.color playerColor ;
    private State state ;
    private Move lastValidMove ;
    private Stack<ArrayList<Row>> pendingMoves ;
    private ArrayList<Row> starting ;

    /**
     * Parameterized constructor
     * A turn is identified by the player, the board they are playing on and their color
     * @param board
     */
    public Turn(CheckersGame board, Player player, CheckersGame.color color){
        LOG.info(String.format("I am a new turn for the [%s] player", board.getActiveColor().toString())) ;

        this.board = board;
        this.starting = board.getBoard().getRedBoard() ;
        this.playerColor = color ;
        this.player = player ;

        this.pendingMoves = new Stack<>() ;
        this.state = State.EMPTY_TURN ;

        LOG.fine(String.format("Turn initialized in [%s] state", this.state)) ;
    }

    public Message validateMove(Move move){
        LOG.info(String.format("%s Player [%s] is validating move %s", playerColor, player.getName(), move.toString()));

        move.setPieceColor(playerColor) ;
        move.setPlayer(player) ;

        ArrayList<Row> boardView = getLatestBoard() ;

        LOG.finest("The board we are using for this validateMove()");
        LOG.finest(boardView.toString());

        boolean isMoveValid = false ;

        Message moveValidMsg = Message.error("Move is invalid.") ;

        //switch statements for each state
        switch (state) {
            case EMPTY_TURN:
                if (move.isSingleSpace() && MoveValidator.areJumpsAvailableForPlayer(boardView, playerColor)) {
                    moveValidMsg = Message.error(JUMP_MOVE_AVAILABLE_MSG) ;
                }else if (MoveValidator.validateMove(boardView, move)) {
                    String message = (move.isSingleSpace()) ? VALID_SINGLE_MOVE_MSG : VALID_JUMP_MOVE_MSG ;
                    moveValidMsg = Message.info(message) ;
                }
                break;
            case SINGLE_MOVE:
                moveValidMsg = Message.error(SINGLE_MOVE_ONLY_MSG) ;
                break;
            case JUMP_MOVE:
                if (move.isSingleSpace()){
                    moveValidMsg = Message.error(JUMP_MOVE_ONLY_MSG) ;
                }else if (MoveValidator.validateMove(boardView, move)){
                    moveValidMsg = Message.info(VALID_JUMP_MOVE_MSG) ;
                }
                break;
        }

        if (moveValidMsg.getType() == Message.Type.INFO){
            recordMove(move) ;
        }
        LOG.info(String.format("Move validated. Result [%s]", moveValidMsg.toString()));
        LOG.finest(String.format("%s Player [%s] has %d queued moves in [%s] state", playerColor, player.getName(), pendingMoves.size(), state));
        return moveValidMsg ;
    }

    public boolean recordMove(Move move){
        LOG.info(String.format("%s Player [%s] turn - executing move %s", playerColor, player.getName(), move.toString()));
        ArrayList<Row> matrix = getLatestBoard() ;
        if (move.isJump()){
            Position position = move.getMidpoint() ;
            int cellMid = position.getCell() ;
            int rowMid = position.getRow() ;
            Space spaceMid = matrix.get(rowMid).getSpaces().get(cellMid) ;
            spaceMid.removePiece() ;
        }
        Position positionStart = move.getStart() ;
        int cellStart = positionStart.getCell() ;
        int rowStart = positionStart.getRow() ;
        Space spaceStart = matrix.get(rowStart).getSpaces().get(cellStart) ;
        Position positionEnd = move.getEnd() ;
        int cellEnd = positionEnd.getCell() ;
        int rowEnd = positionEnd.getRow() ;
        Space spaceEnd = matrix.get(rowEnd).getSpaces().get(cellEnd) ;

        spaceEnd.movePieceFrom(spaceStart) ;
        LOG.finest("Move successfully made on board");

        pendingMoves.push(matrix) ;
        lastValidMove = move ;
        setStateAfterMove(move) ;
        return true ;
    }

    public void setStateAfterMove(Move move){
        if (move.isSingleSpace()) {
            state = State.SINGLE_MOVE ;
        }else if (move.isJump()){
            state = State.JUMP_MOVE ;
        }
    }

    public boolean backUpMove(){
        if (!pendingMoves.isEmpty()){
            pendingMoves.pop() ;
            LOG.info(String.format("Removing last move from %s's history", player.getName()));
            if (pendingMoves.isEmpty()){
                state = State.EMPTY_TURN ;
                LOG.finest(String.format("%s has reversed all planned moves", player.getName()));
            }
            return true ;
        }
        return false ;
    }

    public Message isFinalized(){
        Message finalizedMessage = Message.info(TURN_FINISHED_MSG) ;
        switch (state) {
            case SINGLE_MOVE:
                return finalizedMessage ;
            case JUMP_MOVE:
                if (MoveValidator.canContinueJump(getLatestBoard(), lastValidMove.getEnd(), playerColor)) {
                    return Message.error(JUMP_MOVE_PARTIAL_MSG) ;
                }else {
                    return finalizedMessage ;
                }
            default:
                return Message.error(NO_MOVES_MSG) ;
        }
    }

    public ArrayList<Row> getLatestBoard(){
        return (pendingMoves.empty()) ? starting : pendingMoves.peek() ;
    }

    public Player getPlayer(){
        return this.player ;
    }
}
