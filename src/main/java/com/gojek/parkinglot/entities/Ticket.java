package com.gojek.parkinglot.entities;

import com.gojek.parkinglot.util.GenerateUUID;

import java.util.Date;

/**
 * @author : gaurav.ss
 * @since : 22/06/20
 **/
public class Ticket {

    private String registrationNumber;
    private String color;
    private String id;
    private int slot;
    private int level;
    private Long createdAt;


    public Ticket(String registrationNumber, String color, int slot, int level) {
        this.registrationNumber = registrationNumber;
        this.color = color;
        this.id = GenerateUUID.next();
        this.createdAt=new Date().getTime();
        this.slot=slot;
        this.level=level;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "registrationNumber='" + registrationNumber + '\'' +
                ", color='" + color + '\'' +
                ", id='" + id + '\'' +
                ", slot=" + slot +
                ", level=" + level +
                ", createdAt=" + createdAt +
                '}';
    }
}

