package com.example.ticketPlatform.exceptions;

public class TicketAlreadyValidatedException extends EventTicketException {
    public TicketAlreadyValidatedException() {
    }

    public TicketAlreadyValidatedException(String message) {
        super(message);
    }

    public TicketAlreadyValidatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public TicketAlreadyValidatedException(Throwable cause) {
        super(cause);
    }

    public TicketAlreadyValidatedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
