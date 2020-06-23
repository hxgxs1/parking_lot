package com.gojek.parkinglot.core;

import com.gojek.parkinglot.core.ParkingLotStrategy.NearestToEntryStratergy;
import com.gojek.parkinglot.entities.Car;
import com.gojek.parkinglot.entities.Ticket;
import com.gojek.parkinglot.exception.ParkingLotError;
import com.gojek.parkinglot.exception.ParkinglotException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import javax.swing.text.html.Option;
import java.util.Optional;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.*;

/**
 * @author : gaurav.ss
 * @since : 22/06/20
 **/
public class ParkingLotServiceTest {

    int level;
    private final ByteArrayOutputStream output	= new ByteArrayOutputStream();


    @Before
    public void init(){
        System.setOut(new PrintStream(output));
        level=0;
    }

    @Test
    public void createParkingLot() throws ParkinglotException {
        ParkingLotService service=new ParkingLotService();
        String str=service.createParkingLot(level, 6, new NearestToEntryStratergy());
        assertTrue("created a parking lot with 6 slots".equalsIgnoreCase(str.trim()));
        service.cleanUp();
    }

    @Test
    public void testCreatingMultipleLots() throws ParkinglotException{
        ParkinglotException exception=null;
        ParkingLotService service=new ParkingLotService();

        try { // creating multiple lots on same level should result in exception
            service.createParkingLot(level, 6, new NearestToEntryStratergy());
            service.createParkingLot(level, 10, new NearestToEntryStratergy());
        }catch (ParkinglotException e){
            exception=e;
        }
        assertNotNull(exception);
        assertSame(ParkinglotException.class, exception.getClass());
        assertSame(ParkingLotError.PARKIGN_LOT_ALREADY_EXISTS.getErrorMsg(), exception.getError().getErrorMsg());
        service.createParkingLot(2, 15, new NearestToEntryStratergy()); //created a new Level
        service.cleanUp();
    }


    @Test
    public void testParkingWithoutCreatingLot() {
        Throwable caught = null;
        ParkinglotException exception=null;
        ParkingLotService service=new ParkingLotService();
        try {
            service.park(new Car("KA-01-HH-1234", "White"));
        }catch(Exception e){
            caught=e;
            exception=(ParkinglotException)e;
        }
        assertNotNull(caught);
        assertSame(ParkinglotException.class, caught.getClass());
        assertSame(ParkingLotError.PARKING_LOT_DOES_NOT_EXIST.getErrorMsg(), exception.getError().getErrorMsg());
        service.cleanUp();
    }

    @Test
    public void park() {
        Throwable caught = null;
        ParkingLotService service=new ParkingLotService();
        try {
            service.createParkingLot(level, 6, new NearestToEntryStratergy());
            service.park(new Car("KA-01-HH-1234", "White"));
        }catch(Exception e){
            caught=e;
        }
        assertNull(caught);
        service.cleanUp();
    }


    @Test
    public void parkAndCheckLotSize() throws ParkinglotException {
        ParkingLotService service=new ParkingLotService();
        service.createParkingLot(level, 6, new NearestToEntryStratergy());
        service.park( new Car("KA-01-HH-1234", "White"));
        service.park( new Car("KA-02-MM-3034", "Black"));
        service.park( new Car("KA-51-HH-1324", "Grey"));
        int remainingSlots=service.getAvailableSlots();
        assertEquals(remainingSlots, 3); //after parking 3 cars, lot must have 3 slots free
        service.cleanUp();
    }


    @Test
    public void parkAndCheckSlotIsFull() throws ParkinglotException {
        ParkingLotService service=new ParkingLotService();
        service.createParkingLot(level, 3, new NearestToEntryStratergy());
        Optional<Ticket> t1=service.park( new Car("KA-01-HH-1234", "White"));
        Optional<Ticket> t2 =service.park( new Car("KA-02-MM-3034", "Black"));
        Optional<Ticket> t3= service.park( new Car("KA-51-HH-1324", "Grey"));
        Optional<Ticket> t4= service.park( new Car("KA-50-HH-0001", "White"));
        System.out.println(t1.get());
        assertEquals(t4, Optional.empty()); //after parking 3 cars, 4th parking shouldn't take place
        service.cleanUp();
    }

