package io.github.sebkaminski16.tictactoe.logic;

public class CellNumberOutOfBoundsException extends RuntimeException {
    public CellNumberOutOfBoundsException(String message) {
        super(message);
    }
}
