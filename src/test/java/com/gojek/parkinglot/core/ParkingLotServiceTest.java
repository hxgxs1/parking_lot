package com.gojek.parkinglot.core;

import com.gojek.parkinglot.core.ParkingLotStrategy.NearestToEntryStratergy;
import com.gojek.parkinglot.entities.Car;
import com.gojek.parkinglot.exception.ParkingLotError;
import com.gojek.parkinglot.exception.ParkinglotException;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.*;

/**
 * @author : gaurav.ss
 * @since : 22/06/20
 **/
public class ParkingLotServiceTest {

    int level;


    @Before
    public void init(){
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
    public void leave() {
    }

    @Test
    public void getStatus() {
    }

    @Test
    public void getRegistrationNumsForColour() {
    }

    @Test
    public void slotForRegistrationNumber() {
    }

    @Test
    public void getSlotsForVehicleColour() {
    }
}