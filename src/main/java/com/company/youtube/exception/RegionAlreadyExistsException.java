package com.company.youtube.exception;

public class RegionAlreadyExistsException extends RuntimeException{
    public RegionAlreadyExistsException(String message) {
        super(message);
    }
}
