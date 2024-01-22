package com.example.article.controller;

import com.example.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class QueryController {
    private final ArticleService service;

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

    // GET /query-example?query=keyword&limit=20 HTTP/1.1
    @GetMapping("/query-example")
    public String queryParams(
            // key전달
            @RequestParam("query")
            String query,
            // 받을 자료형 선택 가능
            // 만약 변환 불가한 자료형일 경우 Bad Request (400)
            @RequestParam("limit")
            Integer limit,
            // 반드시 포함해야 하는지 아닌지를 required로 정의 가능
            @RequestParam(value = "notreq", required = false)
            String notRequired,
            // 기본값 설정을 원한다면 defaultValue
            @RequestParam(value = "default", defaultValue = "hello")
            String defaultVal
    ) {
        log.info("query: "+ query);
        log.info("limit: "+ limit);
        log.info("notRequired: "+ notRequired);
        log.info("default: "+ defaultVal);

        return "done";
    }

    // GET /query-page?page=1&perpage=25
    @GetMapping("/query-page")
    public Object queryPage(
            // defaultValue 문자열로 전달해야 함
            @RequestParam(value = "page", defaultValue = "1")
            Integer page,
            @RequestParam(value = "perpage", defaultValue = "25")
            Integer perPage
    ) {
        log.info("page: "+page);
        log.info("perpage:"+ perPage);
        service.readTop20();
        service.readArticlePagedList();
        return service.readArticlePaged(page, perPage);
    }

    // GET /query-search?q=keyword&cat=writer
    @GetMapping("/query-serch")
    public String querySearch(
            @RequestParam("q")
            String keyword,
            @RequestParam(value = "cat", defaultValue = "title")
            String category
    ) {
        log.info("keyword:"+ keyword);
        log.info("category: "+ category);
        return "done";
    }
}
