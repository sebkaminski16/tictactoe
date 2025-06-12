package io.github.sebkaminski16.tictactoe.logic;

public class GameAlreadyFinishedException extends RuntimeException {
    public GameAlreadyFinishedException(String message) {
        super(message);
    }
}
