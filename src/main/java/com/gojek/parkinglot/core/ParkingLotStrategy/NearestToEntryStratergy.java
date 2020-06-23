package com.gojek.parkinglot.core.ParkingLotStrategy;

import com.gojek.parkinglot.core.ParkingLotStrategy.ParkingStratergy;
import com.gojek.parkinglot.entities.Slot;

import java.util.Map;
import java.util.TreeSet;

/**
 * @author : gaurav.ss
 * @since : 22/06/20
 **/
public class NearestToEntryStratergy implements ParkingStratergy {



    public NearestToEntryStratergy(){
    }
    public Integer getFreeSlot(Map<Integer, Slot> slots){
        Integer slot=0;
        TreeSet<Integer> slotNumbers= new TreeSet<>(slots.keySet());
        for(Integer slotNumber: slotNumbers){
           if(!slots.get(slotNumber).getVehicleOpt().isPresent())
               return slotNumber;
        }
        return slot;
    }

}
