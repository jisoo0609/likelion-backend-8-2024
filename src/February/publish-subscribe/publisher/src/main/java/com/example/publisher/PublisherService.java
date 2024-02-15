package com.example.publisher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.FanoutExchange;
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
}
