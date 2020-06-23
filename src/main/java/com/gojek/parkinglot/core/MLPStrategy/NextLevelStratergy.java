package com.gojek.parkinglot.core.MLPStrategy;

import com.gojek.parkinglot.entities.ParkingLevel;

import java.util.Map;
import java.util.TreeSet;

/**
 * @author : gaurav.ss
 * @since : 23/06/20
 **/
public class NextLevelStratergy implements MLPParkingStrategy{

    // Start search for a level from bottom and find a level that this not full
    @Override
    public Integer getParkingLevel(Map<Integer, ParkingLevel> parkingLevelMap) {
        TreeSet<Integer> levels= new TreeSet<>(parkingLevelMap.keySet());
        for(int level: levels){
            if(parkingLevelMap.get(level).getAvailableSlotsCount()>0)
                return level;
        }
        return -1;
    }
}
