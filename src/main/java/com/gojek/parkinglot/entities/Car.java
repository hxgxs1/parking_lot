package com.gojek.parkinglot.entities;

/**
 * @author : gaurav.ss
 * @since : 22/06/20
 **/
public class Car extends Vehicle{

    public Car(String registrationNumber, String color) {
        //todo: check on null/empty attributes;
        super(registrationNumber, color);
    }
}
