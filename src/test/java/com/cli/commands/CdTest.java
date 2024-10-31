package com.cli.commands;

import com.cli.CLI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class CdTest {
    private final cd cdCommand = new cd();
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        // Redirect System.out to capture outputs for assertions
        System.setOut(new PrintStream(outContent));

        // Reset currentDir to homeDir for each test
        CLI.currentDir = CLI.homeDir;
        outContent.reset();  // Clear output before each test
    }

    @Test
    public void testExecuteHomeDirectory() {
        boolean result = cdCommand.execute(null);

        assertTrue(result, "cd command should execute successfully with a null path");
        assertEquals(CLI.homeDir, CLI.currentDir, "CLI.currentDir should be set to CLI.homeDir");
        assertTrue(outContent.toString().contains("Current directory: " + CLI.homeDir),
                "Expected output should contain the home directory path");
    }

    @Test
    public void testExecuteParentDirectory() {
        // Create a temporary subdirectory for testing
        File tempDir = new File(CLI.currentDir, "testParentDir");
        tempDir.mkdir();

        // Set currentDir to tempDir for testing ".."
        CLI.currentDir = tempDir.getAbsolutePath();

        boolean result = cdCommand.execute("..");

        assertTrue(result, "cd command should execute successfully with '..' as path");
        assertEquals(tempDir.getParent(), CLI.currentDir,
                "CLI.currentDir should be set to the parent directory");
        assertTrue(outContent.toString().contains("Current directory: " + CLI.currentDir),
                "Expected output should contain the parent directory path");

        // Clean up
        tempDir.delete();
    }

    @Test
    public void testExecuteRootDirectoryWarning() {
        // Set CLI.currentDir to root directory
        CLI.currentDir = new File(CLI.currentDir).toPath().getRoot().toString();

        boolean result = cdCommand.execute("..");

        assertTrue(result, "cd command should execute successfully even when already at root");
        assertEquals(new File(CLI.currentDir).toPath().getRoot().toString(), CLI.currentDir,
                "CLI.currentDir should remain at root");
        assertTrue(outContent.toString().contains("Your in the root directory!."),
                "Expected output should contain 'Your in the root directory!.' message");
    }

    @Test
    public void testExecuteValidDirectory() {
        String validDirName = "testDir";
        File validDir = new File(CLI.currentDir, validDirName);
        validDir.mkdir();

        boolean result = cdCommand.execute(validDirName);

        assertTrue(result, "cd command should execute successfully with a valid directory name");
        assertEquals(validDir.getAbsolutePath(), CLI.currentDir,
                "CLI.currentDir should be updated to the valid directory path");
        assertTrue(outContent.toString().contains("Current directory: " + validDir.getAbsolutePath()),
                "Expected output should contain the path to the new directory");

        // Clean up
        validDir.delete();
    }

    @Test
    public void testExecuteInvalidDirectory() {
        String invalidDirName = "nonExistentDir";

        boolean result = cdCommand.execute(invalidDirName);

        assertTrue(result, "cd command should execute successfully even with an invalid directory");
        assertEquals(CLI.homeDir, CLI.currentDir,
                "CLI.currentDir should remain unchanged on invalid directory access");
        assertTrue(outContent.toString().contains("Directory not found: " + invalidDirName),
                "Expected output should contain 'Directory not found' message");
    }
}
