package com.example.article.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class QueryController {
    // http://localhost:8080/query-test?name=gildong
    @GetMapping
    public void queryParams(@RequestParam("name") String name,
                            @RequestParam("age") Integer age,
                            @RequestParam("height") Integer height,
                            @RequestParam("weight") Integer weight) {
        log.info(name);
        log.info(age.toString());
        log.info(height.toString());
        log.info(weight.toString()
        );
    }
}
