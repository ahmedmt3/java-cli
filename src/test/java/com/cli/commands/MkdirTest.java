package com.cli.commands;
import com.cli.CLI;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class MkdirTest {

    private static final String TEST_DIR_NAME = "testDirectory";
    private static final String TEST_DIR_PATH = CLI.currentDir + File.separator + TEST_DIR_NAME;
    private mkdir mkdirCommand;

    @BeforeEach
    void setUp() {
        mkdirCommand = new mkdir();
    }

    @AfterEach
    void tearDown() {
        // Clean up: delete the directory if it was created
        File testDir = new File(TEST_DIR_PATH);
        if (testDir.exists()) {
            testDir.delete();
        }
    }

    @Test
    void testCreateDirectory() {
        boolean result = mkdirCommand.execute(TEST_DIR_NAME);
        assertTrue(result, "Command should execute successfully");
        assertTrue(new File(TEST_DIR_PATH).exists(), "Directory should be created");
    }

    @Test
    void testDirectoryAlreadyExists() {
        mkdirCommand.execute(TEST_DIR_NAME); // Create the directory first
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        boolean result = mkdirCommand.execute(TEST_DIR_NAME);
        assertTrue(result, "Command should execute successfully even if the directory exists");

        String output = outputStream.toString().trim();
        assertEquals("mkdir: directory already exists: " + TEST_DIR_NAME, output, "Output message should indicate that the directory exists");

        System.setOut(originalOut);
    }

    @Test
    void testMissingDirectoryName() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        boolean result = mkdirCommand.execute("");
        assertTrue(result, "Command should execute successfully even if the directory name is missing");

        String output = outputStream.toString().trim();
        assertEquals("mkdir: missing directory name", output, "Output message should indicate that the directory name is missing");

        System.setOut(originalOut);
    }

    @Test
    void testNullDirectoryName() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        boolean result = mkdirCommand.execute(null);
        assertTrue(result, "Command should execute successfully even if the directory name is null");

        String output = outputStream.toString().trim();
        assertEquals("mkdir: missing directory name", output, "Output message should indicate that the directory name is missing");

        System.setOut(originalOut);
    }
}
