package com.cli.commands;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class RmTest {

    private static final String TEST_FILE_NAME = "testFileToDelete.txt";
    private rm rmCommand;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        rmCommand = new rm();
        System.setOut(new PrintStream(outputStream));

        // Create a test file to delete
        try {
            new File(TEST_FILE_NAME).createNewFile();
        } catch (Exception e) {
            fail("Failed to create test file for rm command.");
        }
    }

    @AfterEach
    void tearDown() {
        // Ensure the test file is deleted after each test
        File testFile = new File(TEST_FILE_NAME);
        if (testFile.exists()) {
            testFile.delete();
        }
        System.setOut(originalOut);
    }

    @Test
    void testDeleteExistingFile() {
        boolean result = rmCommand.execute(TEST_FILE_NAME);
        assertTrue(result, "Command should execute successfully for an existing file");
        assertFalse(new File(TEST_FILE_NAME).exists(), "File should be deleted");
    }

    @Test
    void testDeleteNonExistentFile() {
        boolean result = rmCommand.execute("nonExistentFile.txt");
        assertFalse(result, "Command should return false for a non-existent file");

        String output = outputStream.toString().trim();
        assertEquals("rm: nonExistentFile.txt: No such file or unable to delete", output, "Output message should indicate the file does not exist");
    }
}
