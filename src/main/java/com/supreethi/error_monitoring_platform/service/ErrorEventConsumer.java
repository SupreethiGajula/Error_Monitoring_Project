package com.supreethi.error_monitoring_platform.service;

import com.supreethi.error_monitoring_platform.model.ErrorEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import tools.jackson.databind.ObjectMapper;

@Service
public class ErrorEventConsumer {

    private final ObjectMapper objectMapper;
    private final ErrorEventService errorEventService;

    public ErrorEventConsumer(
            ObjectMapper objectMapper,
            ErrorEventService errorEventService) {
        this.objectMapper = objectMapper;
        this.errorEventService = errorEventService;
    }

    @KafkaListener(
            topics = "error-events",
            groupId = "error-monitoring-consumer"
    )
    public void consume(String payload) throws Exception {

        // Convert the JSON string back into our Java DTO
        ErrorEvent errorEvent =
                objectMapper.readValue(payload, ErrorEvent.class);

        // Reuse our existing database persistence logic
        var savedEvent = errorEventService.saveError(errorEvent);

        System.out.println(
                "Error event saved to MySQL. ID: "
                        + savedEvent.getId()
        );
    }
}


    



