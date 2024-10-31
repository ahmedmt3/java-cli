package com.cli.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class MvTest {
    private final mv mvCommand = new mv();
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        // Redirect System.out to capture outputs for assertions
        System.setOut(new PrintStream(outContent));
        outContent.reset(); // Clear output before each test
    }

    @Test
    public void testExecuteMoveFileToNewPath() throws IOException {
        File sourceFile = new File("testSourceFile.txt");
        File destinationFile = new File("testDestinationFile.txt");

        // Set up source file
        assertTrue(sourceFile.createNewFile(), "Source file should be created successfully.");

        // Execute mv command
        boolean result = mvCommand.execute(sourceFile.getPath(), destinationFile.getPath());

        assertTrue(result, "mv command should execute successfully for moving file.");
        assertFalse(sourceFile.exists(), "Source file should no longer exist after move.");
        assertTrue(destinationFile.exists(), "Destination file should exist after move.");
        assertTrue(outContent.toString().contains("File moved/renamed successfully."),
                "Expected output should confirm successful move.");

        // Clean up
        destinationFile.delete();
    }

    @Test
    public void testExecuteMoveFileToDirectory() throws IOException {
        File sourceFile = new File("testSourceFile.txt");
        File destinationDir = new File("testDir");

        // Set up source file and destination directory
        assertTrue(sourceFile.createNewFile(), "Source file should be created successfully.");
        assertTrue(destinationDir.mkdir(), "Destination directory should be created successfully.");

        // Execute mv command
        boolean result = mvCommand.execute(sourceFile.getPath(), destinationDir.getPath());

        File movedFile = new File(destinationDir, sourceFile.getName());

        assertTrue(result, "mv command should execute successfully for moving file to directory.");
        assertFalse(sourceFile.exists(), "Source file should no longer exist after move.");
        assertTrue(movedFile.exists(), "File should be moved inside the destination directory.");
        assertTrue(outContent.toString().contains("File moved/renamed successfully."),
                "Expected output should confirm successful move.");

        // Clean up
        movedFile.delete();
        destinationDir.delete();
    }

    @Test
    public void testExecuteNonExistentSourceFile() {
        String sourcePath = "nonExistentFile.txt";
        String destinationPath = "testDestinationFile.txt";

        boolean result = mvCommand.execute(sourcePath, destinationPath);

        assertFalse(result, "mv command should fail when source file does not exist.");
        assertTrue(outContent.toString().contains("mv: cannot move '" + sourcePath + "': No such file or directory"),
                "Expected output should indicate non-existent source file.");
    }

    @Test
    public void testExecuteFailedMove() throws IOException {
        File sourceFile = new File("testSourceFile.txt");
        File destinationFile = new File("testDestinationFile.txt");

        // Set up source file and a "locked" destination
        assertTrue(sourceFile.createNewFile(), "Source file should be created successfully.");
        assertTrue(destinationFile.createNewFile(), "Destination file should be created successfully.");

        // Try to move source file to an existing file (should fail)
        boolean result = mvCommand.execute(sourceFile.getPath(), destinationFile.getPath());

        assertTrue(result, "mv command should execute but fail to overwrite the existing destination.");
        assertTrue(sourceFile.exists(), "Source file should still exist after failed move.");
        assertTrue(destinationFile.exists(), "Destination file should remain unchanged.");
        assertTrue(outContent.toString().contains("mv: failed to move '" + sourceFile.getPath() + "' to '" + destinationFile.getPath() + "'"),
                "Expected output should indicate failure to move file.");

        // Clean up
        sourceFile.delete();
        destinationFile.delete();
    }
}
