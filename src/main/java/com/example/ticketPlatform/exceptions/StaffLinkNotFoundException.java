package com.example.ticketPlatform.exceptions;

public class StaffLinkNotFoundException extends EventTicketException {
    public StaffLinkNotFoundException() {
    }

    public StaffLinkNotFoundException(String message) {
        super(message);
    }

    public StaffLinkNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public StaffLinkNotFoundException(Throwable cause) {
        super(cause);
    }

    public StaffLinkNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
