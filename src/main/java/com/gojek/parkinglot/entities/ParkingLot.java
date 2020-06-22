package com.gojek.parkinglot.entities;

import java.util.List;
import java.util.Optional;

/**
 * @author : gaurav.ss
 * @since : 22/06/20
 **/
public interface ParkingLot {

    // each Parking level must have these behaviours
    public Optional<Ticket> park(Vehicle vehicle);
    public void leave(int slot);
    public int getAvailableSlotsCount();
    public List<String> getStatus();

}
