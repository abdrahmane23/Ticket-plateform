package com.example.ticketPlatform.exceptions;

public class TaskOwnershipException extends EventTicketException {
    public TaskOwnershipException() {
    }

    public TaskOwnershipException(String message) {
        super(message);
    }

    public TaskOwnershipException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskOwnershipException(Throwable cause) {
        super(cause);
    }

    public TaskOwnershipException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
