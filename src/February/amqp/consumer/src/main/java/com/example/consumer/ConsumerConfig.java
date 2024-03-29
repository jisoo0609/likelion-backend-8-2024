package com.example.consumer;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsumerConfig {
    @Bean
    public Queue queue() {
        return new Queue(
                "boot.amqp.job-queue",
                true,
                false,
                true
        );
    }
}
