package com.example.ticketPlatform.controller;


import com.example.ticketPlatform.domain.Dtos.ErrorDto;
import com.example.ticketPlatform.exceptions.*;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
  @ExceptionHandler(TicketOwnershipException.class)
  public ResponseEntity<ErrorDto> TicketOwnershipException (TicketOwnershipException ex) {
    log.error("Caught TicketOwnership Exception  ", ex);
    ErrorDto errorDto = new ErrorDto();
    errorDto.setError("unable to get ticket because of false ownership ");
    return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
  }
  @ExceptionHandler(TaskNotFoundException.class)
  public ResponseEntity<ErrorDto> TaskNotFoundException(TaskNotFoundException ex) {
    log.error("Caught TaskNotFound Exception  ", ex);
    ErrorDto errorDto = new ErrorDto();
    errorDto.setError("unable to find active task with given id ");
    return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(DuplicateWaitcardException.class)
  public ResponseEntity<ErrorDto> DuplicateWaitcardException(DuplicateWaitcardException ex) {
    log.error("Caught DuplicateWaitcard Exception  ", ex);
    ErrorDto errorDto = new ErrorDto();
    errorDto.setError("unable to enter waitlist because tickets are still available ");
    return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
  }
  @ExceptionHandler(TicketAvailableException.class)
  public ResponseEntity<ErrorDto> TicketAvailable(TicketAvailableException ex) {
    log.error("Caught TicketAvailable Exception  ", ex);
    ErrorDto errorDto = new ErrorDto();
    errorDto.setError("unable to enter waitlist because tickets are still available ");
    return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
  }
  @ExceptionHandler(TicketAlreadyExistException.class)
  public ResponseEntity<ErrorDto> TicketAlreadyExistException(TicketAlreadyExistException ex) {
    log.error("Caught TicketAlreadyExist Exception  ", ex);
    ErrorDto errorDto = new ErrorDto();
    errorDto.setError("unable to enter waitlist because u already own a ticket for this event ");
    return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
  }
  @ExceptionHandler(StafferNotRegisteredException.class)
  public ResponseEntity<ErrorDto> StafferNotRegisteredException(StafferNotRegisteredException ex) {
    log.error("Caught StafferNotRegistered Exception  ", ex);
    ErrorDto errorDto = new ErrorDto();
    errorDto.setError("unable to browse tasks because staffer is not register under this organizer ");
    return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
  }
  @ExceptionHandler(EventOwnershipException.class)
  public ResponseEntity<ErrorDto> EventOwnershipException(EventOwnershipException ex) {
    log.error("Caught EventOwnership Exception  ", ex);
    ErrorDto errorDto = new ErrorDto();
    errorDto.setError("unable to update TicketType because of false ownership ");
    return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
  }
  @ExceptionHandler(TaskOwnershipException.class)
  public ResponseEntity<ErrorDto> TaskOwnershipException(TaskOwnershipException ex) {
    log.error("Caught TaskOwnership Exception ", ex);
    ErrorDto errorDto = new ErrorDto();
    errorDto.setError("unable to update task because of false ownership ");
    return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
  }
  @ExceptionHandler(StaffLinkOwnershipException.class)
  public ResponseEntity<ErrorDto> StaffLinkOwnershipException(StaffLinkOwnershipException ex) {
    log.error("Caught StaffLinkOwnership Exception", ex);
    ErrorDto errorDto = new ErrorDto();
    errorDto.setError("unable to remove link because of false ownership ");
    return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
  }

  //exceptions for requests
  @ExceptionHandler(RequestOwnershipException.class)
  public ResponseEntity<ErrorDto> RequestOwnershipException(RequestOwnershipException ex) {
    log.error("Caught RequestOwnership Exception", ex);
    ErrorDto errorDto = new ErrorDto();
    errorDto.setError("unable to accept request because of false ownership ");
    return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(IvalideRoleException.class)
  public ResponseEntity<ErrorDto> IvalideRoleException(IvalideRoleException ex) {
    log.error("Caught IvalideRole Exception", ex);
    ErrorDto errorDto = new ErrorDto();
    errorDto.setError("unable to accept request because of invalid role ");
    return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(RequestException.class)
  public ResponseEntity<ErrorDto> RequestException(RequestException ex) {
    log.error("Caught Request Exception", ex);
    ErrorDto errorDto = new ErrorDto();
    errorDto.setError("Unable to create request because staffer already linked ");
    return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
  }
  @ExceptionHandler(StafferAlreadyLinkedException.class)
  public ResponseEntity<ErrorDto> handleStafferAlreadyLinkedException(StafferAlreadyLinkedException ex) {
    log.error("Caught StafferAlreadyLinked Exception", ex);
    ErrorDto errorDto = new ErrorDto();
    errorDto.setError("Unable to link because staffer already linked ");
    return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
  }
  @ExceptionHandler(RequestNotFoundException.class)
  public ResponseEntity<ErrorDto> handleRequestNotFoundException(RequestNotFoundException ex) {
    log.error("Caught RequestNotFound Exception", ex);
    ErrorDto errorDto = new ErrorDto();
    errorDto.setError("Unable to accept request because it doesn't exists ");
    return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
  }
  @ExceptionHandler(DuplicateRequestException.class)
  public ResponseEntity<ErrorDto> handleDuplicateRequestException(DuplicateRequestException ex) {
    log.error("Caught DuplicateRequest Exception", ex);
    ErrorDto errorDto = new ErrorDto();
    errorDto.setError("Unable to Send request because it already exists ");
    return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
  }





  @ExceptionHandler(TicketAlreadyValidatedException.class)
  public ResponseEntity<ErrorDto> handleTicketAlreadyValidatedException(TicketAlreadyValidatedException ex) {
    log.error("Caught TicketAlreadyValidated Exception", ex);
    ErrorDto errorDto = new ErrorDto();
    errorDto.setError("Unable to Validate ticket because it has already been validated ");
    return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(TicketTypeMismatchException.class)
  public ResponseEntity<ErrorDto> handleTicketTypeMismatchException(TicketTypeMismatchException ex) {
    log.error("Caught TicketTypeMismatch Exception", ex);
    ErrorDto errorDto = new ErrorDto();
    errorDto.setError("Unable to Validate ticket because the ticketType doesn't match this Type ");
    return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(TicketEventMismatchException.class)
  public ResponseEntity<ErrorDto> handleTicketEventMismatchException(TicketEventMismatchException ex) {
    log.error("Caught TicketEventMismatch Exception", ex);
    ErrorDto errorDto = new ErrorDto();
    errorDto.setError("Unable to Validate ticket because the ticket doesn't match the event ");
    return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(EventUpdateException.class)
  public ResponseEntity<ErrorDto> handleEventUpdateException(EventUpdateException ex) {
    log.error("Caught EventUpdateException", ex);
    ErrorDto errorDto = new ErrorDto();
    errorDto.setError("Unable to update event");
    return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(TicketTypeNotFoundException.class)
  public ResponseEntity<ErrorDto> handleTicketTypeNotFoundException(
          TicketTypeNotFoundException ex) {
    log.error("Caught TicketTypeNotFoundException", ex);
    ErrorDto errorDto = new ErrorDto();
    errorDto.setError("Ticket type not found");
    return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(EventNotFoundException.class)
  public ResponseEntity<ErrorDto> handleEventNotFoundException(EventNotFoundException ex) {
    log.error("Caught EventNotFoundException", ex);
    ErrorDto errorDto = new ErrorDto();
    errorDto.setError("Event not found");
    return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
  }
  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ErrorDto> handleUserNotFoundException(UserNotFoundException ex) {
    log.error("Caught UserNotFoundException", ex);
    ErrorDto errorDto = new ErrorDto();
    errorDto.setError("User not found");
    return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorDto> handleMethodArgumentNotValidException(
          MethodArgumentNotValidException ex
  ) {
    log.error("Caught MethodArgumentNotValidException", ex);
    ErrorDto errorDto = new ErrorDto();

    BindingResult bindingResult = ex.getBindingResult();
    List<FieldError> fieldErrors = bindingResult.getFieldErrors();
    String errorMessage = fieldErrors.stream()
            .findFirst()
            .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
            .orElse("Validation error occurred");

    errorDto.setError(errorMessage);
    return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
  }
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorDto> handleConstraintViolation(
          ConstraintViolationException ex
  ) {
    log.error("Caught ConstraintViolationException", ex);
    ErrorDto errorDto = new ErrorDto();

    String errorMessage = ex.getConstraintViolations()
            .stream()
            .findFirst()
            .map(violation ->
                    violation.getPropertyPath() + ": " + violation.getMessage()
            ).orElse("Constraint violation occurred");

    errorDto.setError(errorMessage);
    return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorDto> handleException(Exception ex) {
    log.error("Caught exception", ex);
    ErrorDto errorDto = new ErrorDto();
    errorDto.setError("An unknown error occurred");
    return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
