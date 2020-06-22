package com.gojek.parkinglot.entities;

import java.util.Optional;

/**
 * @author : gaurav.ss
 * @since : 22/06/20
 **/
public class Slot {

    private int number;
    private int hourlyRate;
    private boolean isFree;
    private Optional<Vehicle> vehicleOpt;


    public Slot(int number, int hourlyRate, boolean isFree) {
        this.number = number;
        this.hourlyRate = hourlyRate;
        this.isFree = isFree;
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

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    @Override
    public String toString() {
        return "Slot{" +
                "number=" + number +
                ", hourlyRate=" + hourlyRate +
                ", isFree=" + isFree +
                '}';
    }
}