    @Test
    public void leave() throws ParkinglotException {
        ParkingLotService service=new ParkingLotService();
        service.createParkingLot(level, 3, new NearestToEntryStratergy());
        Optional<Ticket> t1=service.park( new Car("KA-01-HH-1234", "White"));
        Optional<Ticket> t2 =service.park( new Car("KA-02-MM-3034", "Black"));
        Optional<Integer> slotNumber= service.leave(1);
        assertEquals(Optional.of(1), slotNumber);
        service.cleanUp();

    }

    @Test
    public void testLeavingWithoutCreatingLot(){
        Throwable caught = null;
        ParkinglotException exception=null;
        ParkingLotService service=new ParkingLotService();
        try {
            service.leave(2);
        }catch(Exception e){
            caught=e;
            exception=(ParkinglotException)e;
        }
        assertNotNull(caught);
        assertSame(ParkinglotException.class, caught.getClass());
        assertSame(ParkingLotError.PARKING_LOT_DOES_NOT_EXIST.getErrorMsg(), exception.getError().getErrorMsg());
        service.cleanUp();
    }

    @Test
    public void testLeavingWithoutParking()  throws ParkinglotException {
        ParkingLotService service=new ParkingLotService();
        service.createParkingLot(level, 3, new NearestToEntryStratergy());
        Optional<Integer> slotOpt= service.leave(2); // taking out a car from an empty slot
        assertEquals(Optional.empty(), slotOpt);
        service.cleanUp();
    }

    @Test
    public void getStatusWithoutVehiclesParked()throws ParkinglotException {
        ParkingLotService service=new ParkingLotService();
        service.createParkingLot(level, 3, new NearestToEntryStratergy());
        List<String> status =service.getStatus();
        assertEquals("Created a parking lot with 3 slots\nParking-lot is empty", output.toString().trim());
        service.cleanUp();
    }

    @Test
    public void getStatusWithVehiclesParked() throws ParkinglotException{
        ParkingLotService service=new ParkingLotService();
        service.createParkingLot(level, 3, new NearestToEntryStratergy());
        Optional<Ticket> t1=service.park( new Car("KA-01-HH-1234", "White"));
        Optional<Ticket> t2 =service.park( new Car("KA-02-MM-3034", "Black"));
        service.getStatus();
        assertEquals("Created a parking lot with 3 slots\n" +
                "Allocated slot number: 1\n" +
                "Allocated slot number: 2\n" +
                "Slot No.\tRegistration No\t\tColour\n" +
                "1\t\tKA-01-HH-1234\t\tWhite\n" +
                "2\t\tKA-02-MM-3034\t\tBlack", output.toString().trim());

    }

    @Test
    public void getRegistrationNumsForColour()throws ParkinglotException {
        ParkingLotService service=new ParkingLotService();
        service.createParkingLot(level, 10, new NearestToEntryStratergy());
        Optional<Ticket> t1=service.park( new Car("KA-01-HH-1234", "White"));
        Optional<Ticket> t2 =service.park( new Car("KA-02-MM-3034", "Black"));
        Optional<Ticket> t3= service.park( new Car("KA-51-HH-1324", "Grey"));
        Optional<Ticket> t4= service.park( new Car("KA-50-HH-0001", "White"));
        service.getRegistrationNumsForColour("white");
        //NearestSlot strategy has been tested here
        assertEquals("Created a parking lot with 10 slots\n" +
                "Allocated slot number: 1\n" +
                "Allocated slot number: 2\n" +
                "Allocated slot number: 3\n" +
                "Allocated slot number: 4\n" +
                "KA-01-HH-1234, KA-50-HH-0001", output.toString().trim());
        service.getRegistrationNumsForColour("grey");
        assertEquals("Created a parking lot with 10 slots\n" +
                "Allocated slot number: 1\n" +
                "Allocated slot number: 2\n" +
                "Allocated slot number: 3\n" +
                "Allocated slot number: 4\n" +
                "KA-01-HH-1234, KA-50-HH-0001\n" +
                "KA-51-HH-1324", output.toString().trim());
        service.getRegistrationNumsForColour("orange"); // No Vehicle for color orange
        assertEquals("Created a parking lot with 10 slots\n" +
                "Allocated slot number: 1\n" +
                "Allocated slot number: 2\n" +
                "Allocated slot number: 3\n" +
                "Allocated slot number: 4\n" +
                "KA-01-HH-1234, KA-50-HH-0001\n" +
                "KA-51-HH-1324\n" +
                "No cars parked with this color", output.toString().trim());

        service.cleanUp();

    }

