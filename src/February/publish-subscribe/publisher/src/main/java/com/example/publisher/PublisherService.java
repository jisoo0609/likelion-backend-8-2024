package com.example.publisher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PublisherService {
    private final RabbitTemplate rabbitTemplate;
    private final FanoutExchange fanoutExchange;

    // fanout 종류의 exchage에 message 보내기
    public void fanoutMessage(String message) {
        rabbitTemplate.convertAndSend(
                fanoutExchange.getName(),
                "",
                message
        );
    }

    private final DirectExchange directExchange;
    // direct 종류의 exchange에 message 보내기
    public void directMessage(String message, String key) {
        rabbitTemplate.convertAndSend(
                directExchange.getName(),
                key,
                message
        );
    }

    private final TopicExchange topicExchange;
    public void topicMessage(String message, String topic) {
        rabbitTemplate.convertAndSend(
                topicExchange.getName(),
                topic,
                message
        );
    }
}
