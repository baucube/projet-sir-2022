package com.ca.formation.formationdemo1.exception;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ErrorResponseTest {
    @Test
    public void testConstructor(){
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        ErrorResponse actualErrorResponse = new ErrorResponse(
                Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()), "Status", "Not all who wander are lost",
                "Details");
        actualErrorResponse.setDetails("Details");
        actualErrorResponse.setMessage("Not all who wander are lost");
        actualErrorResponse.setStatus("Status");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date fromResult = Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant());
        actualErrorResponse.setTimestamp(fromResult);
        assertEquals("Details", actualErrorResponse.getDetails());
        assertEquals("Not all who wander are lost", actualErrorResponse.getMessage());
        assertEquals("Status", actualErrorResponse.getStatus());
        assertSame(fromResult, actualErrorResponse.getTimestamp());
    }
}