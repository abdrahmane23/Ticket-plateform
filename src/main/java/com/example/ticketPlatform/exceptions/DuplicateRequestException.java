package com.example.ticketPlatform.exceptions;

public class DuplicateRequestException extends EventTicketException {
    public DuplicateRequestException() {
    }

    public DuplicateRequestException(String message) {
        super(message);
    }

    public DuplicateRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateRequestException(Throwable cause) {
        super(cause);
    }

    public DuplicateRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
