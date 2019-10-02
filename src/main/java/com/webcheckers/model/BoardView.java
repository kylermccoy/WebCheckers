package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;
import com.webcheckers.model.Piece.*;

/**
 * Class that represents the BoardView for checkers
 * @ Kyle McCoy
 */
public class BoardView implements Iterable {

    // iterable value of the eight rows
    private Iterator<Row> rows ;
    // Players
    private Player redPlayer;
    private Player whitePlayer;
    // Active Color variable
    private color activeColor;
    // GameID variables
    private int GameID;
    private int gamesCreated;

    private enum color {
        RED, WHITE
    }

    /**
     * Constructor for the BoardView
     */
    public BoardView(boolean redPlayer, Player playerOne, Player playerTwo){
        this.redPlayer = playerOne;
        this.whitePlayer = playerTwo;
        this.activeColor = color.RED;

        ArrayList<Row> rows = new ArrayList<>();
        for(int i = 0; i < 8; i++){
            rows.add(new Row(i, redPlayer)) ;
        }
        this.rows = rows.iterator() ;

        // Creates a unique GameID for each game created
        synchronized (BoardView.class){
            gamesCreated++;
            GameID = gamesCreated;
        }
    }
    /**
     * iterator for the board, places eight rows
     * @return iterable rows
     */
    public Iterator<Row> iterator() {
        return this.rows ;
    }

    //
    // Public Getters
    //

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
}
