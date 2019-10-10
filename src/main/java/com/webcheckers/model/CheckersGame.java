package com.webcheckers.model;

/**
 * Class for player and game logic
 * @ Brian Mirabito
 */
public class CheckersGame {

    // Players
    private Player redPlayer;
    private Player whitePlayer;
    // Active Color variable
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

    public BoardView getBoard() { return this.board; }

    public int getGameID(){
        return GameID;
    }

    public Player getRedPlayer(){
        return redPlayer;
    }

    public Player getWhitePlayer(){
        return whitePlayer;
    }

    public color getActiveColor(){
        return activeColor;
    }

    public color getPlayerColor(Player player) {
        if (player.equals(whitePlayer)){
            return color.WHITE;
        } else {
            return color.RED;
        }
    }

    public Player getOpponent(Player player){
        color playerColor = getPlayerColor(player);
        if (playerColor == color.RED){
            return whitePlayer;
        } else {
            return redPlayer;
        }
    }

    // public swappers

    public void swapActiveColor(){
        if(this.activeColor.equals(color.RED)){
            this.activeColor = color.WHITE ;
        }else{
            this.activeColor = color.RED ;
        }
    }
}
