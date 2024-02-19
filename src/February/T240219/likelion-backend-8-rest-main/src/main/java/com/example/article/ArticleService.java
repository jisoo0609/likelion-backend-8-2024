package com.example.article;

import com.example.article.dto.ArticleDto;
import com.example.article.entity.Article;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service  // 비즈니스 로직을 담당하는 클래스
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository repository;

    // CREATE
    public ArticleDto create(ArticleDto dto) {
        Article newArticle = new Article(
                dto.getTitle(),
                dto.getContent(),
                dto.getWriter()
        );
//        newArticle = repository.save(newArticle);
//        return ArticleDto.fromEntity(newArticle);

        return ArticleDto.fromEntity(repository.save(newArticle));
    }

    // READ ALL
    public List<ArticleDto> readAll() {
        List<ArticleDto> articleList = new ArrayList<>();
        // 여기에 모든 게시글을 리스트로 정리해서 전달
        List<Article> articles = repository.findAll();
        for (Article entity: articles) {
            articleList.add(ArticleDto.fromEntity(entity));
        }
        return articleList;
    }

    // READ ALL PAGED
    // JPA Query Method 방식 (비추)
//    public List<ArticleDto> readArticlePaged() {
//
//        List<ArticleDto> articleDtoList =
//                new ArrayList<>();
//        for (ArticleEntity entity:
//                repository.findTop20ByOrderByIdDesc()) {
//            articleDtoList.add(ArticleDto.fromEntity(entity));
//        }
//
//        return articleDtoList;
//    }
    // PagingAndSortingRepository 메소드에 전달하는 용도
//    public List<ArticleDto> readArticlePaged() {
//
//        // 조회하고 싶은 페이지의 정보를 담는 객체
//        // 20개씩 데이터를 나눌때 0번 페이지를 달라고 요청하는 Pageable
//        Pageable pageable = PageRequest.of(
//                0, 20, Sort.by("id").descending());
//        Page<ArticleEntity> articleEntityPage
//                = repository.findAll(pageable);
//
//        List<ArticleDto> articleDtoList = new ArrayList<>();
//        for (ArticleEntity entity: articleEntityPage) {
//            articleDtoList.add(ArticleDto.fromEntity(entity));
//        }
//        return articleDtoList;
//    }

    public Page<ArticleDto> readArticlePaged(
            Integer pageNumber, Integer pageSize
    ) {
        // PagingAndSortingRepository 메소드에 전달하는 용도
        // 조회하고 싶은 페이지의 정보를 담는 객체
        // 20개씩 데이터를 나눌때 0번 페이지를 달라고 요청하는 Pageable
        Pageable pageable = PageRequest.of(
                pageNumber, pageSize, Sort.by("id").descending());
        Page<Article> articleEntityPage
                = repository.findAll(pageable);
        // map: 전달받은 함수를 각 원소에 인자로 전달한 결과를
        // 다시 모아서 Stream으로
        // Page.map: 전달받은 함수를 각 원소에 인자로 전달한 결과를
        // 다시 모아서 Page로
        Page<ArticleDto> articleDtoPage
                = articleEntityPage.map(ArticleDto::fromEntity);
        return articleDtoPage;
    }


    // READ ONE
    public ArticleDto readOne(Long id) {
        Optional<Article> optionalArticle = repository.findById(id);
        // 해당하는 Article이 있었다.
        if (optionalArticle.isPresent()) {
            Article article = optionalArticle.get();
            return ArticleDto.fromEntity(article);
        }
        // 없으면 예외를 발생시킨다.
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







