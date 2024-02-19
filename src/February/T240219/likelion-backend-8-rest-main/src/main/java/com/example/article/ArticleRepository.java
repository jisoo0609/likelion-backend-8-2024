package com.example.article;

import com.example.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository
        extends JpaRepository<Article, Long> {
    // ID가 큰 순서데로 최상위 20개
    List<Article> findTop20ByOrderByIdDesc();
}
