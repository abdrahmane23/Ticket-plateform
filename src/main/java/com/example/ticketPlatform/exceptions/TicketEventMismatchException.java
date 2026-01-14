package com.example.ticketPlatform.exceptions;

public class TicketEventMismatchException extends EventTicketException {
    public TicketEventMismatchException() {
    }

    public TicketEventMismatchException(String message) {
        super(message);
    }

    public TicketEventMismatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public TicketEventMismatchException(Throwable cause) {
        super(cause);
    }

    public TicketEventMismatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
