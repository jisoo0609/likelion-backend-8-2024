package com.example.publisher;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class publisherController {
    private final PublisherService service;

    @PostMapping("fanout")
    public void fanout(
            @RequestParam("message")
            String message
    ) {
        service.fanoutMessage(message);
    }
}
