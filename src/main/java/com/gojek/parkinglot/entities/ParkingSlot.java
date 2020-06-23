package com.gojek.parkinglot.entities;

import java.util.Optional;

/**
 * @author : gaurav.ss
 * @since : 22/06/20
 **/
public abstract class ParkingSlot {
    private int number;
    private int hourlyRate;
    private Optional<Vehicle> vehicleOpt;


    public ParkingSlot(int number, int hourlyRate) {
        this.number = number;
        this.hourlyRate = hourlyRate;
        this.vehicleOpt = Optional.empty();
    }

    public Optional<Vehicle> getVehicleOpt() {
        return vehicleOpt;
    }

    public void setVehicleOpt(Optional<Vehicle> vehicleOpt) {
        this.vehicleOpt = vehicleOpt;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(int hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    @Override
    public String toString() {
        return "ParkingSlot{" +
                "number=" + number +
                ", hourlyRate=" + hourlyRate +
                ", vehicleOpt=" + vehicleOpt +
                '}';
    }
}
