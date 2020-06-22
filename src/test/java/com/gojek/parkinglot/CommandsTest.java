package com.gojek.parkinglot;

import com.gojek.parkinglot.commandInterpretor.Commands;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * @author : gaurav.ss
 * @since : 22/06/20
 **/

public class CommandsTest {

    private Commands commands;

    @Before
    public void init(){
        commands=new Commands();
    }


    @Test
    public void testAllCommands(){
        assertFalse(commands.getCommands().isEmpty());
        assertTrue(commands.getCommands().containsKey("create_parking_lot"));
        assertFalse(commands.getCommands().containsKey("FLY_CAR"));
        assertTrue(commands.getCommands().containsKey("park"));
        assertTrue(commands.getCommands().containsKey("leave"));
        assertTrue(commands.getCommands().containsKey("status"));

    }
}
