package com.nbu.electronic_home_manager.exception;

public class ApartmentDoesNotExistException extends RuntimeException {

    public ApartmentDoesNotExistException(String message) {
        super(message);
    }
}

