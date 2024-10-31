package com.cli.commands;

import com.cli.CLI;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class RmdirTest {

    private static final String TEST_DIR_NAME = "testDirectory";
    private static final String NON_EMPTY_DIR_NAME = "nonEmptyDirectory";
    private rmdir rmdirCommand;

    @BeforeEach
    void setUp() {
        rmdirCommand = new rmdir();

        // Create test directory
        new File(TEST_DIR_NAME).mkdir();

        // Create a non-empty directory
        File nonEmptyDir = new File(NON_EMPTY_DIR_NAME);
        nonEmptyDir.mkdir();
        try {
            new File(nonEmptyDir, "testFile.txt").createNewFile(); // Add a file to make it non-empty
        } catch (Exception e) {
            fail("Failed to create test file in non-empty directory.");
        }
    }

    @AfterEach
    void tearDown() {
        // Clean up: delete test directories if they were not removed
        File testDir = new File(TEST_DIR_NAME);
        if (testDir.exists()) {
            testDir.delete();
        }

        File nonEmptyDir = new File(NON_EMPTY_DIR_NAME);
        if (nonEmptyDir.exists()) {
            nonEmptyDir.delete(); // This will fail, but we'll clean it in another test
        }
    }

    @Test
    void testRemoveEmptyDirectory() {
        boolean result = rmdirCommand.execute(TEST_DIR_NAME);
        assertTrue(result, "Command should execute successfully");
        assertFalse(new File(TEST_DIR_NAME).exists(), "Directory should be removed");
    }

    @Test
    void testRemoveNonEmptyDirectory() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        boolean result = rmdirCommand.execute(NON_EMPTY_DIR_NAME);
        assertTrue(result, "Command should execute successfully even if directory is not empty");

        String output = outputStream.toString().trim();
        assertEquals("rmdir: directory not empty: " + NON_EMPTY_DIR_NAME, output, "Output message should indicate directory is not empty");

        System.setOut(originalOut);
    }

    @Test
    void testDirectoryNotFound() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        boolean result = rmdirCommand.execute("nonExistentDirectory");
        assertTrue(result, "Command should execute successfully even if directory is not found");

        String output = outputStream.toString().trim();
        assertEquals("rmdir: directory not found: nonExistentDirectory", output, "Output message should indicate directory is not found");

        System.setOut(originalOut);
    }

    @Test
    void testNotADirectory() {
        File testFile = new File(CLI.currentDir, "notADirectory.txt");
        try {
            testFile.createNewFile(); // Create a file to test with
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PrintStream originalOut = System.out;
            System.setOut(new PrintStream(outputStream));

            boolean result = rmdirCommand.execute(testFile.getName());
            assertTrue(result, "Command should execute successfully even if input is not a directory");

            String output = outputStream.toString().trim();
            assertEquals("rmdir: not a directory: " + testFile.getName(), output, "Output message should indicate input is not a directory");

            System.setOut(originalOut);
        } catch (Exception e) {
            fail("Failed to create test file for not a directory test.");
        } finally {
            if (testFile.exists()) {
                testFile.delete(); // Clean up the test file
            }
        }
    }

    @Test
    void testMissingDirectoryName() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        boolean result = rmdirCommand.execute("");
        assertTrue(result, "Command should execute successfully even if directory name is missing");

        String output = outputStream.toString().trim();
        assertEquals("rmdir: missing directory name", output, "Output message should indicate that the directory name is missing");

        System.setOut(originalOut);
    }

    @Test
    void testNullDirectoryName() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        boolean result = rmdirCommand.execute(null);
        assertTrue(result, "Command should execute successfully even if directory name is null");

        String output = outputStream.toString().trim();
        assertEquals("rmdir: missing directory name", output, "Output message should indicate that the directory name is missing");

        System.setOut(originalOut);
    }
}
