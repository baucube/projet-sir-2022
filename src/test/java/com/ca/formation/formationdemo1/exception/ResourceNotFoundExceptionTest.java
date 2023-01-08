package com.ca.formation.formationdemo1.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceNotFoundExceptionTest {
    @Test
    void testConstructor() {
        ResourceNotFoundException actualResourceNotFoundException = new ResourceNotFoundException("An error occurred");
        assertNull(actualResourceNotFoundException.getCause());
        assertEquals(0, actualResourceNotFoundException.getSuppressed().length);
        assertEquals("An error occurred", actualResourceNotFoundException.getMessage());
        assertEquals("An error occurred", actualResourceNotFoundException.getLocalizedMessage());
    }
}