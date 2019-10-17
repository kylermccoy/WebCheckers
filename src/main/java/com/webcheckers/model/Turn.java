package com.webcheckers.model;

import java.util.Stack;
import java.util.logging.Logger;

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
    private Stack<CheckersGame> pendingMoves ;

    /**
     * Parameterized constructor
     * A turn is identified by the player, the board they are playing on and their color
     * @param board
     */
    public Turn(CheckersGame board){
        LOG.info(String.format("I am a new turn for the [%s] player", board.getActiveColor().toString())) ;

        this.board = board;
        this.playerColor = board.getActiveColor() ;
        if(this.playerColor.equals(CheckersGame.color.RED)){
            this.player = board.getRedPlayer();
        }else{
            this.player = board.getWhitePlayer() ;
        }

        this.pendingMoves = new Stack<>() ;
        this.state = State.EMPTY_TURN ;

        LOG.fine(String.format("Turn initialized in [%s] state", this.state)) ;
    }

}
