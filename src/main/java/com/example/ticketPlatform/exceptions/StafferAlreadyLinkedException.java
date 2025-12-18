package com.example.ticketPlatform.exceptions;

public class StafferAlreadyLinkedException extends EventTicketException {
  public StafferAlreadyLinkedException() {
  }

  public StafferAlreadyLinkedException(String message) {
    super(message);
  }

  public StafferAlreadyLinkedException(String message, Throwable cause) {
    super(message, cause);
  }

  public StafferAlreadyLinkedException(Throwable cause) {
    super(cause);
  }

  public StafferAlreadyLinkedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
