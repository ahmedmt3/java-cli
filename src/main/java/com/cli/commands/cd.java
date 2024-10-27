package com.cli.commands;

import com.cli.CLI;

import java.io.File;

public class cd {

    public boolean execute(String path){
        if (path == null || path.isEmpty()) {
            CLI.currentDir = CLI.homeDir;

        } else if (path.equals("..")) {
            File current = new File(CLI.currentDir);
            File parent = current.getParentFile();
            if (parent != null) {
                CLI.currentDir = parent.getAbsolutePath();
            }else {
                System.out.println("Your in the root directory!.");
                return true;
            }
        } else {
            File newDir = new File(CLI.currentDir, path);

            if (newDir.exists() && newDir.isDirectory()) {
                CLI.currentDir = newDir.getAbsolutePath();

            } else {
                System.out.println("Directory not found: " + path);
                return true;
            }
        }
        System.out.println("Current directory: " + CLI.currentDir);
        return true;
    }
}
