package com.cli.project;
import java.util.Scanner;
import com.cli.project.CommandHandler;
import com.cli.project.commands.*;

public class CLI {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // command handler
        CommandHandler commandHandler = new CommandHandler();

        System.out.println("welcome to our CLI Type help To get the list of commands");
        while(true){
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            System.out.println(input);
            if (!commandHandler.excuteCommand(input)){
                break;
            }
        }
    }
}