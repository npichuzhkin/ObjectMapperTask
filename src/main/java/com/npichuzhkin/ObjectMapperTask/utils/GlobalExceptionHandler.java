package com.npichuzhkin.ObjectMapperTask.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>("Invalid input: " + Objects.requireNonNull(ex.getFieldError()).getDefaultMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolation(ConstraintViolationException ex) {
        return new ResponseEntity<>("Validation error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleConstraintViolation(EntityNotFoundException ex) {
        return new ResponseEntity<>("Entity error: " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<String> handleConstraintViolation(JsonProcessingException ex) {
        return new ResponseEntity<>("Json mapping error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
