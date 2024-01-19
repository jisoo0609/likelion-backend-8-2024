package com.example.article.repository;

import com.example.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findTopBy20OrderByIdDesc();
}
