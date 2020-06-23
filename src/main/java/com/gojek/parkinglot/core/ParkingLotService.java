package com.gojek.parkinglot.core;

import com.gojek.parkinglot.datastore.inMemoryDB.MultiLevelParking;
import com.gojek.parkinglot.entities.Ticket;
import com.gojek.parkinglot.entities.Vehicle;
import com.gojek.parkinglot.exception.ParkingLotError;
import com.gojek.parkinglot.exception.ParkinglotException;
import com.gojek.parkinglot.exception.Error;

import java.util.List;
import java.util.Optional;


/**
 * @author : gaurav.ss
 * @since : 22/06/20
 **/
public class ParkingLotService {

    private MultiLevelParking multiLevelParking;

    public ParkingLotService(){

    }

    public String createParkingLot(int level , int capacity, ParkingStratergy stratergy) throws ParkinglotException {
        multiLevelParking = MultiLevelParking.getInstance();
        if(multiLevelParking.getParkingLevelMap().containsKey(level)){
            throwParkingLotException(ParkingLotError.PARKIGN_LOT_ALREADY_EXISTS);
        }

        multiLevelParking.addParkingLot(level, capacity, stratergy);
        System.out.println("Created a parking lot with "+  capacity +" slots");
        return "Created a parking lot with "+  capacity +" slots";
    }

    public Optional<Ticket> park(int level, Vehicle vehicle) throws ParkinglotException{
        if(multiLevelParking==null){  //Todo: make a separate function to check this in each command
            throwParkingLotException(ParkingLotError.PARKING_LOT_DOES_NOT_EXIST);
        }
        try {
            Optional<Ticket> ticketOpt = multiLevelParking.park(level, vehicle);
            ticketOpt.ifPresent(ticket -> System.out.println("Allocated slot number:" + ticket.getSlot()));
            return ticketOpt;
        }catch(ParkinglotException e){
            System.out.println(e.getError().getErrorMsg());
            return Optional.empty();
        }
    }


    public Optional<Integer>  leave(int level, int slot) throws ParkinglotException{

        if(multiLevelParking==null){
            throwParkingLotException(ParkingLotError.PARKING_LOT_DOES_NOT_EXIST);
        }
        try {
            Optional<Integer> slotOpt = multiLevelParking.leave(level, slot);
            if (slotOpt.isPresent()) {
                System.out.println("Slot number " + slot + " is free");
            }
            return slotOpt;
        }catch (ParkinglotException e){
            System.out.println(e.getError().getErrorMsg());
        }
        return Optional.empty();
    }

    public List<String> getStatus(int level) throws ParkinglotException{
        if(multiLevelParking==null){
            throwParkingLotException(ParkingLotError.PARKING_LOT_DOES_NOT_EXIST);
        }
        List<String> ans= multiLevelParking.getStatus(level);
        if(ans.size()==1){
            System.out.println("Parking-lot is empty");
        }else{
            for(String s: ans){
                System.out.println(s);
            }
        }
        return ans;
    }

    public List<String> getRegistrationNumsForColour(int level, String color) throws ParkinglotException{
        if(multiLevelParking==null){
            throwParkingLotException(ParkingLotError.PARKING_LOT_DOES_NOT_EXIST);
        }
        List<String> regisNums= multiLevelParking.getRegistrationNumsForColour(level, color);
        if(regisNums.size()==0)
            System.out.println("No cars parked with this color");
        else{
            StringBuilder str=new StringBuilder();
            for(String regisNum: regisNums){
                str.append(regisNum+",");
            }
            str.setLength(str.length()-1);
            System.out.println(str);
        }
        return regisNums;
    }

    public Optional<Integer> slotForRegistrationNumber(int level, String registrationNUm) throws ParkinglotException{
        if(multiLevelParking==null){
            throwParkingLotException(ParkingLotError.PARKING_LOT_DOES_NOT_EXIST);
        }
        Optional<Integer> slot=multiLevelParking.getSlotForRegistrationNumber(level, registrationNUm);
        if(slot.isPresent()){
            System.out.println(slot.get());
        }else{
            System.out.println("Not Found");
        }
        return slot;
    }

    public List<Integer> getSlotsForVehicleColour(int level, String color) throws ParkinglotException{
        if(multiLevelParking==null){
            throwParkingLotException(ParkingLotError.PARKING_LOT_DOES_NOT_EXIST);
        }
        List<Integer> slots=multiLevelParking.getSlotsForVehicleColour(level, color);
        if(slots.size()==0){
            System.out.println("Sorry, No vehicles of this color has been parked");
        }else{
            StringBuilder str=new StringBuilder();
            for(Integer regisNum: slots){
                str.append(regisNum).append(",");
            }
            str.setLength(str.length()-1);
            System.out.println(str);
        }
        return slots;

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
