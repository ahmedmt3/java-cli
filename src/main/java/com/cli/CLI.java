package com.cli;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

public class CLI {
    // Variables and Constants
    public static String currentDir = System.getProperty("user.dir");
    public static final String homeDir = System.getProperty("user.dir");
    // Main func.
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // command handler
        CommandHandler commandHandler = new CommandHandler();

        System.out.println("welcome to our CLI Type `help` To get the list of commands");
        while(true){
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            if (input.trim().isEmpty()){
                System.out.println("enter a valid command");
            }else {
                if (!commandHandler.executeCommand(input)){
                    break;
                }
            }

        }
    }
}