package com.cli.commands;

import com.cli.CLI;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class LsTest {
    private File tempDir;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() throws IOException {
        // Redirect System.out to capture output for testing
        System.setOut(new PrintStream(outContent));

        // Set up a temporary directory for testing
        tempDir = Files.createTempDirectory("cli_test").toFile();
        CLI.currentDir = tempDir.getAbsolutePath();

        // Create sample files and directories
        new File(tempDir, "file1.txt").createNewFile();
        new File(tempDir, "file2.txt").createNewFile();
        new File(tempDir, ".hiddenfile").createNewFile();
        new File(tempDir, "subdir").mkdir();
    }

    @AfterEach
    public void tearDown() {
        // Clean up temporary directory and reset output
        for (File file : tempDir.listFiles()) {
            file.delete();
        }
        tempDir.delete();
        System.setOut(originalOut);  // Reset System.out
    }

    @Test
    public void testLsWithoutOptions() {
        ls lsCommand = new ls();
        lsCommand.execute(null);  // Run `ls` without any options

        // Debugging: Capture and print output to check if files are listed as expected
        String output = outContent.toString().trim();
        System.out.println("Captured Output:\n" + output); // Debugging line

        // Split and sort the output lines
        String[] lines = output.split("\\r?\\n");
        Arrays.sort(lines);

        // Check that we have exactly 3 items (files and directories) listed
        assertEquals(3, lines.length, "Expected 3 items in the directory");  // Adjust based on output

        // Check for presence of each file and directory (sorted alphabetically)
        assertTrue(lines[0].contains("file1.txt") || lines[1].contains("file1.txt") || lines[2].contains("file1.txt"),
                "file1.txt should be present in output");
        assertTrue(lines[0].contains("file2.txt") || lines[1].contains("file2.txt") || lines[2].contains("file2.txt"),
                "file2.txt should be present in output");
        assertTrue(lines[0].contains("[DIR]  subdir") || lines[1].contains("[DIR]  subdir") || lines[2].contains("[DIR]  subdir"),
                "subdir directory should be present in output");
    }


    @Test
    public void testLsWithAOption() {
        ls lsCommand = new ls();
        lsCommand.execute("-a");  // Run `ls -a` to show hidden files

        String output = outContent.toString().trim();
        assertTrue(output.contains("file1.txt"));
        assertTrue(output.contains("file2.txt"));
        assertTrue(output.contains("[DIR]  subdir"));
        assertTrue(output.contains(".hiddenfile"));  // Hidden file should be displayed
    }

    @Test
    public void testLsWithROption() {
        ls lsCommand = new ls();
        lsCommand.execute("-r");  // Run `ls -r` to reverse order

        String output = outContent.toString().trim();
        String[] lines = output.split("\\r?\\n");

        // Ensure files are listed in reverse alphabetical order
        assertTrue(lines[0].contains("[DIR]  subdir"));
        assertTrue(lines[1].contains("file2.txt"));
        assertTrue(lines[2].contains("file1.txt"));
    }

}
