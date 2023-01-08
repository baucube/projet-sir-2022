package com.ca.formation.formationdemo1.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;

import static org.junit.jupiter.api.Assertions.*;
class GlobalExceptionHandlerTest {

    @Test
    void resourceNotFoundException() {
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        ResourceNotFoundException ex = new ResourceNotFoundException("An error occurred");
        ResponseEntity<?> actualResourceNotFoundExceptionResult = globalExceptionHandler.resourceNotFoundException(ex,
                new ServletWebRequest(new MockHttpServletRequest()));
        assertTrue(actualResourceNotFoundExceptionResult.hasBody());
        assertTrue(actualResourceNotFoundExceptionResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.NOT_FOUND, actualResourceNotFoundExceptionResult.getStatusCode());
        assertEquals("An error occurred", ((ErrorResponse) actualResourceNotFoundExceptionResult.getBody()).getMessage());
        assertEquals("uri=", ((ErrorResponse) actualResourceNotFoundExceptionResult.getBody()).getDetails());
        assertEquals("404 NOT_FOUND", ((ErrorResponse) actualResourceNotFoundExceptionResult.getBody()).getStatus());
    }

    @Test
    void globalExceptionHandler() {
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        ResourceNotFoundException ex = new ResourceNotFoundException("An error occurred");
        ResponseEntity<?> actualGlobalExceptionHandlerResult = globalExceptionHandler.globalExceptionHandler(ex,
                new ServletWebRequest(new MockHttpServletRequest()));
        assertTrue(actualGlobalExceptionHandlerResult.hasBody());
        assertTrue(actualGlobalExceptionHandlerResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualGlobalExceptionHandlerResult.getStatusCode());
        assertEquals("An error occurred", ((ErrorResponse) actualGlobalExceptionHandlerResult.getBody()).getMessage());
        assertEquals("uri=", ((ErrorResponse) actualGlobalExceptionHandlerResult.getBody()).getDetails());
        assertEquals("500 INTERNAL_SERVER_ERROR",
                ((ErrorResponse) actualGlobalExceptionHandlerResult.getBody()).getStatus());
    }

}