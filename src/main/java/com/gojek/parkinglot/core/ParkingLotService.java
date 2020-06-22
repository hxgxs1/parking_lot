package com.gojek.parkinglot.core;

import com.gojek.parkinglot.datastore.inMemoryDB.MultiLevelParking;
import com.gojek.parkinglot.entities.Ticket;
import com.gojek.parkinglot.entities.Vehicle;
import com.gojek.parkinglot.exception.ParkingLotError;
import com.gojek.parkinglot.exception.ParkinglotException;
import com.gojek.parkinglot.exception.Error;

import java.util.Optional;


/**
 * @author : gaurav.ss
 * @since : 22/06/20
 **/
public class ParkingLotService {
    private MultiLevelParking multiLevelParking;

    public ParkingLotService(){
        multiLevelParking = MultiLevelParking.getInstance();
    }

    public String createParkingLot(int level , int capacity, ParkingStratergy stratergy) throws ParkinglotException {

        if(multiLevelParking.getParkingLevelMap().containsKey(level)){
            throwParkingLotException(ParkingLotError.PARKIGN_LOT_ALREADY_EXISTS);
        }
        multiLevelParking.addParkingLot(level, capacity, stratergy);
        System.out.println("Created a parking lot with "+  capacity +" slots");
        return "Created a parking lot with "+  capacity +" slots";
    }

    public Optional<Ticket> park(int level, Vehicle vehicle) throws ParkinglotException{
        if(multiLevelParking==null){
            throwParkingLotException(ParkingLotError.PARKING_LOT_DOES_NOT_EXIST);
        }

        Optional<Ticket> ticketOpt= multiLevelParking.park(level, vehicle);
        ticketOpt.ifPresent(ticket -> System.out.println("Allocated slot number:" + ticket.getSlot()));
        return ticketOpt;
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
