package com.nbu.electronic_home_manager.exception;

public class EmployeeDoesNotExistException extends RuntimeException {

    public EmployeeDoesNotExistException(String message) {
        super(message);
    }
}

