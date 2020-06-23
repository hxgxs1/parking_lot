package com.gojek.parkinglot;

import com.gojek.parkinglot.core.NearestToEntryStratergy;
import com.gojek.parkinglot.core.ParkingLotService;
import com.gojek.parkinglot.entities.Car;
import com.gojek.parkinglot.exception.ParkingLotError;
import com.gojek.parkinglot.exception.ParkinglotException;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * @author : gaurav.ss
 * @since : 22/06/20
 **/
public class ParkingLotTest {

    int level;

    public ExpectedException thrownException = ExpectedException.none();

    @Before
    public void init(){
        level=0;
        System.out.println("Starting with test cases");
    }

    @Test
    public void createParkingLot() throws ParkinglotException {
        ParkingLotService service=new ParkingLotService();
        String str=service.createParkingLot(level, 6, new NearestToEntryStratergy());
        assertTrue("created a parking lot with 6 slots".equalsIgnoreCase(str.trim()));
        service.cleanUp();
    }


    @Test
    public void testCreatingMultipleLots() throws ParkinglotException {
        ParkinglotException exception=null;
        ParkingLotService service=new ParkingLotService();

        try {
            service.createParkingLot(level, 6, new NearestToEntryStratergy());
            service.createParkingLot(level, 10, new NearestToEntryStratergy());
        }catch (ParkinglotException e){
            exception=e;
        }
        assertNotNull(exception);
        assertSame(ParkinglotException.class, exception.getClass());
        assertSame(ParkingLotError.PARKIGN_LOT_ALREADY_EXISTS.getErrorMsg(), exception.getError().getErrorMsg());
        service.createParkingLot(1, 15, new NearestToEntryStratergy()); //created a new Level
        service.cleanUp();
    }
    
}
