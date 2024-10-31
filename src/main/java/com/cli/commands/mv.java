package com.cli.commands;

import java.io.File;

public class mv {

    public boolean execute(String sourcePath, String destinationPath) {
        File sourceFile = new File(sourcePath);
        File destinationFile = new File(destinationPath);

        // Check if source file exists
        if (!sourceFile.exists()) {
            System.out.println("mv: cannot move '" + sourcePath + "': No such file or directory");
            return false;
        }

        // Adjust destination if it's a directory
        if (destinationFile.isDirectory()) {
            destinationFile = new File(destinationPath + File.separator + sourceFile.getName());
        }

        // Move the file
        if (sourceFile.renameTo(destinationFile)) {
            System.out.println("File moved/renamed successfully.");
        } else {
            System.out.println("mv: failed to move '" + sourcePath + "' to '" + destinationPath + "'");
        }
        return true;
    }
}
