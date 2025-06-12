package io.github.sebkaminski16.tictactoe.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Game {
    public enum GameMode {
        HUMAN_VS_HUMAN,
        COMPUTER_VS_HUMAN
    }

    private final List<Player> players = new ArrayList<>(2);
    private List<Symbol> gameBoard = null;
    private GameMode gameMode = null;
    private boolean configured = false;
    private int cellsInRow = 0;
    private Player currentlyPlayingPlayer = null;
    private GameStatus gameStatus = GameStatus.UNSETTLED;
    private Player winner = null;

    public List<Player> getPlayers() {
        return this.players;
    }

    public List<Symbol> getGameBoard() {
        return this.gameBoard;
    }

    public boolean isConfigured() {
        return this.configured;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public Player getWinner() {
        return winner;
    }

    public Player getCurrentlyPlayingPlayer() {
        return currentlyPlayingPlayer;
    }

    //CONFIGURATION

    public void configure(GameBoardSize size, String... playerNames) {
        String player1name = playerNames[0];
        String player2name;
        if(player1name == null) throw new PlayerNameIsNullException("Player 1 name is null!");
        if(playerNames.length > 1) {
            player2name = playerNames[1];
            if(player2name == null) throw new PlayerNameIsNullException("Player 2 name is null!");
            if(player1name.equals(player2name)) throw new PlayerNameDuplicateException("The name of player 1 cannot be the same as the name of player 2");
        } else {
            player2name = "Computer";
            if(player1name.equals(player2name)) {
                player1name += "[USER-PROVIDED]";
            }
        }
        this.configurePlayers(player1name, player2name);
        this.configureCells(size);
        this.gameMode = playerNames.length > 1 ? GameMode.HUMAN_VS_HUMAN : GameMode.COMPUTER_VS_HUMAN;
        this.currentlyPlayingPlayer = players.getFirst();
        this.configured = true;
    }

    private List<Symbol> generatePlayerSymbolsRandomly() {
        Random random = new Random();
        int number = random.nextInt(2);
        Symbol player1symbol = number == 0 ? Symbol.O : Symbol.X;
        Symbol player2symbol = player1symbol == Symbol.O ? Symbol.X : Symbol.O;
        //https://stackoverflow.com/a/1005089
        return Arrays.asList(player1symbol, player2symbol);
    }

    private void configurePlayers(String... playerNames) {
        List<Symbol> symbols = this.generatePlayerSymbolsRandomly();
        this.players.add(new Player(playerNames[0], symbols.get(0)));
        this.players.add(new Player(playerNames[1], symbols.get(1)));
    }

    private void configureCells(GameBoardSize size) {
        int cells = this.cellsInRow = size.getValue();
        this.gameBoard = new ArrayList<>(cells * cells);
        for(int i = 0; i < cells * cells; i++) {
            this.gameBoard.add(null);
        }
    }

    //GAMEPLAY

    public void insertSymbolIntoCellN(int cellNumber) {
        if(!this.isConfigured()) throw new GameNotConfiguredException("The game is not configured! Cannot insert any cells!");
        if(this.gameStatus.equals(GameStatus.UNSETTLED)) {
            int gameBoardSize = this.gameBoard.size();
            if (cellNumber > gameBoardSize || cellNumber < 0)
                throw new CellNumberOutOfBoundsException("Incorrect cell number! Cell number should be from range ["
                        + this.cellsInRow + "-" + gameBoardSize + "]");
            if (this.gameBoard.get(cellNumber) != null)
                throw new CellAlreadyTakenException("The cell already contains a symbol! Choose other cell!");

            this.gameBoard.set(cellNumber, this.currentlyPlayingPlayer.symbol());
            this.currentlyPlayingPlayer = this.players.indexOf(this.currentlyPlayingPlayer)
                    == 0 ? this.players.get(1) : this.players.get(0);

            this.checkGameBoardState();

            if (this.gameStatus.equals(GameStatus.UNSETTLED) && this.gameMode.equals(GameMode.COMPUTER_VS_HUMAN)) {
                this.gameBoard.set(this.chooseRandomCell(), this.currentlyPlayingPlayer.symbol());
                this.currentlyPlayingPlayer = this.players.indexOf(this.currentlyPlayingPlayer)
                        == 0 ? this.players.get(1) : this.players.get(0);
                this.checkGameBoardState();
            }
        } else throw new GameAlreadyFinishedException("The game has already finished! Cannot insert any more cells!");
    }

    // CALCULATIONS & OTHER

    private int chooseRandomCell() {
        Random random = new Random();
        int randomCell = random.nextInt(this.cellsInRow * this.cellsInRow);
        while(this.gameBoard.get(randomCell) != null) {
            randomCell = random.nextInt(this.cellsInRow * this.cellsInRow);
        }
        return randomCell;
    }

    private void checkGameBoardState() {
        this.checkDiagonalState();
        this.checkHorizontalAndVerticalState();
        if(this.gameStatus.equals(GameStatus.UNSETTLED) && this.gameBoard.size() == cellsInRow * cellsInRow && !this.gameBoard.contains(null)) {
            this.gameStatus = GameStatus.TIE;
        }
    }

    private void checkDiagonalState() {
        if(this.gameStatus.equals(GameStatus.UNSETTLED)) {
            boolean complete = true;
            Symbol lastSymbol = null;
            //left to right
            for (int i = 0; i < this.cellsInRow * this.cellsInRow; i += this.cellsInRow + 1) {
                if (i != 0) {
                    if (this.gameBoard.get(i) != lastSymbol || this.gameBoard.get(i) == null) complete = false;
                }
                lastSymbol = this.gameBoard.get(i);
            }
            //right to left
            if(!complete) {
                complete = true;
                lastSymbol = null;
                for (int i = this.cellsInRow - 1; i <= this.cellsInRow * this.cellsInRow - this.cellsInRow; i += this.cellsInRow - 1) {
                    if (i != this.cellsInRow - 1) {
                        if (this.gameBoard.get(i) != lastSymbol || this.gameBoard.get(i) == null) complete = false;
                    }
                    lastSymbol = this.gameBoard.get(i);
                }
            }
            if (complete) {
                this.gameStatus = GameStatus.HAS_WINNER;
                this.winner = this.players.get(0).symbol() == lastSymbol ? this.players.get(0) : this.players.get(1);
            }
        }
    }

    private void checkHorizontalAndVerticalState() {
        if(this.gameStatus.equals(GameStatus.UNSETTLED)) {
            for(int i = 1; i <= this.cellsInRow; i++) {
                boolean complete = true;
                Symbol lastSymbol = null;

                for (int j = i * this.cellsInRow - this.cellsInRow; j < i * this.cellsInRow; j++) {
                    if (j != i * this.cellsInRow - this.cellsInRow) {
                        if (this.gameBoard.get(j) != lastSymbol || this.gameBoard.get(j) == null) complete = false;
                    }
                    lastSymbol = this.gameBoard.get(j);
                }

                if(!complete) {
                    complete = true;
                    lastSymbol = null;
                    for (int j = i - 1; j <= this.cellsInRow * this.cellsInRow - this.cellsInRow + i - 1; j += this.cellsInRow) {
                        if (j != i - 1) {
                            if (this.gameBoard.get(j) != lastSymbol || this.gameBoard.get(j) == null) complete = false;
                        }
                        lastSymbol = this.gameBoard.get(j);
                    }
                }

                if (complete) {
                    this.gameStatus = GameStatus.HAS_WINNER;
                    this.winner = this.players.get(0).symbol() == lastSymbol ? this.players.get(0) : this.players.get(1);
                }
            }
        }
    }
}
