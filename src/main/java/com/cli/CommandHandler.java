package com.cli;

import com.cli.commands.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandHandler {

    public boolean executeCommand(String input) {
        // Split input for piping but consider quotes
        String[] parts = splitCommandByPipe(input);
        String commandPart = parts[0].trim();
        String filePath = null;
        boolean isAppend = false;

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

        if (parts.length > 1) {
            return handlePiping(parts);
        }

        return executeSingleCommand(commandPart, filePath, isAppend);
    }

    private boolean executeSingleCommand(String commandPart, String filePath, boolean isAppend) {
        // Use regex to match command and arguments correctly, accounting for quotes
        Pattern pattern = Pattern.compile("(\"[^\"]*\"|\\S+)");
        Matcher matcher = pattern.matcher(commandPart);
        List<String> commandParts = new ArrayList<>();

        while (matcher.find()) {
            commandParts.add(matcher.group().replaceAll("^\"|\"$", "")); // Remove surrounding quotes
        }

        if (commandParts.isEmpty()) {
            System.out.println("Command not found: " + commandPart);
            return false;
        }

        String command = commandParts.get(0);
        String argument = commandParts.size() > 1 ? commandParts.get(1) : null;
        String additionalArgument = commandParts.size() > 2 ? commandParts.get(2) : null;

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
                    return false;
                }
            case "echo":
                return handleEcho(commandParts, filePath, isAppend);
            case "touch":
                return new touch().execute(argument);
            case "cat":
                return new cat().execute(argument);
            case "rm":
                return new rm().execute(argument);
            default:
                System.out.println("Command not found: " + command);
                return false;
        }
    }

    private boolean handleEcho(List<String> commandParts, String filePath, boolean isAppend) {
        // Join all arguments after the first one to support multiple words in echo
        String output = String.join(" ", commandParts.subList(1, commandParts.size()));
        if (filePath != null) {
            return new RedirectOutput().execute(output, filePath, isAppend);
        } else {
            System.out.println(output);
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

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PrintStream originalOut = System.out;
            System.setOut(new PrintStream(outputStream));

            // Execute each command in the pipeline
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
                case "cat":
                    new cat().execute(argument != null ? argument : output);
                    break;
                case "rm":
                    new rm().execute(argument);
                    break;
                case "touch":
                    new touch().execute(argument);
                    break;
                default:
                    System.out.println("Command not found: " + command);
                    System.setOut(originalOut);
                    return false;
            }

            System.setOut(originalOut);
            output = outputStream.toString().trim();

            // Set up the next command to receive this output
            if (i < commandParts.length - 1 && command.equals("echo")) {
                System.out.println(output);
            }
        }

        return true;
    }

    public String[] splitCommandByPipe(String input) {
        List<String> parts = new ArrayList<>();
        Matcher m = Pattern.compile("(\"[^\"]*\"|[^|]+)").matcher(input);

        while (m.find()) {
            parts.add(m.group().replaceAll("^\"|\"$", "")); // Remove surrounding quotes
        }

        return parts.toArray(new String[0]);
    }
}
