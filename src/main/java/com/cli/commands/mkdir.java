package com.cli.commands;

import com.cli.CLI;

import java.io.File;

public class mkdir {

    public boolean execute(String dirName) {
        if (dirName == null || dirName.isEmpty()) {
            System.out.println("mkdir: missing directory name");
            return true;
        }

        File newDir = new File(CLI.currentDir, dirName);
        if (newDir.exists()) {
            System.out.println("mkdir: directory already exists: " + dirName);
        } else {
            final boolean created = newDir.mkdir();
            if (created) {
                System.out.println("Directory created: " + newDir.getAbsolutePath());
            } else {
                System.out.println("mkdir: failed to create directory: " + dirName);
            }
        }
        return true;
    }
}
