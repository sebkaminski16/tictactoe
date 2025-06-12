package io.github.sebkaminski16.tictactoe.logic;

public enum GameBoardSize {
    SMALL(3),
    MEDIUM(4),
    LARGE(5);

    private final int value;
    GameBoardSize(int value) {
        this.value = value;
    }
    public int getValue() {
        return this.value;
    }

}