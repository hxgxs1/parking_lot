package com.gojek.parkinglot.entities;

import com.gojek.parkinglot.core.ParkingLotStrategy.ParkingStratergy;
import com.gojek.parkinglot.exception.Error;
import com.gojek.parkinglot.exception.ParkingLotError;
import com.gojek.parkinglot.exception.ParkinglotException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author : gaurav.ss
 * @since : 22/06/20
 **/
public class ParkingLevel implements ParkingLot {


    private int capapcity;
    private int level;
    private ParkingStratergy parkingStratergy;
    private List<Ticket> tickets;
    private Map<Integer, Slot> slots;


    public ParkingLevel(int capapcity, int level, ParkingStratergy parkingStratergy) {
        this.capapcity = capapcity;
        this.level = level;
        this.parkingStratergy = parkingStratergy;
        this.slots=new HashMap<>();
        this.tickets=new ArrayList<>();
        for(int i=1;i<=capapcity;i++){
            slots.put(i, new Slot(i, 0));
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
        return slots;
    }

    public void setSlotsStatus(Map<Integer, Slot> slotsStatus) {
        this.slots = slotsStatus;
    }

    @Override
    public String toString() {
        return "ParkingLevel{" +
                "capapcity=" + capapcity +
                ", level=" + level +
                ", parkingStratergy=" + parkingStratergy +
                ", slotsStatus=" + slots +
                '}';
    }


    private Ticket generateTicket(int slot, Vehicle vehicle){
        Ticket ticket= new Ticket(vehicle.getRegistrationNumber(), vehicle.getColor(), slot, level);
        tickets.add(ticket);
        return ticket;
    }

    private boolean checkIfVehicleAlreadyPresent(Vehicle vehicle){
        int count = (int)slots.values()
                .stream()
                .filter(x-> x.getVehicleOpt().isPresent() && x.getVehicleOpt().get().getRegistrationNumber().toLowerCase().equals(vehicle.getRegistrationNumber().toLowerCase()))
                .count();
        if(count>0)
            return true;
        return false;
    }

    @Override
    public Optional<Ticket> park(Vehicle vehicle) throws ParkinglotException {

        if(getAvailableSlotsCount()==0) {
            throwParkingLotException(ParkingLotError.PARKING_IS_FULL);
        }
        if(checkIfVehicleAlreadyPresent(vehicle)){
            throwParkingLotException(ParkingLotError.VEHICLE_ALREADY_PRESENT_IN_LOT);
        }
        int slot=parkingStratergy.getFreeSlot(slots);

        if(slots.values().contains(Optional.of(vehicle))){
            throwParkingLotException(ParkingLotError.VEHICLE_ALREADY_PARKED_ON_THIS_SLOT);
        }
        slots.get(slot).setVehicleOpt(Optional.of(vehicle));
        return Optional.of(generateTicket(slot, vehicle));
    }

    @Override
    public Optional<Integer> leave(int slot) throws ParkinglotException{

        if(slots.size() < slot) {
            throwParkingLotException(ParkingLotError.INVALID_SLOT);
        }

        if(!slots.get(slot).getVehicleOpt().isPresent()) {
            throwParkingLotException(ParkingLotError.SLOT_IS_EMPTY);
        }
        slots.get(slot).setVehicleOpt(Optional.empty());
        return Optional.of(slot);
    }


    @Override
    public int getAvailableSlotsCount() {

        return (int) slots.entrySet()
                .stream()
                .filter(x-> !x.getValue().getVehicleOpt().isPresent()).count();
    }



    @Override
    public List<String> getRegistrationNumsForColour(String color) {

        List<String> regNums  = slots.values()
                .stream()
                .filter(x-> x.getVehicleOpt().isPresent() && x.getVehicleOpt().get().getColor().toLowerCase().equals(color.toLowerCase()))
                .map(x-> x.getVehicleOpt().get().getRegistrationNumber())
                .collect(Collectors.toList());
        return regNums;
    }

    @Override
    public Optional<Integer> getSlotForRegistrationNumber(String registrationNum) {

        for(Map.Entry<Integer, Slot> entry: slots.entrySet()){
            if(entry.getValue().getVehicleOpt().isPresent()){
                Vehicle vehicle=entry.getValue().getVehicleOpt().get();
                if(vehicle.getRegistrationNumber().toLowerCase().equals(registrationNum.toLowerCase()))
                    return Optional.of(entry.getKey());
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Integer> getSlotsForVehicleColour(String color) {

        List<Integer> coloredSlots = slots.entrySet()
                .stream()
                .filter(x-> x.getValue().getVehicleOpt().isPresent() && x.getValue().getVehicleOpt().get().getColor().toLowerCase().equals(color.toLowerCase()))
                .map(x-> x.getKey())
                .collect(Collectors.toList());
        return coloredSlots;
    }

    @Override
    public List<Ticket> getAllTicketsForThisLot() {
        return tickets;
    }


    @Override
    public List<String> getStatus(){
        List<String> status=new ArrayList<>();
        status.add("Slot No."+ "    "+ "Registration No" + "    "+ "Colour");
        for(int i=1;i<=capapcity;i++){
            if(slots.get(i).getVehicleOpt().isPresent()){
                Vehicle vehicle= slots.get(i).getVehicleOpt().get();
                status.add(i +"     " + vehicle.getRegistrationNumber() + "    "+ vehicle.getColor());
            }
        }
        return status;
    }

    private void throwParkingLotException(ParkingLotError parkingLotError) throws ParkinglotException {
        Error error = new Error();
        error.setErrorCode(parkingLotError.name());
        error.setErrorMsg(parkingLotError.getErrorMsg());
        ParkinglotException exception = new ParkinglotException();
        exception.setError(error);
        throw exception;
    }

    public void cleanUp(){
        capapcity=0;
        level=-1;
        parkingStratergy=null;
        slots=null;
        tickets=null;
    }
}
