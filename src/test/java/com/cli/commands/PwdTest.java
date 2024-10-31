package com.cli.commands;

import com.cli.CLI;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PwdTest {

    @Test
    void testExecute() {
        // Prepare to capture the output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        // Create an instance of pwd and execute it
        pwd pwdCommand = new pwd();
        boolean result = pwdCommand.execute();

        // Restore the original System.out
        System.setOut(originalOut);

        // Verify the output
        String expectedOutput = CLI.currentDir + System.lineSeparator(); // Use System.lineSeparator() for cross-platform compatibility
        assertEquals(expectedOutput, outputStream.toString(), "Output should match the current directory");
        assertEquals(true, result, "Command should execute successfully");
    }
}
