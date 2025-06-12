package io.github.sebkaminski16.tictactoe.presentation.consolescreen;

import io.github.sebkaminski16.tictactoe.logic.GameBoardSize;
import io.github.sebkaminski16.tictactoe.logic.Player;
import io.github.sebkaminski16.tictactoe.logic.Symbol;
import io.github.sebkaminski16.tictactoe.presentation.keyboardinput.KeyboardInput;

import java.util.List;

public class GameScreen {
    private final KeyboardInput keyboard;

    public GameScreen(KeyboardInput keyboardInput) {
        this.keyboard = keyboardInput;
    }

    public void showFramedMessage(String symbol, String message) {
        String frame = symbol.repeat(message.length() + 4);
        String messageFrame = symbol + " " + message + " " + symbol;

        System.out.println();

        System.out.println(frame);
        System.out.println(messageFrame);
        System.out.print(frame);

        System.out.println();
        System.out.println();
    }

    public int chooseGameMode() {
        System.out.println("Please select game mode");
        System.out.println("1. Human vs Human");
        System.out.println("2. Computer vs Human");
        int gameMode = 0;
        while (gameMode != 1 && gameMode != 2) {
            try {
                System.out.print("Mode: ");
                gameMode = this.keyboard.readIntegerValue();
                if (gameMode != 1 && gameMode != 2) {
                    System.out.println("Mode needs to be 1 or 2");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println();
        return gameMode;
    }

    public GameBoardSize chooseGameBoardSize() {
        GameBoardSize gameBoardSize = null;
        while(gameBoardSize == null) {
            try {
                System.out.println("Please choose the size of the game board");
                System.out.println("1. Small (3x3), 2. Medium (4x4), 3. Large (5x5)");
                int decision = this.keyboard.readIntegerValue();
                gameBoardSize = switch (decision) {
                    case 1 -> GameBoardSize.SMALL;
                    case 2 -> GameBoardSize.MEDIUM;
                    case 3 -> GameBoardSize.LARGE;
                    default -> null;
                };
            } catch (Exception e) {
                System.out.print(e.getMessage());
            }
        }
        System.out.println();
        return gameBoardSize;
    }

    public String[] choosePlayers(int gameMode) {
        if(gameMode != 1 && gameMode != 2) throw new IncorrectGameModeException("Incorrect game mode! Can only be 1 or 2!");
        String player1Name = null;
        String player2Name = null;
        if (gameMode == 1) {
            while ((player1Name == null || player2Name == null)) {
                try {
                    if (player1Name == null) {
                        System.out.print("First player name: ");
                        player1Name = this.keyboard.readStringValue();
                    }
                    if (player2Name == null) {
                        System.out.print("Second player name: ");
                        player2Name = this.keyboard.readStringValue();
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        } else {
            while (player1Name == null) {
                try {
                    System.out.print("First player name: ");
                    player1Name = this.keyboard.readStringValue();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        System.out.println();
        return new String[]{player1Name, player2Name};
    }

    public void showError(String errorMessage) {
        System.out.println();
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(errorMessage);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println();
    }

    public void showGameBoard(List<Symbol> gameBoard) {
        int gameBoardSize = gameBoard.size();
        int cellsInRow = (int) Math.sqrt(gameBoardSize);

        System.out.println("LEGEND");
        for(int i = 0; i < gameBoardSize; i+= cellsInRow) {
            for(int j = i; j < i + cellsInRow; j++) {
                System.out.print(j + " ");
            }
            System.out.println();
        }
        System.out.println();

        System.out.println("GAME BOARD");
        for(int i = 0; i < gameBoardSize; i+= cellsInRow) {
            for(int j = i; j < i + cellsInRow; j++) {
                String cell = gameBoard.get(j) + "";
                cell = cell.equals("null") ? "_ " : cell + " ";
                System.out.print(cell);
            }
            System.out.println();
        }
        System.out.println();
    }

    public void showPlayerInfo(List<Player> players) {
        String player1Name = players.get(0).name();
        String player2Name = players.get(1).name();
        Symbol player1Symbol = players.get(0).symbol();
        Symbol player2Symbol = players.get(1).symbol();

        System.out.println("PLAYERS");
        System.out.printf("%s (%s), %s (%s)", player1Name, player1Symbol, player2Name, player2Symbol);
        System.out.println();
    }

    public int chooseCell(Symbol symbol) {
        System.out.println();
        System.out.printf("Please choose the cell number in which you want to put your symbol (%s)\n", symbol.name());
        System.out.print("Cell: ");
        return this.keyboard.readIntegerValue();
    }
}
