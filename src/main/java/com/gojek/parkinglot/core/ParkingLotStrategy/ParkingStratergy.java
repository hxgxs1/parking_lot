package com.gojek.parkinglot.core.ParkingLotStrategy;

import com.gojek.parkinglot.entities.Slot;

import java.util.Map;

/**
 * @author : gaurav.ss
 * @since : 22/06/20
 **/
// we would need multiple slot allocation stratergies
public interface ParkingStratergy {


    public Integer getFreeSlot(Map<Integer, Slot> slots);

}
