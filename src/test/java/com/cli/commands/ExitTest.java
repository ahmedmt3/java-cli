package com.cli.commands;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExitTest {

    @Test
    void testExecute() {
        // Set up a stream to capture output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out; // Save the original System.out
        System.setOut(new PrintStream(outputStream)); // Redirect System.out to our stream

        exit exitCommand = new exit();
        boolean result = exitCommand.execute(); // Execute the command

        System.setOut(originalOut); // Restore the original System.out

        // Prepare the expected output
        String expectedOutput = "Exiting Command Line Interface";
        assertEquals(expectedOutput, outputStream.toString().trim(), "Output should match the exit message");
        assertEquals(false, result, "The command should indicate termination of the CLI");
    }
}
