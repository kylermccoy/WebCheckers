package com.webcheckers.model;

/**
 * Class for player and game logic
 * @ Brian Mirabito
 */
public class CheckersGame {

    // Players
    private Player redPlayer;
    private Player whitePlayer;

    // Active Color variable, an enum
    private color activeColor;

    // Active Mode
    private viewMode mode ;

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

    private enum color {
        RED, WHITE
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

        this.board = new BoardView() ;

        // Creates a unique GameID for each game created
        synchronized (BoardView.class){
            gamesCreated++;
            this.GameID = gamesCreated;
        }
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

    // public swappers

    /***
     * switches the current active color.
     */
    public void swapActiveColor(){
        if(this.activeColor.equals(color.RED)){
            this.activeColor = color.WHITE ;
        }else{
            this.activeColor = color.RED ;
        }
    }
}
