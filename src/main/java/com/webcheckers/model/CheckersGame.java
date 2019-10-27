package com.webcheckers.model;

import java.util.ArrayList;

/**
 * Class for player and game logic
 * @ Brian Mirabito
 */
public class CheckersGame {

    protected enum State {
        IN_PLAY, WON, RESIGNED
    }

    // Players
    private Player redPlayer;
    private Player whitePlayer;
    private Player winner ;
    private Player loser ;

    // Active State and Color variable, an enum
    private color activeColor;
    private State state ;

    // Active Mode
    private viewMode mode ;

    // Active Turn
    private Turn activeTurn ;

    // GameID variables
    private int GameID;
    private int gamesCreated;

    //BoardView
    private BoardView board ;

    // Inverted BoardView
    private BoardView invertedBoard;

    private enum viewMode {
        PLAY, SPECTATOR, REPLAY
    }

    public enum color {
        RED, WHITE ;

        public boolean isRed(){
            return this.equals(color.RED) ;
        }
    }

    /***
     * Constructor to create a new CheckersGame. Each game has a unique ID, which allows multiple games to be played
     * at the same time.
     * @param playerOne Player 1, regular board view
     * @param playerTwo Player 2, inverted board view
     */
    public CheckersGame (Player playerOne, Player playerTwo) {
        this.redPlayer = playerOne;
        this.whitePlayer = playerTwo;
        this.activeColor = color.RED;
        this.mode = viewMode.PLAY ;
        this.state = State.IN_PLAY ;
        this.loser = null ;
        this.winner = null ;

        this.board = new BoardView() ;

        // Creates a unique GameID for each game created
        synchronized (BoardView.class){
            gamesCreated++;
            this.GameID = gamesCreated;
        }

        this.activeTurn = new Turn(this) ;
    }

    //
    // Public Getters
    //

    /***
     * Getter for the board
     * @return BoardView
     */
    public BoardView getBoard() { return this.board; }

    /***
     * Gets the unique Game ID for the CheckersGame object
     * @return
     */
    public int getGameID(){
        return GameID;
    }

    /***
     * Gets the player who controls the red pieces
     * @return red player
     */
    public Player getRedPlayer(){
        return redPlayer;
    }

    /***
     * Gets the player who controls the white pieces
     * @return white player
     */
    public Player getWhitePlayer(){
        return whitePlayer;
    }

    /***
     * Gets the active color
     * @return active color
     */
    public color getActiveColor(){
        return activeColor;
    }

    /***
     * Gets the enum representation of the player's piece color
     * @param player player
     * @return enum
     */
    public color getPlayerColor(Player player) {
        if (player.equals(whitePlayer)){
            return color.WHITE;
        } else {
            return color.RED;
        }
    }

    /***
     * Gets the opponent of the player passed in as parameter(aka the other palyer)
     * @param player player
     * @return the opponent of player
     */
    public Player getOpponent(Player player){
        color playerColor = getPlayerColor(player);
        if (playerColor == color.RED){
            return whitePlayer;
        } else {
            return redPlayer;
        }
    }

    public Player getPlayerActive(){
        if (activeTurn == null){
            return winner ;
        }
        return this.activeTurn.getPlayer() ;
    }

    public void changeActivePlayer() {
        Player activePlayer = activeTurn.getPlayer() ;
        color activePlayerColor = getPlayerColor(activePlayer) ;
        Player nextPlayer ;
        color nextPlayerColor ;

        if (activePlayer.equals(redPlayer)){
            nextPlayer = whitePlayer ;
            nextPlayerColor = color.WHITE ;
        } else {
            nextPlayer = redPlayer ;
            nextPlayerColor = color.RED ;
        }

        makeKings() ;

        boolean nextPlayerHasPieces = MoveValidator.playerHasPieces(board.getRedBoard(), nextPlayerColor) ;
        boolean nextPlayerHasMoves = MoveValidator.areMovesAvailableForPlayer(board, nextPlayer, nextPlayerColor) ;

        boolean isPlayerOutOfMoves = !MoveValidator.areMovesAvailableForPlayer(board, activePlayer, activePlayerColor) ;

        if (!nextPlayerHasPieces){
            recordEndGame(activePlayer, nextPlayer) ;
        }else if (nextPlayerHasPieces && isPlayerOutOfMoves){
            recordEndGame(nextPlayer, activePlayer) ;
        }else if (nextPlayerHasMoves && nextPlayerHasPieces){
            activeColor = nextPlayerColor ;
            activeTurn = new Turn(this  ) ;
        }

    }

    public void recordEndGame(Player winner, Player loser){
        state = State.WON ;

        this.winner = winner ;
        this.loser = loser ;

        activeTurn = null ;
    }

    public void makeKings(){
        ArrayList<Row> redBoard = board.getRedBoard() ;

        for (int cell = 0; cell < 8; cell++){
            if (redBoard.get(0).getSpaces().get(cell).isOccupied() && redBoard.get(0).getSpaces().get(cell).getPiece().isRed()){
                redBoard.get(0).getSpaces().get(cell).getPiece().makeKing();
            }
        }

        for (int cell = 0; cell < 8; cell++){
            if (redBoard.get(7).getSpaces().get(cell).isOccupied() && !redBoard.get(7).getSpaces().get(cell).getPiece().isRed()){
                redBoard.get(7).getSpaces().get(cell).getPiece().makeKing();
            }
        }
    }

    public Message submitTurn(Player player){
        if (player.equals(getPlayerActive())){
            Message finalizedMessage = getTurn().isFinalized() ;
            if (finalizedMessage.getType() == Message.MessageType.info) {
                board.update(getTurn().getLatestBoard()) ;
                changeActivePlayer();
            }
            return finalizedMessage ;
        }else {
            return new Message("It is not your turn.", Message.MessageType.error) ;
        }
    }

    public boolean isResigned(){
        return state == State.RESIGNED ;
    }

    public boolean isWon(){
        return state == State.WON ;
    }

    public Turn getTurn(){
        return activeTurn ;
    }

    public Player getWinner(){
        return winner ;
    }

    public Player getLoser(){
        return loser ;
    }
}
