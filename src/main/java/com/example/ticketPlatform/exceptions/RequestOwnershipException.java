package com.example.ticketPlatform.exceptions;

public class RequestOwnershipException extends EventTicketException {
    public RequestOwnershipException() {
    }

    public RequestOwnershipException(String message) {
        super(message);
    }

    public RequestOwnershipException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestOwnershipException(Throwable cause) {
        super(cause);
    }

    public RequestOwnershipException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
