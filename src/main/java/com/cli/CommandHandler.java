package com.cli;

import com.cli.commands.*;

public class CommandHandler {

    public boolean executeCommand(String input) {
        String[] parts = input.split(" ", 3);
        String command = parts[0];
        String argument = parts.length > 1 ? parts[1] : null;
        String additionalArgument = parts.length > 2 ? parts[2] : null;

        switch (command) {
            case "exit":
                return new exit().execute();
            case "help":
                return new help().execute();
            case "pwd":
                return new pwd().execute();
            case "cd":
                return new cd().execute(argument);
            case "mkdir":
                return new mkdir().execute(argument);
            case "rmdir":
                return new rmdir().execute(argument);
            case "ls":
                return new ls().execute(argument);
            case "mv":
                if (argument != null && additionalArgument != null) {
                    return new mv().execute(argument, additionalArgument);
                } else {
                    System.out.println("mv: missing file operands. Usage: mv <source> <destination>");
                    return true;
                }
            default:
                System.out.println("Command not found: " + command);
                return true;
        }
    }
}
