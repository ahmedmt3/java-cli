package com.cli;

import com.cli.commands.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class CommandHandler {

    public boolean executeCommand(String input) {
        // Split input for piping
        String[] parts = input.split("\\|");
        String commandPart = parts[0].trim();
        String filePath = null;
        boolean isAppend = false;

        // Check for redirection in the first command
        if (commandPart.contains(">>")) {
            String[] redirectionParts = commandPart.split(">>");
            commandPart = redirectionParts[0].trim();
            filePath = redirectionParts[1].trim();
            isAppend = true;
        } else if (commandPart.contains(">")) {
            String[] redirectionParts = commandPart.split(">");
            commandPart = redirectionParts[0].trim();
            filePath = redirectionParts[1].trim();
        }

        // If piping is present, handle piping
        if (parts.length > 1) {
            return handlePiping(parts);
        }

        // Execute single command
        return executeSingleCommand(commandPart, filePath, isAppend);
    }

    private boolean executeSingleCommand(String commandPart, String filePath, boolean isAppend) {
        String[] commandParts = commandPart.split(" ", 2);
        String command = commandParts[0];
        String argument = commandParts.length > 1 ? commandParts[1] : null;

        // Execute based on command type
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
            case "echo":
                return handleEcho(commandParts, filePath, isAppend);
            default:
                System.out.println("Command not found: " + command);
                return true;
        }
    }

    private boolean handleEcho(String[] commandParts, String filePath, boolean isAppend) {
        String output = commandParts.length > 1 ? commandParts[1] : "";
        if (filePath != null) {
            return new RedirectOutput().execute(output, filePath, isAppend);
        } else {
            System.out.println(output); // Print to console if no redirection
            return true;
        }
    }

    private boolean handlePiping(String[] commandParts) {
        String output = null;

        for (int i = 0; i < commandParts.length; i++) {
            String commandPart = commandParts[i].trim();
            String[] cmdArgs = commandPart.split(" ", 2);
            String command = cmdArgs[0];
            String argument = cmdArgs.length > 1 ? cmdArgs[1] : null;

            // Capture output of intermediate commands
            if (i < commandParts.length - 1) {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                PrintStream originalOut = System.out;
                System.setOut(new PrintStream(outputStream));

                // Execute intermediate command
                switch (command) {
                    case "pwd":
                        new pwd().execute();
                        break;
                    case "ls":
                        new ls().execute(argument);
                        break;
                    case "echo":
                        System.out.println(argument);
                        break;
                    default:
                        System.out.println("Command not found: " + command);
                        System.setOut(originalOut);
                        return true;
                }

                // Restore original output and capture intermediate output
                System.setOut(originalOut);
                output = outputStream.toString().trim(); // Store output for next command
            } else {
                // Last command in the pipe sequence
                if (output != null && command.equals("echo")) {
                    System.out.println(output);
                } else {
                    // Handle the last command normally
                    switch (command) {
                        case "echo":
                            System.out.println(argument);
                            break;
                        default:
                            System.out.println("Command not found: " + command);
                            return true;
                    }
                }
            }
        }

        return true; // Continue running the CLI
    }
}
