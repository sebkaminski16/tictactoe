package io.github.sebkaminski16.tictactoe.logic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class GameTestSuite {
    private Game game;

    @BeforeEach
    public void beforeEach() {
        //arrange
        this.game = new Game();
    }

    @Nested
    class configureTests {
        @Test
        public void configureThrowsExceptionIfNoPlayerNameIsSpecified() {
            //act & assert
            Assertions.assertThrows(PlayerNameIsNullException.class, () -> game.configure(GameBoardSize.SMALL));
        }

        @Test
        public void configureThrowsExceptionIfPlayerNameIsSpecifiedAsNull() {
            //act & assert
            Assertions.assertThrows(PlayerNameIsNullException.class, () -> game.configure(GameBoardSize.SMALL, "Player1", null));
            Assertions.assertThrows(PlayerNameIsNullException.class, () -> game.configure(GameBoardSize.SMALL,  null, "Player 2"));
            Assertions.assertThrows(PlayerNameIsNullException.class, () -> game.configure(GameBoardSize.SMALL, (String) null));
        }

        @Test
        public void configureThrowsExceptionIfHumanVsHumanPlayerNamesAreTheSame() {
            //arrange
            String duplicateName = "Player";
            //act & assert
            Assertions.assertThrows(PlayerNameDuplicateException.class, () -> game.configure(GameBoardSize.SMALL, duplicateName, duplicateName));
        }

        @Test
        public void configureComputerVsHumanShouldDistinguishBetweenDuplicateNames() {
            //act
            game.configure(GameBoardSize.SMALL, "Computer");
            //assert
            Assertions.assertEquals("Computer[USER-PROVIDED]", game.getPlayers().getFirst().name());
            Assertions.assertEquals("Computer", game.getPlayers().get(1).name());
        }

        @Test
        public void configureHumanVsHumanShouldBeCorrectlyConfigured() {
            //act
            game.configure(GameBoardSize.SMALL, "Player1", "Player2");
            //assert
            Assertions.assertEquals("Player1", game.getPlayers().getFirst().name());
            Assertions.assertEquals("Player2", game.getPlayers().get(1).name());
            Assertions.assertEquals(3, game.getCellsInRow());
            Assertions.assertEquals(GameMode.HUMAN_VS_HUMAN, game.getGameMode());
            Assertions.assertEquals("Player1", game.getCurrentlyPlayingPlayer().name());
            Assertions.assertTrue(game.isConfigured());
            Assertions.assertNull(game.getWinner());
            Assertions.assertEquals(GameStatus.UNSETTLED, game.getGameStatus());
        }

        @Test
        public void configureComputerVsHumanShouldBeCorrectlyConfigured() {
            //act
            game.configure(GameBoardSize.LARGE, "Player1");
            //assert
            Assertions.assertEquals("Player1", game.getPlayers().getFirst().name());
            Assertions.assertEquals("Computer", game.getPlayers().get(1).name());
            Assertions.assertEquals(5, game.getCellsInRow());
            Assertions.assertEquals(GameMode.COMPUTER_VS_HUMAN, game.getGameMode());
            Assertions.assertEquals("Player1", game.getCurrentlyPlayingPlayer().name());
            Assertions.assertTrue(game.isConfigured());
            Assertions.assertNull(game.getWinner());
            Assertions.assertEquals(GameStatus.UNSETTLED, game.getGameStatus());
        }
    }

    @Nested
    class insertSymbolIntoCellNTests {

        @Test
        public void insertSymbolIntoCellNShouldThrowExceptionIfGameHadNotBeenConfigured() {
            //act & assert
            Assertions.assertThrows(GameNotConfiguredException.class, () -> game.insertSymbolIntoCellN(2));
        }


        @Test
        public void insertSymbolIntoCellNShouldThrowExceptionIfCellSpecifiedDoesNotExist() {
            //act
            game.configure(GameBoardSize.SMALL, "Player1", "Player2");
            //assert
            Assertions.assertThrows(CellNumberOutOfBoundsException.class, () -> game.insertSymbolIntoCellN(GameBoardSize.SMALL.getValue() * 100));
        }

        @Test
        public void insertSymbolIntoCellNShouldThrowExceptionIfCellIsAlreadyOccupied() {
            //act
            game.configure(GameBoardSize.SMALL, "Player1", "Player2");
            int cell = 0;
            game.insertSymbolIntoCellN(cell);
            //assert
            Assertions.assertThrows(CellAlreadyTakenException.class, () -> game.insertSymbolIntoCellN(cell));
        }

        @Test
        public void insertSymbolIntoCellNShouldSuccessfullyFinishTheGameAsTie() {
            //act
            game.configure(GameBoardSize.SMALL, "Player1", "Player2");
            game.insertSymbolIntoCellN(1);
            game.insertSymbolIntoCellN(0);
            game.insertSymbolIntoCellN(3);
            game.insertSymbolIntoCellN(2);
            game.insertSymbolIntoCellN(5);
            game.insertSymbolIntoCellN(4);
            game.insertSymbolIntoCellN(6);
            game.insertSymbolIntoCellN(7);
            game.insertSymbolIntoCellN(8);

            /*
            * GAME BOARD - TIE
            * x o x
            * o x o
            * o x o
            * */

            //assert
            Assertions.assertEquals(GameStatus.TIE, game.getGameStatus());
            Assertions.assertNull(game.getWinner());
        }

        @Test
        public void insertSymbolIntoCellNShouldSuccessfullyFinishTheGameWithOneWinnerHorizontal() {
            //act
            game.configure(GameBoardSize.SMALL, "Player1", "Player2");
            game.insertSymbolIntoCellN(0);
            game.insertSymbolIntoCellN(3);
            game.insertSymbolIntoCellN(1);
            game.insertSymbolIntoCellN(5);
            game.insertSymbolIntoCellN(2);

            /*
             * GAME BOARD - PLAYER 1 WINS
             * x x x
             * o _ o
             * _ _ _
             * */

            //assert
            Assertions.assertEquals(GameStatus.HAS_WINNER, game.getGameStatus());
            Assertions.assertEquals("Player1", game.getWinner().name());
        }

        @Test
        public void insertSymbolIntoCellNShouldSuccessfullyFinishTheGameWithOneWinnerVertical() {
            //act
            game.configure(GameBoardSize.SMALL, "Player1", "Player2");
            game.insertSymbolIntoCellN(0);
            game.insertSymbolIntoCellN(2);
            game.insertSymbolIntoCellN(3);
            game.insertSymbolIntoCellN(5);
            game.insertSymbolIntoCellN(6);

            /*
             * GAME BOARD - PLAYER 1 WINS
             * x _ o
             * x _ o
             * x _ _
             * */

            //assert
            Assertions.assertEquals(GameStatus.HAS_WINNER, game.getGameStatus());
            Assertions.assertEquals("Player1", game.getWinner().name());
        }

        @Test
        public void insertSymbolIntoCellNShouldSuccessfullyFinishTheGameWithOneWinnerDiagonalRight() {
            //act
            game.configure(GameBoardSize.SMALL, "Player1", "Player2");
            game.insertSymbolIntoCellN(0);
            game.insertSymbolIntoCellN(2);
            game.insertSymbolIntoCellN(4);
            game.insertSymbolIntoCellN(5);
            game.insertSymbolIntoCellN(8);

            /*
             * GAME BOARD - PLAYER 1 WINS
             * x _ o
             * _ x o
             * _ _ x
             * */

            //assert
            Assertions.assertEquals(GameStatus.HAS_WINNER, game.getGameStatus());
            Assertions.assertEquals("Player1", game.getWinner().name());
        }

        @Test
        public void insertSymbolIntoCellNShouldSuccessfullyFinishTheGameWithOneWinnerDiagonalLeft() {
            //act
            game.configure(GameBoardSize.SMALL, "Player1", "Player2");
            game.insertSymbolIntoCellN(2);
            game.insertSymbolIntoCellN(0);
            game.insertSymbolIntoCellN(4);
            game.insertSymbolIntoCellN(1);
            game.insertSymbolIntoCellN(6);

            /*
             * GAME BOARD - PLAYER 1 WINS
             * o o x
             * _ x _
             * x _ _
             * */

            //assert
            Assertions.assertEquals(GameStatus.HAS_WINNER, game.getGameStatus());
            Assertions.assertEquals("Player1", game.getWinner().name());
        }

        @Test
        public void insertSymbolIntoCellNShouldThrowExceptionIfGameHadAlreadyFinished() {
            //act
            game.configure(GameBoardSize.SMALL, "Player1", "Player2");
            game.insertSymbolIntoCellN(2);
            game.insertSymbolIntoCellN(0);
            game.insertSymbolIntoCellN(4);
            game.insertSymbolIntoCellN(1);
            game.insertSymbolIntoCellN(6);

            /*
             * GAME BOARD - PLAYER 1 WINS
             * o o x
             * _ x _
             * x _ _
             * */

            //assert
            Assertions.assertThrows(GameAlreadyFinishedException.class, () -> game.insertSymbolIntoCellN(0));
        }
    }
}