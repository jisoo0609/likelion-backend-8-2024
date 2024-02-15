package com.example.subscriber;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SubscriberConfig {
    @Bean
    // 내가 갈 우체국
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("boot.fanout");
    }

    @Bean
    // 내 우체통
    public Queue fanoutQueue() {
        // 다른 Queue와 겹치지 않을 우체통을 준비한다.
        return new AnonymousQueue();
    }


    @Bean
    public Binding fanoutBinding() {
        // 우체통을 우체국에 전달
        return BindingBuilder
                // @Bean 메서드를 추출해도 Baen 객체로 등록된다.
                .bind(fanoutQueue())
                .to(fanoutExchange());
    }
//    @Bean
//    public Binding fanoutBinding(
//            // 어떤 Queue로 등록된 Bean을 사용할지
//            @Qualifier("fanoutQueue")
//            Queue queue,
//            FanoutExchange fanoutExchange
//    ) {
//        // 우체통을 우체국에 전달
//        return BindingBuilder
//                .bind(queue)
//                .to(fanoutExchange);
//    }
}
