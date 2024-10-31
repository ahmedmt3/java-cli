package com.cli.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class cat {
    public boolean execute(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("cat: " + fileName + ": No such file");
            return false;
        }
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
            return false;
        }
    }
}

