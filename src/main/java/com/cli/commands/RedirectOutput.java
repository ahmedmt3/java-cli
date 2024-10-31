package com.cli.commands;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class RedirectOutput {
    public boolean execute(String commandOutput, String filePath, boolean isAppend) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, isAppend))) {
            writer.write(commandOutput);
            writer.newLine();
            System.out.println("Output " + (isAppend ? "appended" : "written to") + " " + filePath);
            return true;
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
            return false;
        }
    }
}
