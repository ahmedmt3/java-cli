package com.cli.project;

import com.cli.project.commands.*;

public class CommandHandler {

    public boolean executeCommand(String input) {
        switch (input) {
            case "exit":
                return new exit().excute();
            case "help":
                return new help().excute();

            case "cd":
                //
                return new cd().execute();

            default:
                return false;
        }
    }
}