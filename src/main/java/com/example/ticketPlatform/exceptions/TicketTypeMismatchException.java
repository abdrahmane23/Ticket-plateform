package com.example.ticketPlatform.exceptions;

public class TicketTypeMismatchException extends EventTicketException {
    public TicketTypeMismatchException() {
    }

    public TicketTypeMismatchException(String message) {
        super(message);
    }

    public TicketTypeMismatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public TicketTypeMismatchException(Throwable cause) {
        super(cause);
    }

    public TicketTypeMismatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
