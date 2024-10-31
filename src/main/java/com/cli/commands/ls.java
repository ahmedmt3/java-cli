package com.cli.commands;

import com.cli.CLI;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;

public class ls {

    public boolean execute(String option) {
        File currentDir = new File(CLI.currentDir);
        String[] contents = currentDir.list();

        if (contents == null || contents.length == 0) {
            System.out.println("Directory is empty.");
            return true;
        }

        Arrays.sort(contents);

        // ls -r
        if ("-r".equals(option)) {
            Collections.reverse(Arrays.asList(contents));
        }

        for (String item : contents) {
            // ls -a
            if ("-a".equals(option) || !item.startsWith(".")) {
                File tempfile = new File(currentDir, item);
                if (tempfile.isDirectory()) {
                    System.out.println("[DIR]  " + item);
                } else {
                    System.out.println("       " + item);
                }
            }
        }
        return true;
    }
}
