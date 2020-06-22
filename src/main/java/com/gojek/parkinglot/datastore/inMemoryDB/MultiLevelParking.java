package com.gojek.parkinglot.datastore.inMemoryDB;

import com.gojek.parkinglot.core.ParkingStratergy;
import com.gojek.parkinglot.entities.ParkingLevel;

import java.util.HashMap;
import java.util.Map;

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

}
