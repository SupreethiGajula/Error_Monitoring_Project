package com.supreethi.error_monitoring_platform.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class ErrorEvent {

    @NotBlank(message = "service name is required")
    private String serviceName;
    @NotBlank(message = "error message is required") 
    private String errorMessage;
    @NotBlank 
    @Pattern(
        regexp = "ERROR|WARN|INFO",
        message = "severity cannot be blank"
    ) 
    private String severity;
    private String eventId;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public ErrorEvent() {
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }
}
