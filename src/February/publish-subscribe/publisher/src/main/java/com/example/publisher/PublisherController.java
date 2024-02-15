package com.example.publisher;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PublisherController {
    private final PublisherService service;

    @PostMapping("fanout")
    public void fanout(
            @RequestParam("message")
            String message
    ) {
        service.fanoutMessage(message);
    }

    @PostMapping("direct")
    public void direct(
            @RequestParam("message")
            String message,
            @RequestParam("key")
            String key
    ) {
        service.directMessage(message, key);
    }

    @PostMapping("topic")
    public void topic(
            @RequestParam("message")
            String message,
            @RequestParam("topic")
            String topic
    ) {
        service.topicMessage(message, topic);
    }
}
