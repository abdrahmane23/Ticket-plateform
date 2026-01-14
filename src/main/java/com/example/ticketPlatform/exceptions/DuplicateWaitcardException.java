package com.example.ticketPlatform.exceptions;

public class DuplicateWaitcardException extends EventTicketException {
  public DuplicateWaitcardException() {
  }

  public DuplicateWaitcardException(String message) {
    super(message);
  }

  public DuplicateWaitcardException(String message, Throwable cause) {
    super(message, cause);
  }

  public DuplicateWaitcardException(Throwable cause) {
    super(cause);
  }

  public DuplicateWaitcardException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
