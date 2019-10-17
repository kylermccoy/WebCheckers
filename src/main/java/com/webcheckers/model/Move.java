package com.webcheckers.model;

/**
 * Representation of a move on the game board
 * from the starting space to the finishing space
 */
public class Move {

    public Position start;
    public Position end ;

    public Move(Position start, Position end){
        this.start = start ;
        this.end = end ;
    }
}
