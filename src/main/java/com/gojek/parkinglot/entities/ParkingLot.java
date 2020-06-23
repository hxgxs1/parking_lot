package com.gojek.parkinglot.entities;

import com.gojek.parkinglot.exception.ParkinglotException;

import java.util.List;
import java.util.Optional;

/**
 * @author : gaurav.ss
 * @since : 22/06/20
 **/
public interface ParkingLot {

    // each Parking level must have these behaviours
    public Optional<Ticket> park(Vehicle vehicle) throws ParkinglotException;
    public Optional<Integer> leave(int slot) throws ParkinglotException;
    public int getAvailableSlotsCount();
    public List<String> getStatus();
    public List<String> getRegistrationNumsForColour(String color);
    public Optional<Integer> getSlotForRegistrationNumber(String registrationNum);
    public List<Integer> getSlotsForVehicleColour(String color);
    public List<Ticket> getAllTicketsForThisLot(); // we can put a time span as argument

}
