package com.nbu.electronic_home_manager.exception;

public class BuildingDoesNotExistException extends RuntimeException {

    public BuildingDoesNotExistException(String noSuchBuildingExist) {
        super(noSuchBuildingExist);
    }
}
