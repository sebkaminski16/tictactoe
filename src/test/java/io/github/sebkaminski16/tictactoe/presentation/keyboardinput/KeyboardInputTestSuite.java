package io.github.sebkaminski16.tictactoe.presentation.keyboardinput;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

//https://stackoverflow.com/a/31635737
public class KeyboardInputTestSuite {

    private final KeyboardInput keyboardInputInstance = new KeyboardInput();

    @Nested
    class StringValues {
        @Test
        public void keyboardInputShouldSuccessfullyReadStringValue() {
            //act
            String input = "test string";
            ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
            System.setIn(in);
            //assert
            Assertions.assertEquals("test string", keyboardInputInstance.readStringValue());
        }

        @Test
        public void keyboardInputReadStringValueShouldThrowAnExceptionIfInputIsEmpty() {
            //act
            String input = "";
            ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
            System.setIn(in);
            //assert
            Assertions.assertThrows(EmptyInputException.class, keyboardInputInstance::readStringValue);
        }
    }

    @Nested
    class IntegerValues {
        @Test
        public void keyboardInputShouldSuccessfullyReadIntegerValue() {
            //act
            String input = "3";
            ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
            System.setIn(in);
            //assert
            Assertions.assertEquals(3, keyboardInputInstance.readIntegerValue());
        }

        @Test
        public void keyboardInputReadIntegerValueShouldThrowAnExceptionIfInputIsEmpty() {
            //act
            String input = "";
            ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
            System.setIn(in);
            //assert
            Assertions.assertThrows(EmptyInputException.class, keyboardInputInstance::readIntegerValue);
        }

        @Test
        public void keyboardInputReadIntegerValueShouldThrowAnExceptionIfInputIsNotAnInteger() {
            //act
            String input = "not an integer";
            ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
            System.setIn(in);
            //assert
            Assertions.assertThrows(IncorrectInputException.class, keyboardInputInstance::readIntegerValue);
        }
    }
}
