package com.xy.inc.service.exception;

public class PoiException extends Exception {

    private final String message;

    public PoiException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
