package com.gojek.parkinglot.exception;

/**
 * @author : gaurav.ss
 * @since : 22/06/20
 **/
public class ParkinglotException extends Exception {

    private Error error;

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }
}
