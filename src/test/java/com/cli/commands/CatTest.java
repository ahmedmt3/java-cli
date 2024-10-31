package com.cli.commands;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.*;

class CatTest {

    private static final String TEST_FILE_NAME = "testFile.txt";
    private cat catCommand;

    @BeforeEach
    void setUp() throws IOException {
        catCommand = new cat();
        // Create a test file with some content
        try (FileWriter writer = new FileWriter(TEST_FILE_NAME)) {
            writer.write("Hello, World!\nThis is a test file.");
        }
    }

    @AfterEach
    void tearDown() {
        File testFile = new File(TEST_FILE_NAME);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    void testFileExists() {
        boolean result = catCommand.execute(TEST_FILE_NAME);
        assertTrue(result, "The command should execute successfully");
    }

    @Test
    void testFileDoesNotExist() {
        boolean result = catCommand.execute("nonExistentFile.txt");
        assertFalse(result, "The command should fail for a non-existent file");
    }

    @Test
    void testFileContent() {
        // Capture the output from the command
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        catCommand.execute(TEST_FILE_NAME);

        // Restore the original System.out
        System.setOut(originalOut);

        String output = outputStream.toString().trim();
        String expectedOutput = "Hello, World!\nThis is a test file.";

        // Normalize line endings
        String normalizedOutput = output.replace("\r\n", "\n");
        String normalizedExpectedOutput = expectedOutput.replace("\r\n", "\n");

        assertEquals(normalizedExpectedOutput, normalizedOutput, "The output should match the file content");
    }

}
