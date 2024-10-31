package com.cli.commands;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class TouchTest {

    private static final String TEST_FILE_NAME = "testFile.txt";
    private touch touchCommand;

    @BeforeEach
    void setUp() {
        touchCommand = new touch();
    }

    @AfterEach
    void tearDown() {
        File testFile = new File(TEST_FILE_NAME);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    void testCreateNewFile() {
        boolean result = touchCommand.execute(TEST_FILE_NAME);
        assertTrue(result, "The command should execute successfully");
        assertTrue(new File(TEST_FILE_NAME).exists(), "The file should be created");
    }

    @Test
    void testFileAlreadyExists() {
        // First, create the file
        touchCommand.execute(TEST_FILE_NAME);

        // Now, try to create it again
        boolean result = touchCommand.execute(TEST_FILE_NAME);
        assertTrue(result, "The command should execute successfully");
        // The file should still exist
        assertTrue(new File(TEST_FILE_NAME).exists(), "The file should still exist");
    }

    @Test
    void testInvalidFileName() {
        String invalidFileName = "invalid/path/to/file.txt";
        boolean result = touchCommand.execute(invalidFileName);
        assertFalse(result, "The command should fail for an invalid file path");
    }
}
