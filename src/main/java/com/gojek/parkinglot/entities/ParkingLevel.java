package com.gojek.parkinglot.entities;

import com.gojek.parkinglot.core.ParkingStratergy;
import java.util.*;

/**
 * @author : gaurav.ss
 * @since : 22/06/20
 **/
public class ParkingLevel implements ParkingLot {


    private int capapcity;
    private int level;
    private int availableSlots;
    private ParkingStratergy parkingStratergy;
    private List<Ticket> tickets;
    private Map<Integer, Slot> slotsStatus;


    public ParkingLevel(int capapcity, int level, ParkingStratergy parkingStratergy) {
        this.capapcity = capapcity;
        this.level = level;
        this.availableSlots = capapcity;
        this.parkingStratergy = parkingStratergy;
        this.slotsStatus=new HashMap<>();
        this.tickets=new ArrayList<>();
        for(int i=1;i<=capapcity;i++){
            slotsStatus.put(i, new Slot(i, 0, true)); //all slots are empty initially;
        }
    }

    public int getCapapcity() {
        return capapcity;
    }

    public void setCapapcity(int capapcity) {
        this.capapcity = capapcity;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getAvailableSlots() {
        return availableSlots;
    }

    public void setAvailableSlots(int availableSlots) {
        this.availableSlots = availableSlots;
    }

    public ParkingStratergy getParkingStratergy() {
        return parkingStratergy;
    }

    public void setParkingStratergy(ParkingStratergy parkingStratergy) {
        this.parkingStratergy = parkingStratergy;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Map<Integer, Slot> getSlotsStatus() {
        return slotsStatus;
    }

    public void setSlotsStatus(Map<Integer, Slot> slotsStatus) {
        this.slotsStatus = slotsStatus;
    }

    @Override
    public String toString() {
        return "ParkingLevel{" +
                "capapcity=" + capapcity +
                ", level=" + level +
                ", availableSlots=" + availableSlots +
                ", parkingStratergy=" + parkingStratergy +
                ", slotsStatus=" + slotsStatus +
                '}';
    }


    private Ticket generateTicket(int slot, Vehicle vehicle){
        Ticket ticket= new Ticket(vehicle.getRegistrationNumber(), vehicle.getColor(), slot, level);
        tickets.add(ticket);
        return ticket;
    }

    @Override
    public Optional<Ticket> park(Vehicle vehicle) {

        if(availableSlots==0) {
            System.out.println("Sorry, parking lot is full");
            return Optional.empty();
        }
        int slot=parkingStratergy.getFreeSlot(slotsStatus);

        if(slotsStatus.values().contains(Optional.of(vehicle))){
            System.out.println("Vehicle already present in the parking");
            return Optional.empty();
        }
        slotsStatus.get(slot).setFree(false);
        slotsStatus.get(slot).setVehicleOpt(Optional.of(vehicle));
        availableSlots--;
        return Optional.of(generateTicket(slot, vehicle));
    }

    @Override
    public void leave(int slot) {

    }

    @Override
    public int getAvailableSlotsCount() {
        return 0;
    }

    @Override
    public List<String> getStatus() {
        return null;
    }
}
