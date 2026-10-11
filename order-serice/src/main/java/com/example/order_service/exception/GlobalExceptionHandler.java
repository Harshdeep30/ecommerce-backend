package com.example.order_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String,String> handleOrderException(OrderNotFoundException ex){
        return Map.of("message",ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,String> handleValidationError(MethodArgumentNotValidException ex){
        Map<String,String> errors=new HashMap<>();
       ex.getBindingResult()
                .getFieldErrors()
               .forEach(error->
                       errors.put(error.getField(),error.getDefaultMessage()));
       return errors;
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String,String> handleEmailAlreadyExists(EmailAlreadyExistsException ex){
        return Map.of("message",ex.getMessage());
    }

}
