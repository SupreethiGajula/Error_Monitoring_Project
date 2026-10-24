package com.supreethi.error_monitoring_platform.service;
import com.supreethi.error_monitoring_platform.model.ErrorEvent;

import java.util.concurrent.CompletableFuture;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import org.springframework.stereotype.Service;



import tools.jackson.databind.ObjectMapper;

@Service 
public class ErrorEventProducer {
    public static final String TOPIC = "error-events";
    //the way in which records get stored in kafka topic
    private final KafkaTemplate<String,String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    public ErrorEventProducer(KafkaTemplate<String,String> kafkaTemplate,ObjectMapper objectMapper){
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;

    }
    public CompletableFuture<SendResult<String,String>> publish(ErrorEvent errorEvent){
        try{
            //convert JSON to string before sending to kafka topic
        String payload = objectMapper.writeValueAsString(errorEvent);
        return kafkaTemplate.send(TOPIC,errorEvent.getServiceName(),payload);
        }
        catch(Exception exception){
            return CompletableFuture.failedFuture(exception);

        }

    }

}
