package com.gojek.parkinglot;


import com.gojek.parkinglot.commandInterpretor.CommandProcessor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * @author : gaurav.ss
 * @since : 22/06/20
 **/
public class Application {

    private static CommandProcessor processor;

    private static void executeCammandsFromAFile(String filePath) {
        try {
            File commandFile = new File(filePath);
            BufferedReader bufferReader = new BufferedReader(new FileReader(commandFile));
            String command = null;
            int lineNumber=1;
            while ((command = bufferReader.readLine()) != null) {
                command=command.trim();
                if(processor.validateCommand(command)){  // validate each command before executing it
                    System.out.println("Valid command: " +command);
                }else{
                    System.out.println("ERROR: You have entered a wrong command: {}" + command + " at line: {}" + lineNumber);
                }

                lineNumber++;
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

    }


    public static void main(String[] args) {
        System.out.println("Staring Gojek Assignment: 09:36am");
        processor =new CommandProcessor();
        //if args has a fileName then read input commands from file, else prompt a shell for command execution
        if (args.length == 1) { // we have a file to read commands from
            executeCammandsFromAFile(args[0]);
        }
        else
        if(args.length==0){ // we have to prompt interactive shell

        }else{
            System.out.println("Please go through ReadMe to run this code");
        }
    }
}
