package com.gojek.parkinglot.exception;

/**
 * @author : gaurav.ss
 * @since : 22/06/20
 **/
public enum ParkingLotError {

    INVALID_COMMAND("Invalid input command"),
    WRONG_PARAMETER("You have entered a wrong parameter for this commmand"),
    PARKIGN_LOT_ALREADY_EXISTS("Parking lot has already been created"),
    PARKING_LOT_DOES_NOT_EXIST("There is no parking lot present at the moment");

    private final String errorMsg;
    ParkingLotError(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
