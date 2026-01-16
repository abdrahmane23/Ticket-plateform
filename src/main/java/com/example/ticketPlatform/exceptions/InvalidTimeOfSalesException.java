package com.example.ticketPlatform.exceptions;

public class InvalidTimeOfSalesException extends EventTicketException {
  public InvalidTimeOfSalesException() {
  }

  public InvalidTimeOfSalesException(String message) {
    super(message);
  }

  public InvalidTimeOfSalesException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidTimeOfSalesException(Throwable cause) {
    super(cause);
  }

  public InvalidTimeOfSalesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
