package com.webcheckers.model;

import com.google.gson.Gson;

/**
 * Message class for communication in the model
 */
public class Message {
    // public enum for type of message
    public enum MessageType{
        info, error
    }

    private  MessageType type ;
    private String message ;

    public Message(String message, MessageType type){
        this.message = message ;
        this.type = type ;
    }

    public String getMessage(){
        return this.message ;
    }

    public MessageType getType(){
        return this.type ;
    }

    public String toJson(){
        return (new Gson()).toJson(this) ;
    }

    public String toString(){
        return String.format("[%s] %s", type, message) ;
    }
}
