package com.supreethi.error_monitoring_platform.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity 
@Table(name = "error_events")
public class ErrorEventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String serviceName;
    @Column(nullable = false, length = 2000)
    private String errorMessage;
    @Column(nullable = false)
    private String severity;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "event_id", nullable = false, unique = true, length = 36)
    private String eventId;

    public String getEventId() {
        return eventId;
    }
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
    public String getFingerprint() {
        return fingerprint;
    }
    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }
    @Column(length = 64)
    private String fingerprint;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
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
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }




}
