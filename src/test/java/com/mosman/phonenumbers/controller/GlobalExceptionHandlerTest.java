package com.mosman.phonenumbers.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

    @Test
    @DisplayName("Should handle ResponseStatusException correctly")
    public void testHandleResponseStatusException() {
        var exception = new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found");
        
        var response = exceptionHandler.handleResponseStatusException(exception);
        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Resource not found", response.getBody().get("error"));
        assertEquals("404", response.getBody().get("status"));
    }

    @Test
    @DisplayName("Should handle MethodArgumentNotValidException correctly")
    public void testHandleValidationExceptions() {
        var bindingResult = mock(org.springframework.validation.BindingResult.class);
        var fieldError = new FieldError("phoneNumber", "subscriberNumber", "Invalid format");
        
        when(bindingResult.getAllErrors()).thenReturn(java.util.List.of(fieldError));
        
        var exception = mock(MethodArgumentNotValidException.class);
        when(exception.getBindingResult()).thenReturn(bindingResult);
        
        var response = exceptionHandler.handleValidationExceptions(exception);
        
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid format", response.getBody().get("subscriberNumber"));
    }

    @Test
    @DisplayName("Should handle IllegalArgumentException correctly")
    public void testHandleIllegalArgumentException() {
        var exception = new IllegalArgumentException("Invalid argument provided");
        
        var response = exceptionHandler.handleIllegalArgumentException(exception);
        
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid argument provided", response.getBody().get("error"));
        assertEquals("400", response.getBody().get("status"));
    }

    @Test
    @DisplayName("Should handle generic Exception correctly")
    public void testHandleGenericException() {
        var exception = new RuntimeException("Unexpected error");
        
        var response = exceptionHandler.handleGenericException(exception);
        
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Internal server error", response.getBody().get("error"));
        assertEquals("500", response.getBody().get("status"));
    }
}