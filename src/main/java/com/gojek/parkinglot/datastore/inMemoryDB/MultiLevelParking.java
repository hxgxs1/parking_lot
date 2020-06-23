package com.gojek.parkinglot.datastore.inMemoryDB;

import com.gojek.parkinglot.core.ParkingStratergy;
import com.gojek.parkinglot.entities.ParkingLevel;
import com.gojek.parkinglot.entities.Ticket;
import com.gojek.parkinglot.entities.Vehicle;
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

    private static MultiLevelParking instance;


    private MultiLevelParking() {
        parkingLevelMap=new HashMap<>();
    }

    synchronized public static MultiLevelParking getInstance(){
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

    public void addParkingLot(int level, int capacity, ParkingStratergy stratergy){
        parkingLevelMap.put(level, new ParkingLevel(capacity, level, stratergy));
    }

    public Optional<Ticket> park(int level, Vehicle vehicle) throws ParkinglotException{
        return parkingLevelMap.get(level).park(vehicle);
    }

    public Optional<Integer> leave(int level, int slot) throws ParkinglotException{
        return parkingLevelMap.get(level).leave(slot);
    }

    public List<String> getStatus(int level){
        return parkingLevelMap.get(level).getStatus();
    }

    public List<String> getRegistrationNumsForColour(int level, String color){
        return parkingLevelMap.get(level).getRegistrationNumsForColour(color);
    }

    public Optional<Integer> getSlotForRegistrationNumber(int level, String registrationNum){
        return parkingLevelMap.get(level).getSlotForRegistrationNumber(registrationNum);
    }

    public List<Integer> getSlotsForVehicleColour(int level, String color){
        return parkingLevelMap.get(level).getSlotsForVehicleColour(color);
    }

}
