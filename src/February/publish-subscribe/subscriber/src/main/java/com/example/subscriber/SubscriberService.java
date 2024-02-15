package com.example.subscriber;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SubscriberService {
    //SpEL로 Queue를 지정한다.
    @RabbitListener(queues = "#{fanoutQueue.name}")
    public void receiveFanout(String message) {
        log.info("in fanout received: {}", message);
    }

    @RabbitListener(queues = "#{directQueue.name}")
    public void receiveDirect(String message) {
        log.info("in direct received: {}", message);
    }

    @RabbitListener(queues = "#{topicQueue.name}")
    public void receiveTopic(String message) {
        log.info("in topic received: {}", message);
    }
}
