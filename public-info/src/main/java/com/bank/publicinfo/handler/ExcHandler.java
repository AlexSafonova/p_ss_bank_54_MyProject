package com.bank.publicinfo.handler;

import com.bank.publicinfo.exception.NotFoundException;
import io.micrometer.core.instrument.config.validate.ValidationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
@Log4j2
public class ExcHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<HandlerResponse> handleRequestParam(MethodArgumentTypeMismatchException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new HandlerResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<HandlerResponse> handleNotFound(NotFoundException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new HandlerResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<HandlerResponse> handleValidation(ValidationException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new HandlerResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
