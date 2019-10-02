package com.webcheckers.appl;

import com.webcheckers.model.Player;

import java.util.HashMap;
import java.util.Map;

public class GameCenter {

    private Map<String, Player> players;
    private PlayerLobby playerLobby;

    public GameCenter(PlayerLobby playerLobby) {
        players = new HashMap<>() ;
        this.playerLobby = playerLobby;
    }
}
