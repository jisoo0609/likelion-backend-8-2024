package com.example.kafkaproducer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProducerService {
    private final KafkaTemplate<String, String> stringKafkaTemplate;
    private static final String TOPIC = "topic";

    public void send(String message) {
        stringKafkaTemplate.send(TOPIC, message);
    }

    public void sendWithCallback(String message) {
        CompletableFuture<SendResult<String, String>> sendResultFuture
                = stringKafkaTemplate.send(TOPIC, message);
        sendResultFuture.whenComplete((sendResult, throwable) -> {
            log.info("send().whenComplete()");
            log.info(String.valueOf(sendResult));
            log.info(String.valueOf(throwable));
        });
        log.info("end sendWithCallback()");
    }
}
