package com.gojek.parkinglot.commandInterpretor;

import java.util.Set;

/**
 * @author : gaurav.ss
 * @since : 22/06/20
 **/

public class CommandProcessor {

    private Commands commands;
    public CommandProcessor(){
        commands =new Commands();
    }

    private boolean checkValidCommandName(Set<String> commands, String inputCommans){
        if(commands.contains(inputCommans))
            return true;
        else
            return false;
    }

    public boolean validateCommand(String command){

        try {
            String[] commandParts = command.split(" ");  //Todo: this can fail put try catch here
            //check if command name is fine
            if (!checkValidCommandName(commands.getCommands().keySet(), commandParts[0])) {
                return false;
            }

            //check if number of params are fine
            if (commandParts.length - 1 == commands.getCommands().get(commandParts[0])) {
                return true;
            } else
                return false;
        }catch (Exception e){
            return false;
        }
    }


    public void executeCommand(String inputCommand){ }
}
