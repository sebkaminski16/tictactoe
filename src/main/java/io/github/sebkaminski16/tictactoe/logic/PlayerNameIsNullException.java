package io.github.sebkaminski16.tictactoe.logic;

public class PlayerNameIsNullException extends RuntimeException {
    public PlayerNameIsNullException(String message) {
        super(message);
    }
}
