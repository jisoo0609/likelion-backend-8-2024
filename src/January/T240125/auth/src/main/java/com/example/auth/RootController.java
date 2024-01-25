package com.example.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class RootController {
    @GetMapping
    public String root() {
        return "hello";
    }

    @GetMapping("/home")
    public String home() {
        log.info(SecurityContextHolder.getContext().getAuthentication().getName());
        return "index";
    }

    @GetMapping("/no-auth")
    public String noAuth() {
        return "no auth success!";
    }

    @GetMapping("/require-auth")
    public String reAuth() {
        return "auth success!";
    }
}
