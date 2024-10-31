package com.cli.commands;

import com.cli.CLI;

import java.io.File;

public class rmdir {

    public boolean execute(String dirName) {
        if (dirName == null || dirName.isEmpty()) {
            System.out.println("rmdir: missing directory name");
            return true;
        }

        File dir = new File(CLI.currentDir, dirName);

        if (!dir.exists()) {
            System.out.println("rmdir: directory not found: " + dirName);

        } else if (!dir.isDirectory()) {
            System.out.println("rmdir: not a directory: " + dirName);

        } else if (dir.list().length > 0) {
            System.out.println("rmdir: directory not empty: " + dirName);

        } else {
            boolean deleted = dir.delete();
            if (deleted) {
                System.out.println("Directory removed: " + dir.getAbsolutePath());
            } else {
                System.out.println("rmdir: failed to remove directory: " + dirName);
            }
        }
        return true;
    }
}
