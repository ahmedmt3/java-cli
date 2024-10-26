package com.cli.project;

import com.cli.project.commands.*;

public class CommandHandler {

    public boolean excuteCommand(String input) {
        switch (input) {
            case "exit":
                return new exit().excute();
            case "help":
                return new help().excute();


            default:
                return false;
        }
    }
}