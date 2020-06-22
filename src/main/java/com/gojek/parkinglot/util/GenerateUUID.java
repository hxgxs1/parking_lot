package com.gojek.parkinglot.util;

import java.util.UUID;

/**
 * @author : gaurav.ss
 * @since : 22/06/20
 **/
public class GenerateUUID {

private GenerateUUID() { }

public static String next() {
        UUID u = UUID.randomUUID();
        return u.toString();
        }

}