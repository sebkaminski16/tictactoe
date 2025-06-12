package io.github.sebkaminski16.tictactoe.logic;

public class PlayerNameDuplicateException extends RuntimeException {
    public PlayerNameDuplicateException(String message) {
        super(message);
    }
}
