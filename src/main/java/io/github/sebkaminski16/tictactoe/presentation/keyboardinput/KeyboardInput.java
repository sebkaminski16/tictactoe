package io.github.sebkaminski16.tictactoe.presentation.keyboardinput;

import java.util.Scanner;

public class KeyboardInput {
    //READING INPUT

    private void checkIfInputIsEmpty(String input) {
        if(input.isEmpty()) throw new EmptyInputException("Your input is incorrect! You provided an empty string!");
    }

    public String readStringValue() {
        Scanner scanner = new Scanner(System.in);
        String value = "";

        if(scanner.hasNext()) {
            value = scanner.nextLine();
        }

        this.checkIfInputIsEmpty(value);
        return value;
    }

    public int readIntegerValue() {
        Scanner scanner = new Scanner(System.in);
        String value = "";

        if(scanner.hasNext()) {
            value = scanner.nextLine();
        }

        this.checkIfInputIsEmpty(value);
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            throw new IncorrectInputException("Value is not an integer!");
        }
    }
}
