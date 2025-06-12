package io.github.sebkaminski16.tictactoe.presentation.keyboardinput;

public class IncorrectInputException extends RuntimeException {
  public IncorrectInputException(String message) {
    super(message);
  }
}
