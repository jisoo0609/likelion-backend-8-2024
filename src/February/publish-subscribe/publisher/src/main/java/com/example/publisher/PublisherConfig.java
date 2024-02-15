package com.example.publisher;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PublisherConfig {
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange("boot.fanout");
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("boot.direct");
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("boot.topic");
    }
}