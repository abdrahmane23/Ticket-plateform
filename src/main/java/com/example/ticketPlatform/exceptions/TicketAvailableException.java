package com.example.ticketPlatform.exceptions;

public class TicketAvailableException extends EventTicketException {
  public TicketAvailableException() {
  }

  public TicketAvailableException(String message) {
    super(message);
  }

  public TicketAvailableException(String message, Throwable cause) {
    super(message, cause);
  }

  public TicketAvailableException(Throwable cause) {
    super(cause);
  }

  public TicketAvailableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
