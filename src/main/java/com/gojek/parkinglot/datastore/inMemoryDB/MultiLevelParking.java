package com.gojek.parkinglot.datastore.inMemoryDB;

import com.gojek.parkinglot.core.MLPStrategy.DefaultZeroLevelStrategy;
import com.gojek.parkinglot.core.MLPStrategy.MLPParkingStrategy;
import com.gojek.parkinglot.core.ParkingLotStrategy.ParkingStratergy;
import com.gojek.parkinglot.entities.ParkingLevel;
import com.gojek.parkinglot.entities.Ticket;
import com.gojek.parkinglot.entities.Vehicle;
import com.gojek.parkinglot.exception.Error;
import com.gojek.parkinglot.exception.ParkingLotError;
import com.gojek.parkinglot.exception.ParkinglotException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author : gaurav.ss
 * @since : 22/06/20
 **/
public class MultiLevelParking {


    private Map<Integer, ParkingLevel> parkingLevelMap;
    private MLPParkingStrategy mlpParkingStrategy;

    private static MultiLevelParking instance;


    private MultiLevelParking() {
        parkingLevelMap=new HashMap<>();
        mlpParkingStrategy=new DefaultZeroLevelStrategy();
    }

     public static MultiLevelParking getInstance(){
        if(instance==null)
            instance=new MultiLevelParking();
        return instance;
    }

    public Map<Integer, ParkingLevel> getParkingLevelMap() {
        return parkingLevelMap;
    }

    public void setParkingLevelMap(Map<Integer, ParkingLevel> parkingLevelMap) {
        this.parkingLevelMap = parkingLevelMap;
    }

    public static void setInstance(MultiLevelParking instance) {
        MultiLevelParking.instance = instance;
    }

    public void addParkingLot(int level, int capacity, ParkingStratergy stratergy) throws ParkinglotException{
        if(parkingLevelMap.containsKey(level)){
            throwParkingLotException(ParkingLotError.PARKIGN_LOT_ALREADY_EXISTS);
        }
        parkingLevelMap.put(level, new ParkingLevel(capacity, level, stratergy));
    }

    public Optional<Ticket> park(Vehicle vehicle) throws ParkinglotException{
        int level =mlpParkingStrategy.getParkingLevel(parkingLevelMap);
        return parkingLevelMap.get(level).park(vehicle);
    }

    public Optional<Integer> leave(int slot) throws ParkinglotException{
        int level =mlpParkingStrategy.getParkingLevel(parkingLevelMap);
        return parkingLevelMap.get(level).leave(slot);
    }

    public List<String> getStatus(){
        int level =mlpParkingStrategy.getParkingLevel(parkingLevelMap);
        return parkingLevelMap.get(level).getStatus();
    }

    public List<String> getRegistrationNumsForColour(String color){
        int level =mlpParkingStrategy.getParkingLevel(parkingLevelMap);
        return parkingLevelMap.get(level).getRegistrationNumsForColour(color);
    }

    public Optional<Integer> getSlotForRegistrationNumber(String registrationNum){
        int level =mlpParkingStrategy.getParkingLevel(parkingLevelMap);
        return parkingLevelMap.get(level).getSlotForRegistrationNumber(registrationNum);
    }

    public List<Integer> getSlotsForVehicleColour(String color){
        int level =mlpParkingStrategy.getParkingLevel(parkingLevelMap);
        return parkingLevelMap.get(level).getSlotsForVehicleColour(color);
    }

    public int getAvailableSlots(){
        int level =mlpParkingStrategy.getParkingLevel(parkingLevelMap);
        return parkingLevelMap.get(level).getAvailableSlotsCount();
    }

    public boolean doesLevelExists(){
        int level =mlpParkingStrategy.getParkingLevel(parkingLevelMap);
        return parkingLevelMap.containsKey(level);
    }

    public void cleanUp(){
        for(ParkingLevel level: parkingLevelMap.values()){
            level.cleanUp();
        }
        parkingLevelMap=null;
        instance=null;
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
