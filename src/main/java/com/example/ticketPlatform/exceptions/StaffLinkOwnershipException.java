package com.example.ticketPlatform.exceptions;

public class StaffLinkOwnershipException extends EventTicketException {
    public StaffLinkOwnershipException() {
    }

    public StaffLinkOwnershipException(String message) {
        super(message);
    }

    public StaffLinkOwnershipException(String message, Throwable cause) {
        super(message, cause);
    }

    public StaffLinkOwnershipException(Throwable cause) {
        super(cause);
    }

    public StaffLinkOwnershipException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
