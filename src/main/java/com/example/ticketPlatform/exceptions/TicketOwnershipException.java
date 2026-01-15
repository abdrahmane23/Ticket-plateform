package com.example.ticketPlatform.exceptions;

public class TicketOwnershipException extends EventTicketException {
  public TicketOwnershipException() {
  }

  public TicketOwnershipException(String message) {
    super(message);
  }

  public TicketOwnershipException(String message, Throwable cause) {
    super(message, cause);
  }

  public TicketOwnershipException(Throwable cause) {
    super(cause);
  }

  public TicketOwnershipException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
