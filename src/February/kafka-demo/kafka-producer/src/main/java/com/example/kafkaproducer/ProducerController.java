package com.example.kafkaproducer;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProducerController {
    private final ProducerService service;

    @PostMapping("/publish")
    public String publish(
            @RequestParam("message")
            String message
    ) {
        service.sendWithCallback(message);
        return "published: " + message;
    }
}
