package com.example.article.controller;
// CRUD를 위한 URL
/*
POST /articles -> crate()
GET /articles -> readAll()
GET /articles/{id} -> read()
PUT /articles/{id} -> update()
DELETE /articles/{id} -> delete
 */

import com.example.article.service.ArticleService;
import com.example.article.dto.ArticleDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public List<ArticleDto> readAll() {
        return service.readAll();
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
