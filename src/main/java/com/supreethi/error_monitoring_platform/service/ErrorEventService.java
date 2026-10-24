package com.supreethi.error_monitoring_platform.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supreethi.error_monitoring_platform.repository.ErrorEventRepository;



import com.supreethi.error_monitoring_platform.model.ErrorEvent;
import com.supreethi.error_monitoring_platform.entity.ErrorEventEntity;
import java.util.List;


@Service 
public class ErrorEventService {
    private final ErrorEventRepository errorEventRepository;


    //Because the class is annotated with @Service, 
    // Spring manages it as a bean. Spring also creates and manages the repository, 
    // then injects it into the service's constructor.
   //Notice that we're not using @Autowired explicitly. 
   //Spring supports constructor injection automatically when a class has a single constructor.
    public ErrorEventService(
            ErrorEventRepository errorEventRepository) {
        this.errorEventRepository = errorEventRepository;
    }

    @Transactional 
    public ErrorEventEntity saveError(ErrorEvent errorEvent){
        ErrorEventEntity entity = new ErrorEventEntity();
        entity.setServiceName(errorEvent.getServiceName());
        entity.setErrorMessage(errorEvent.getErrorMessage());
        entity.setSeverity(errorEvent.getSeverity());
        entity.setCreatedAt(LocalDateTime.now());
        //Spring Data JPA uses Hibernate to persist the entity. Because this is a new entity 
        // with a generated ID, the normal result is an insert. 
        // The returned entity contains the generated ID.
        return errorEventRepository.save(entity);
    }


    @Transactional(readOnly = true)
    public List<ErrorEventEntity> getErrors(
            String severity, String serviceName) {

        boolean hasSeverity =
                severity != null && !severity.isBlank();

        boolean hasServiceName =
                serviceName != null && !serviceName.isBlank();

        if (hasSeverity && hasServiceName) {
            return errorEventRepository
                    .fetchErrorsBySeverityAndService(
                            severity.trim(), serviceName.trim());
        }

        if (hasSeverity) {
            return errorEventRepository
                    .fetchErrorsBySeverity(severity.trim());
        }

        if (hasServiceName) {
            return errorEventRepository
                    .fetchErrorsByService(serviceName.trim());
        }

        return errorEventRepository.fetchAllErrors();
    }
}




