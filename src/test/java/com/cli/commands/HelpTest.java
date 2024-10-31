package com.cli.commands;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HelpTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        // Redirect System.out to capture output for testing
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void tearDown() {
        // Reset System.out after each test
        System.setOut(originalOut);
    }

    @Test
    public void testHelpOutput() {
        help helpCommand = new help();
        helpCommand.execute();

        String output = outContent.toString().trim();

        // Check for key phrases in the output to confirm each command's presence
        assertTrue(output.contains("Command Line Interpreter Help"));
        assertTrue(output.contains("Available Commands:"));
        assertTrue(output.contains("exit\t\t - Terminates the current session."));
        assertTrue(output.contains("pwd\t\t - Prints the current working directory."));
        assertTrue(output.contains("cd\t\t <directory> - Changes the current directory to the specified <directory>."));
        assertTrue(output.contains("ls\t\t - Lists files and directories in the current directory."));
        assertTrue(output.contains("ls\t\t -a - Lists all files, including hidden files, in the current directory."));
        assertTrue(output.contains("ls\t\t -r - Lists files and directories in reverse order."));
        assertTrue(output.contains("mkdir\t <directory> - Creates a new directory with the specified <directory> name."));
        assertTrue(output.contains("rmdir\t <directory> - Removes the specified <directory> if it's empty."));
        assertTrue(output.contains("touch\t <file> - Creates a new empty file with the specified <file> name."));
        assertTrue(output.contains("mv\t\t <source> <destination> - Moves or renames a file or directory."));
        assertTrue(output.contains("rm\t\t <file> - Deletes the specified <file>."));
        assertTrue(output.contains("cat\t\t <file> - Displays the contents of the specified <file>."));
        assertTrue(output.contains(">\t\t - Redirects output to a file, overwriting it."));
        assertTrue(output.contains(">>\t\t - Redirects output to a file, appending to it."));
        assertTrue(output.contains("|\t\t - Pipes the output of one command as input to another command."));
    }
}
