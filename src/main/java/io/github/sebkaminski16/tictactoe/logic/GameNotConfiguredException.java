package io.github.sebkaminski16.tictactoe.logic;

public class GameNotConfiguredException extends RuntimeException {
    public GameNotConfiguredException(String message) {
        super(message);
    }
}