    @Test
    public void slotForRegistrationNumber() throws ParkinglotException{
        ParkingLotService service=new ParkingLotService();
        service.createParkingLot(level, 10, new NearestToEntryStratergy());
        Optional<Ticket> t1=service.park( new Car("KA-01-HH-1234", "White"));
        Optional<Ticket> t2 =service.park( new Car("KA-02-MM-3034", "Black"));
        Optional<Ticket> t3= service.park( new Car("KA-51-HH-1324", "Grey"));
        Optional<Ticket> t4= service.park( new Car("KA-50-HH-0001", "White"));

        service.slotForRegistrationNumber("KA-51-HH-1324");
        assertEquals("Created a parking lot with 10 slots\n" +
                "Allocated slot number: 1\n" +
                "Allocated slot number: 2\n" +
                "Allocated slot number: 3\n" +
                "Allocated slot number: 4\n" +
                "3", output.toString().trim());
        service.slotForRegistrationNumber("KA-51-HH-8938"); //this regNum does not exist
        assertEquals("Created a parking lot with 10 slots\n" +
                "Allocated slot number: 1\n" +
                "Allocated slot number: 2\n" +
                "Allocated slot number: 3\n" +
                "Allocated slot number: 4\n" +
                "3\n" +
                "Not Found", output.toString().trim());
        service.slotForRegistrationNumber("kA-50-hh-0001"); // case insensitive
        assertEquals("Created a parking lot with 10 slots\n" +
                "Allocated slot number: 1\n" +
                "Allocated slot number: 2\n" +
                "Allocated slot number: 3\n" +
                "Allocated slot number: 4\n" +
                "3\n" +
                "Not Found\n" +
                "4", output.toString().trim());


        service.cleanUp();
    }

    @Test
    public void getSlotsForVehicleColour() throws ParkinglotException {
        ParkingLotService service=new ParkingLotService();
        service.createParkingLot(level, 10, new NearestToEntryStratergy());
        Optional<Ticket> t1=service.park( new Car("KA-01-HH-1234", "White"));
        Optional<Ticket> t2 =service.park( new Car("KA-02-MM-3034", "Black"));
        Optional<Ticket> t3= service.park( new Car("KA-51-HH-1324", "Grey"));
        Optional<Ticket> t4= service.park( new Car("KA-50-HH-0001", "White"));

        service.getSlotsForVehicleColour("Black");
        assertEquals("Created a parking lot with 10 slots\n" +
                "Allocated slot number: 1\n" +
                "Allocated slot number: 2\n" +
                "Allocated slot number: 3\n" +
                "Allocated slot number: 4\n" +
                "2", output.toString().trim());
        service.getSlotsForVehicleColour("Yellow");
        assertEquals("Created a parking lot with 10 slots\n" +
                "Allocated slot number: 1\n" +
                "Allocated slot number: 2\n" +
                "Allocated slot number: 3\n" +
                "Allocated slot number: 4\n" +
                "2\n" +
                "Sorry, No vehicles of this color has been parked", output.toString().trim());
        service.getSlotsForVehicleColour("white");
        assertEquals("Created a parking lot with 10 slots\n" +
                "Allocated slot number: 1\n" +
                "Allocated slot number: 2\n" +
                "Allocated slot number: 3\n" +
                "Allocated slot number: 4\n" +
                "2\n" +
                "Sorry, No vehicles of this color has been parked\n" +
                "1, 4", output.toString().trim());
        service.cleanUp();
    }
}