package io.ennov.simple_ticket_management_system.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import io.ennov.simple_ticket_management_system.Constants;

@ControllerAdvice
public class NotFoundExceptionController {
    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<?> exception(ResourceNotFoundException exception, WebRequest request) {
    NotFoundExceptionMessage exceptionMessage = new NotFoundExceptionMessage(Constants.ERROR, new Date(), exception.getMessage(), request.getDescription(false));
    return new ResponseEntity<>(exceptionMessage, HttpStatus.NOT_FOUND);
}

@ExceptionHandler({Exception.class})
public ResponseEntity<?> globalExceptionHandling(Exception exception, WebRequest request) {
    NotFoundExceptionMessage exceptionMessage = new NotFoundExceptionMessage(Constants.ERROR, new Date(), exception.getMessage(), request.getDescription(false));
    return new ResponseEntity<>(exceptionMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
