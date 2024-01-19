package com.example.article.controller;
// CRUD를 위한 URL
/*
POST /articles -> crate()
GET /articles -> readAll()
GET /articles/{id} -> read()
PUT /articles/{id} -> update()
DELETE /articles/{id} -> delete
 */

import com.example.article.entity.Article;
import com.example.article.service.ArticleService;
import com.example.article.dto.ArticleDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService service;

    @PostMapping
    public ArticleDto create(@RequestBody ArticleDto dto) {
       return service.create(dto);
    }
    /*
    @GetMapping
    public List<ArticleDto> readAll() {
        return service.readAll();
    }
    */
    /*
    @GetMapping
    public List<ArticleDto> readAllPaged(
            @RequestParam(value = "page", defaultValue = "1")
            Integer page,
            @RequestParam(value = "ilmit", defaultValue = "20")
            Integer limit
    ) {
        return service.readAllPaged(page, limit);
    }
    */

    @GetMapping
    public Page<Article> readAllPagination(
            @RequestParam(value = "page", defaultValue = "1")
            Integer page,
            @RequestParam(value = "limit", defaultValue = "20")
            Integer limit
    ) {
        return service.readAllPagination(page, limit);
    }

    @GetMapping("/{id}")
    public ArticleDto read(
            @PathVariable("id")
            Long id
    ) {
        return service.readOne(id);
    }

    @PutMapping("/{id}")
    public ArticleDto update(
            @PathVariable("id")
            Long id,
            @RequestBody
            ArticleDto dto
    ) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        service.delete(id);
    }
}
