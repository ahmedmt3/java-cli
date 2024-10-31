package com.cli.commands;

public class help {
    public boolean execute(){
        System.out.println("Command Line Interpreter Help\n");
        System.out.println("Available Commands:");
        System.out.println("1.  exit\t\t - Terminates the current session.");
        System.out.println("2.  pwd\t\t - Prints the current working directory.");
        System.out.println("3.  cd\t\t <directory> - Changes the current directory to the specified <directory>.");
        System.out.println("4.  ls\t\t - Lists files and directories in the current directory.");
        System.out.println("5.  ls\t\t -a - Lists all files, including hidden files, in the current directory.");
        System.out.println("6.  ls\t\t -r - Lists files and directories in reverse order.");
        System.out.println("7.  mkdir\t <directory> - Creates a new directory with the specified <directory> name.");
        System.out.println("8.  rmdir\t <directory> - Removes the specified <directory> if it's empty.");
        System.out.println("9.  touch\t <file> - Creates a new empty file with the specified <file> name.");
        System.out.println("10. mv\t\t <source> <destination> - Moves or renames a file or directory.");
        System.out.println("11. rm\t\t <file> - Deletes the specified <file>.");
        System.out.println("12. cat\t\t <file> - Displays the contents of the specified <file>.");
        System.out.println("13. >\t\t - Redirects output to a file, overwriting it.");
        System.out.println("14. >>\t\t - Redirects output to a file, appending to it.");
        System.out.println("15. |\t\t - Pipes the output of one command as input to another command.\n");
        return true;
    }
}
