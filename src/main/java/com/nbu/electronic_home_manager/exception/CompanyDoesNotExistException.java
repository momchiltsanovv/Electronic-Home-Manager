package com.nbu.electronic_home_manager.exception;

public class CompanyNotFoundException extends RuntimeException {

    public CompanyNotFoundException(String noSuchCompanyExists) {
        super(noSuchCompanyExists);
    }
}
