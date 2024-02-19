package com.example.article;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class QueryController {
    // http://localhost:8080/query-test?name=jeeho&age=40&height=175&weight=68
    @GetMapping("/query-test")
    public void queryParams(
            // Query Parameter를 받아올때 사용 가능
            @RequestParam("name")
            String name,
            @RequestParam(value = "age", required = false)
            Integer age,
            @RequestParam(value = "height", defaultValue = "175")
            Integer height,
            @RequestParam("weight")
            Integer weight
    ) {
        log.info(name);
        log.info(String.valueOf(age));
        log.info(height.toString());
        log.info(weight.toString());
    }
}
