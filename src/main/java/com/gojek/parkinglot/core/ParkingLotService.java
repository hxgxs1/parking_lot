package com.gojek.parkinglot.core;

import com.gojek.parkinglot.datastore.inMemoryDB.MultiLevelParking;
import com.gojek.parkinglot.exception.ParkingLotError;
import com.gojek.parkinglot.exception.ParkinglotException;
import com.gojek.parkinglot.exception.Error;


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

    private void throwParkingLotException(ParkingLotError parkingLotError) throws ParkinglotException {
        Error error = new Error();
        error.setErrorCode(parkingLotError.name());
        error.setErrorMsg(parkingLotError.getErrorMsg());
        ParkinglotException exception = new ParkinglotException();
        exception.setError(error);
        throw exception;
    }
}
