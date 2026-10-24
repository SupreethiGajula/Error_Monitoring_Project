package com.supreethi.error_monitoring_platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.supreethi.error_monitoring_platform.entity.ErrorEventEntity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ErrorEventRepository
        extends JpaRepository<ErrorEventEntity, Long> {

    // Retrieve all error events, newest first
    @Query(value = """
            SELECT *
            FROM error_events
            ORDER BY created_at DESC
            """, nativeQuery = true)
    List<ErrorEventEntity> fetchAllErrors();

    // Retrieve errors filtered by severity
    @Query(value = """
            SELECT *
            FROM error_events
            WHERE UPPER(severity) = UPPER(:severity)
            ORDER BY created_at DESC
            """, nativeQuery = true)
    List<ErrorEventEntity> fetchErrorsBySeverity(
            @Param("severity") String severity);

    // Retrieve errors filtered by service name
    @Query(value = """
            SELECT *
            FROM error_events
            WHERE UPPER(service_name) = UPPER(:serviceName)
            ORDER BY created_at DESC
            """, nativeQuery = true)
    List<ErrorEventEntity> fetchErrorsByService(
            @Param("serviceName") String serviceName);

    // Retrieve errors filtered by severity and service name
    @Query(value = """
            SELECT *
            FROM error_events
            WHERE UPPER(severity) = UPPER(:severity)
              AND UPPER(service_name) = UPPER(:serviceName)
            ORDER BY created_at DESC
            """, nativeQuery = true)
    List<ErrorEventEntity> fetchErrorsBySeverityAndService(
            @Param("severity") String severity,
            @Param("serviceName") String serviceName);
}
