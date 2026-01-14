package com.example.ticketPlatform.exceptions;

public class IvalideRoleException extends EventTicketException {
    public IvalideRoleException() {
    }

    public IvalideRoleException(String message) {
        super(message);
    }

    public IvalideRoleException(String message, Throwable cause) {
        super(message, cause);
    }

    public IvalideRoleException(Throwable cause) {
        super(cause);
    }

    public IvalideRoleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
