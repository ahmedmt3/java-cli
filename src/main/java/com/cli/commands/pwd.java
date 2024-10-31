package com.cli.commands;


import com.cli.CLI;

public class pwd {
    public boolean execute() {
        System.out.println(CLI.currentDir);
        return true;
    }
}
