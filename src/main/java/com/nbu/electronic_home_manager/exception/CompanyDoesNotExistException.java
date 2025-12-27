package com.nbu.electronic_home_manager.exception;

public class CompanyDoesNotExistException extends RuntimeException {

    public CompanyDoesNotExistException(String noSuchCompanyExists) {
        super(noSuchCompanyExists);
    }
}
