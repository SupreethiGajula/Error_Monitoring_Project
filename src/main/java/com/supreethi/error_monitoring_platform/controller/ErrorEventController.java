
package com.supreethi.error_monitoring_platform.controller;

import com.supreethi.error_monitoring_platform.entity.ErrorEventEntity;
import com.supreethi.error_monitoring_platform.model.ErrorEvent;
import com.supreethi.error_monitoring_platform.service.ErrorEventProducer;
import com.supreethi.error_monitoring_platform.service.ErrorEventService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/errors")
public class ErrorEventController {
   
private final ErrorEventService errorEventService;
private final ErrorEventProducer errorEventProducer;

public ErrorEventController(
        ErrorEventService errorEventService,
        ErrorEventProducer errorEventProducer) {
    this.errorEventService = errorEventService;
    this.errorEventProducer = errorEventProducer;
}

@PostMapping
public CompletableFuture<ResponseEntity<Map<String, Object>>> receiveError(
        @Valid @RequestBody ErrorEvent errorEvent) {

    return errorEventProducer.publish(errorEvent)
    //if the promise is resolved
            .thenApply(result -> ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body(Map.<String, Object>of(
                            "message", "Error event accepted by Kafka",
                            "topic", result.getRecordMetadata().topic(),
                            "partition", result.getRecordMetadata().partition(),
                            "offset", result.getRecordMetadata().offset()
                    )))
                    //if error occurs
            .exceptionally(exception -> ResponseEntity
                    .status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(Map.<String, Object>of(
                            "message",
                            "Unable to publish error event to Kafka"
                    )));
}


     @GetMapping
    public List<ErrorEventEntity> getErrors(
            @RequestParam(required = false) String severity,
            @RequestParam(required = false) String serviceName) {

        return errorEventService.getErrors(severity, serviceName);
    }
}