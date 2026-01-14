package com.example.ticketPlatform.exceptions;

public class TicketAlreadyExistException extends EventTicketException {
    public TicketAlreadyExistException() {
    }

    public TicketAlreadyExistException(String message) {
        super(message);
    }

    public TicketAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public TicketAlreadyExistException(Throwable cause) {
        super(cause);
    }

    public TicketAlreadyExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
