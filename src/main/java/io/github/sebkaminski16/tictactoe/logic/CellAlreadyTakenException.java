package io.github.sebkaminski16.tictactoe.logic;

public class CellAlreadyTakenException extends RuntimeException {
    public CellAlreadyTakenException(String message) {
        super(message);
    }
}
