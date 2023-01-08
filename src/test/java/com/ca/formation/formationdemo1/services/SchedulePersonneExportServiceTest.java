package com.ca.formation.formationdemo1.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = {SchedulePersonneExportService.class})
@ExtendWith(SpringExtension.class)
class SchedulePersonneExportServiceTest {
    @Autowired
    private SchedulePersonneExportService schedulePersonneExportService;

    @Test
    void testEnvoyerListePersonnes() {
        schedulePersonneExportService.envoyerListePersonnes();
        assertTrue(schedulePersonneExportService.logger instanceof ch.qos.logback.classic.Logger);
    }
}