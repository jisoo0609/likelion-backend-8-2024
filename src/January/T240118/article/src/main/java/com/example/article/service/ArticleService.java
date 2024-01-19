package com.example.article.service;

import com.example.article.dto.ArticleDto;
import com.example.article.entity.Article;
import com.example.article.repository.ArticleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service // 비즈니스 로직을 담당하는 클래스
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository repository;

    // CREATE
    public ArticleDto create(ArticleDto dto) {
        Article newArticle = new Article(dto.getTitle(), dto.getContent(), dto.getWriter());
        return ArticleDto.fromEntity(repository.save(newArticle));
    }

    // READ ALL
    public List<ArticleDto> readAll() {
        List<ArticleDto> articleList = new ArrayList<>();
        // 모든 게시글을 리스트로 정리해서 전달
        List<Article> articles = repository.findAll();
        for (Article entity : articles) {
            articleList.add(ArticleDto.fromEntity(entity));
        }
        return articleList;
    }

    // READ ALL PAGED
    // page: 몇번째 페이지인지
    // limit: 한 페이지에 몇개가 들어가는지
    public List<ArticleDto> readAllPaged(Integer page, Integer limit) {
        List<ArticleDto> articleDtoList = new ArrayList<>();
        // 1. findAll의 결과 List를 일부만 반환하는 방법
        /*
        List<Article> articles = repository.findAll();
        for (int i = 0; i < 20; i++) {
            articleDtoList.add(ArticleDto.fromEntity(articles.get()));
        }
         */

        // 2. Query Method를 이용해 특정 개수 이후의
        // 개시글만 조회하게 한다
        List<Article> articles = repository.findTop20ByOrderByIdDesc();
        for (Article entity : articles) {
            articleDtoList.add(ArticleDto.fromEntity(entity));
        }

        return articleDtoList;
    }

    public Page<Article> readAllPagination(Integer page, Integer limit) {
        // 3. JPA PagingAndSortingRepository
        Pageable pageable = PageRequest.of(page, limit);
        Page<Article> articleEntityPage = repository.findAll(pageable);

        return articleEntityPage;
    }

    // READ ONE
    public ArticleDto readOne(Long id) {
        Optional<Article> optionalArticle = repository.findById(id);
        // 해당하는 Article이 있다
        if (optionalArticle.isPresent()) {
            Article article = optionalArticle.get();
            return ArticleDto.fromEntity(article);
        }
        // 없으면 예외를 발생시킨다
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    // UPDATE
    public ArticleDto update(Long id, ArticleDto dto) {
        Optional<Article> optionalArticle = repository.findById(id);
        if (optionalArticle.isPresent()) {
            Article targetEntity = optionalArticle.get();
            targetEntity.setTitle(dto.getTitle());
            targetEntity.setContent(dto.getContent());
            targetEntity.setWriter(dto.getWriter());
            return ArticleDto.fromEntity(repository.save(targetEntity));
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    // DELETE
    public void delete(Long id) {
        if (repository.existsById(id))
            repository.deleteById(id);
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
