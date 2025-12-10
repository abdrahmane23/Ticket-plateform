package com.example.ticketPlatform.exceptions;

public class StafferNotRegisteredException extends EventTicketException {

    public StafferNotRegisteredException() {
    }

    public StafferNotRegisteredException(String message) {
        super(message);
    }

    public StafferNotRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }

    public StafferNotRegisteredException(Throwable cause) {
        super(cause);
    }

    public StafferNotRegisteredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
