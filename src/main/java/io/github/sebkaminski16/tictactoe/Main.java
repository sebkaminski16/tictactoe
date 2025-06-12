package io.github.sebkaminski16.tictactoe;

import io.github.sebkaminski16.tictactoe.logic.Game;
import io.github.sebkaminski16.tictactoe.logic.GameBoardSize;
import io.github.sebkaminski16.tictactoe.logic.GameStatus;
import io.github.sebkaminski16.tictactoe.presentation.consolescreen.GameScreen;
import io.github.sebkaminski16.tictactoe.presentation.keyboardinput.KeyboardInput;

public class Main {
    public static void main(String[] args) {
        GameScreen screen = new GameScreen(new KeyboardInput());
        Game game = new Game();

        screen.showFramedMessage("*", "TIC-TAC-TOE");

        while(!game.isConfigured()) {
            int gameMode = screen.chooseGameMode();
            GameBoardSize gameBoardSize = screen.chooseGameBoardSize();
            String[] players = screen.choosePlayers(gameMode);
            try {
                if (players[1] != null) {
                    game.configure(gameBoardSize, players[0], players[1]);
                } else {
                    game.configure(gameBoardSize, players[0]);
                }
            } catch (Exception e) {
                screen.showError(e.getMessage());
            }
        }

        screen.showGameBoard(game.getGameBoard());
        screen.showPlayerInfo(game.getPlayers());

        while(game.getGameStatus().equals(GameStatus.UNSETTLED)) {
            int cell = -1;
            while (cell == -1) {
                try {
                    cell = screen.chooseCell(game.getCurrentlyPlayingPlayer().symbol());
                    game.insertSymbolIntoCellN(cell);
                } catch (Exception e) {
                    cell = 0;
                    screen.showError(e.getMessage());
                }
            }
            screen.showGameBoard(game.getGameBoard());
            screen.showPlayerInfo(game.getPlayers());
        }

        if(game.getGameStatus().equals(GameStatus.TIE)) {
            screen.showFramedMessage("@", "It's a tie!");
        } else {
            screen.showFramedMessage("$", game.getWinner().name() + " wins!");
        }
    }
}

