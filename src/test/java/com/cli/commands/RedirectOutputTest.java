package com.cli.commands;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import static org.junit.jupiter.api.Assertions.*;

class RedirectOutputTest {

    private static final String TEST_FILE_NAME = "redirectOutputTest.txt";
    private RedirectOutput redirectOutput;

    @BeforeEach
    void setUp() {
        redirectOutput = new RedirectOutput();
    }

    @AfterEach
    void tearDown() {
        File testFile = new File(TEST_FILE_NAME);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    void testWriteToFile() {
        boolean result = redirectOutput.execute("Hello, World!", TEST_FILE_NAME, false);
        assertTrue(result, "Writing to the file should be successful");

        String content = readFile(TEST_FILE_NAME);
        assertEquals("Hello, World!", content, "The file content should match the written output");
    }

    @Test
    void testAppendToFile() {
        redirectOutput.execute("Hello, World!", TEST_FILE_NAME, false); // First write
        boolean result = redirectOutput.execute("Appended text", TEST_FILE_NAME, true); // Then append
        assertTrue(result, "Appending to the file should be successful");

        String content = readFile(TEST_FILE_NAME);
        assertEquals("Hello, World!\nAppended text", content, "The file content should match the written and appended output");
    }

    @Test
    void testErrorOnInvalidFilePath() {
        boolean result = redirectOutput.execute("Test output", "/invalid/path/to/file.txt", false);
        assertFalse(result, "Writing to an invalid file path should fail");
    }

    private String readFile(String filePath) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (Exception e) {
            fail("Failed to read the file: " + e.getMessage());
        }
        return content.toString().trim(); // Trim to remove trailing newline
    }
}
