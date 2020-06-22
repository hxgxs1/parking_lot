package com.gojek.parkinglot.entities;

import com.gojek.parkinglot.entities.Vehicle;

import java.util.Map;
import java.util.Optional;

/**
 * @author : gaurav.ss
 * @since : 22/06/20
 **/
public class MultiLevelParking {

    /* outer map= At each parking level there can the multiple parkinglots/space
       inner map= For each level there will be slots mapped to optional Vehivles depicting whether occupied or empty*/
    private Map<Integer, Map<Integer, Slot>> parkingLevelMap;
}
