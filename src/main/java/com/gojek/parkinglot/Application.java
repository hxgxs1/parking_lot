package com.gojek.parkinglot;


import com.gojek.parkinglot.commandInterpretor.CommandProcessor;
import com.gojek.parkinglot.exception.ParkinglotException;

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

                    try {
                        processor.executeCommand(command);
                    }catch (ParkinglotException e){
                        System.out.println(e.getError());
                    }
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

    private static void executeCommandPrompt(){

    }


    public static void main(String[] args) {

        processor =new CommandProcessor();
        //if args has a filePath then read input commands from file, else prompt a shell for command execution
        if (args.length == 1) { // we have a file to read commands from
            executeCammandsFromAFile(args[0]);
        }
        else
        if(args.length==0){ // we have to prompt interactive shell
            executeCommandPrompt();
        }else{
            System.out.println("Please go through ReadMe to run this code");
        }
    }
}
