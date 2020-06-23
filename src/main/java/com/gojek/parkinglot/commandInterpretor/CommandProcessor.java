package com.gojek.parkinglot.commandInterpretor;

import com.gojek.parkinglot.core.NearestToEntryStratergy;
import com.gojek.parkinglot.core.ParkingLotService;
import com.gojek.parkinglot.entities.Car;
import com.gojek.parkinglot.exception.ParkingLotError;
import com.gojek.parkinglot.exception.Error;
import com.gojek.parkinglot.exception.ParkinglotException;

import java.util.Set;

/**
 * @author : gaurav.ss
 * @since : 22/06/20
 **/

public class CommandProcessor {

    private Commands commands;
    private ParkingLotService parkingLotService;

    public CommandProcessor(){
        commands =new Commands();
        parkingLotService=new ParkingLotService();

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


    private void createParkingLot(Integer capacity) throws ParkinglotException {
        int level=0; // by default ground floor is chosen as a level
        parkingLotService.createParkingLot(level, capacity, new NearestToEntryStratergy());
    }

    public void executeCommand(String inputCommand) throws ParkinglotException{

        String[] parts=inputCommand.split(" ");
        String command=parts[0];
        int level=0;
        if(command.equals("create_parking_lot")){
            try {
                int capacity = Integer.parseInt(parts[1]);
                createParkingLot(capacity);
            }catch(NumberFormatException e){
                throwParkingLotException(ParkingLotError.WRONG_PARAMETER);
            }

        }
        if(command.equals("park")){

            String regisNumber=parts[1];
            String color=parts[2];
            if(parts[1].isEmpty() || parts[2].isEmpty()){
                throwParkingLotException(ParkingLotError.WRONG_PARAMETER);
            }
            parkingLotService.park(level, new Car(regisNumber, color));

        }

        if(command.equals("status")){
            parkingLotService.getStatus(level);
        }

        if(command.equals("leave")){
            try {
                int slot = Integer.parseInt(parts[1]);
                parkingLotService.leave(level, slot);
            }catch (NumberFormatException e){
                throwParkingLotException(ParkingLotError.WRONG_PARAMETER);
            }
        }

        if(command.equals("registration_numbers_for_cars_with_colour")){
            String color=parts[1];
            if(parts[1].isEmpty()){
                throwParkingLotException(ParkingLotError.WRONG_PARAMETER);
            }
            parkingLotService.getRegistrationNumsForColour(level, color);
        }
        if(command.equals("slot_number_for_registration_number")){

            String registrationNum=parts[1];
            if(parts[1].isEmpty()){
                throwParkingLotException(ParkingLotError.WRONG_PARAMETER);
            }
            parkingLotService.slotForRegistrationNumber(level, registrationNum);
        }
        if(command.equals("slot_numbers_for_cars_with_colour")){
            String color=parts[1];
            if(parts[1].isEmpty()){
                throwParkingLotException(ParkingLotError.WRONG_PARAMETER);
            }
            parkingLotService.getSlotsForVehicleColour(level, color);
        }
    }


    private void throwParkingLotException(ParkingLotError parkingLotError) throws ParkinglotException {
        Error error = new Error();
        error.setErrorCode(parkingLotError.name());
        error.setErrorMsg(parkingLotError.getErrorMsg());
        ParkinglotException exception = new ParkinglotException();
        exception.setError(error);
        throw exception;
    }
}
