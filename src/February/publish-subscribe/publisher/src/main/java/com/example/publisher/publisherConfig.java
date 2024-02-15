package com.example.publisher;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class publisherConfig {
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("boot.fanout");
    }
}
