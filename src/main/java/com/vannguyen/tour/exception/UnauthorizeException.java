package com.vannguyen.tour.exception;

public class UnauthorizeException extends RuntimeException {
    public UnauthorizeException() {
        super("Unauthorized! Please Login");
    }
}
