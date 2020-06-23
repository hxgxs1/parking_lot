package com.gojek.parkinglot.core.MLPStrategy;

import com.gojek.parkinglot.entities.ParkingLevel;

import java.util.Map;

/**
 * @author : gaurav.ss
 * @since : 23/06/20
 **/
public interface MLPParkingStrategy {

    public Integer getParkingLevel(Map<Integer, ParkingLevel> parkingLevelMap);

}
