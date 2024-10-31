package com.cli.commands;

import com.cli.CLI;

import java.io.File;
import java.io.IOException;

public class touch {
    public boolean execute(String fileName) {
        try {
            File file = new File(CLI.currentDir, fileName);
            if (file.createNewFile()) {
                System.out.println("File created: " + fileName);
            } else {
                System.out.println("File already exists: " + fileName);
            }
            return true;
        } catch (IOException e) {
            System.out.println("An error occurred while creating the file: " + e.getMessage());
            return false;
        }
    }
}
