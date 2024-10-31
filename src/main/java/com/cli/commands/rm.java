package com.cli.commands;

import java.io.File;

public class rm {
    public boolean execute(String fileName) {
        File file = new File(fileName);
        if (file.exists() && file.delete()) {
            System.out.println("File deleted: " + fileName);
            return true;
        } else {
            System.out.println("rm: " + fileName + ": No such file or unable to delete");
            return false;
        }
    }
}
