package com.example.article;

import com.example.article.dto.ArticleDto;
import com.example.article.entity.Article;
import com.example.article.repo.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
// 비즈니스 로직을 담당하는 클래스이다.
@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository repository;

    public ArticleDto create(ArticleDto dto) {
        Article article = new Article(
                dto.getTitle(), dto.getContent(), dto.getWriter()
        );
        return ArticleDto.fromEntity(repository.save(article));
    }

    public List<ArticleDto> readAll() {
        List<ArticleDto> articleList = new ArrayList<>();
//      for (Article article: repository.findAll()) {
        for (Article article : repository.findAllByIdLessThanEqual(25L)) {
            // 게시판에 늦게 작성된 게시글 부터 정렬됨 -> 위에서부터 50, 49, 48,...
            articleList.add(ArticleDto.fromEntity(article));
        }

        return articleList;
        // stream
        /* return repository.findAll().stream()
                .map(ArticleDto::fromEntity)
                .collect(Collectors.toList()); */
    }

    public ArticleDto read(Long id) {
        ArticleStoreSingleton store = ArticleStoreSingleton.getInstance();
        Article article = repository.findById(id).orElseThrow();
        return ArticleDto.fromEntity(article);
    }

    public ArticleDto update(Long id, ArticleDto dto) {
        Article article = repository.findById(id).orElseThrow();
        article.setTitle(dto.getTitle());
        article.setContent(dto.getContent());
        return ArticleDto.fromEntity(repository.save(article));
    }

    public void delete(Long id) {
        repository.delete(repository.findById(id).orElseThrow());
    }
}
