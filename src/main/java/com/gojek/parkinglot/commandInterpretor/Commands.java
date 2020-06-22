package com.gojek.parkinglot.commandInterpretor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : gaurav.ss
 * @since : 22/06/20
 **/
public class Commands {

    //stores commands and corresponding input params required
    private static Map<String, Integer> commands;

    public Commands(){
        commands=new HashMap<String, Integer>();

        commands.put("create_parking_lot", 1);
        commands.put("park", 2);
        commands.put("leave", 1);
        commands.put("status", 0);
        commands.put("registration_numbers_for_cars_with_colour", 1);
        commands.put("slot_numbers_for_cars_with_colour", 1);
        commands.put("slot_number_for_registration_number", 1);
    }

    public  Map<String, Integer> getCommands(){
        return commands;
    }


    public void addCommand(String command, Integer params){
        commands.put(command, params);
    }
}
