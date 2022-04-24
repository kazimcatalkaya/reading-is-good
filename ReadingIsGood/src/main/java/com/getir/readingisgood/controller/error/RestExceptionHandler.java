package com.getir.readingisgood.controller.error;

import com.getir.readingisgood.controller.error.dao.GenericException;
import com.getir.readingisgood.exception.*;
import com.getir.readingisgood.controller.error.dao.GenericResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@AllArgsConstructor
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;


    @ExceptionHandler(EnumNotFoundException.class)
    public ResponseEntity<Object> handleDayOffApprovePendingException(final EnumNotFoundException exception, final WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = "enum.not.found";
        return GenerateResponseByStatusAndMessage(exception, exception.getParameters(), status, message, request);
    }

    @ExceptionHandler(BookAlreadyExistsException.class)
    public ResponseEntity<Object> handleBookAlreadyExistsException(final BookAlreadyExistsException exception, final WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = "book.already.exists";
        return GenerateResponseByStatusAndMessage(exception, exception.getParameters(), status, message, request);
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<Object> handleBookNotFoundException(final BookNotFoundException exception, final WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String message = "book.not.found";
        return GenerateResponseByStatusAndMessage(exception, exception.getParameters(), status, message, request);
    }

    @ExceptionHandler(BookImproperValueException.class)
    public ResponseEntity<Object> handleBookImproperValueException(final BookImproperValueException exception, final WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        String message = "book.improper.values";
        return GenerateResponseByStatusAndMessage(exception, exception.getParameters(), status, message, request);
    }

    @ExceptionHandler(ThereIsNotEnoughProductException.class)
    public ResponseEntity<Object> handleCustomerNotFoundException(final ThereIsNotEnoughProductException exception, final WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String message = "there.are.no.enough.book";
        return GenerateResponseByStatusAndMessage(exception, exception.getParameters(), status, message, request);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Object> handleOrderNotFoundException(final OrderNotFoundException exception, final WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String message = "order.not.found";
        return GenerateResponseByStatusAndMessage(exception, exception.getParameters(), status, message, request);
    }

    @ExceptionHandler(CustomerAlreadyExistsException.class)
    public ResponseEntity<Object> handleOrderNotFoundException(final CustomerAlreadyExistsException exception, final WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        String message = "customer.already.exists";
        return GenerateResponseByStatusAndMessage(exception, exception.getParameters(), status, message, request);
    }

    private ResponseEntity<Object> GenerateResponseByStatusAndMessage(final RuntimeException exception,
                                                                      Object[] parameters,
                                                                      HttpStatus status,
                                                                      String message,
                                                                      final WebRequest request) {
        final GenericResponse response =
                new GenericResponse(
                        status,
                        messageSource.getMessage(message, parameters, request.getLocale())
                );
        return handleExceptionInternal(exception, response, new HttpHeaders(), status, request);
    }

}
