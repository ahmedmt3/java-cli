package com.cli;

import com.cli.commands.*;

public class CommandHandler {

    public boolean executeCommand(String input) {
        String[] parts = input.split(" ", 2);
        String command = parts[0];
        String argument = parts.length > 1 ? parts[1] : null;  // Get arg. if available

        switch (command) {
            case "exit":
                return new exit().excute();
            case "help":
                return new help().excute();
            case "pwd":
                return new pwd().execute();

            case "cd":
                //
                return new cd().execute(argument);

            default:
                return false;
        }
    }
}