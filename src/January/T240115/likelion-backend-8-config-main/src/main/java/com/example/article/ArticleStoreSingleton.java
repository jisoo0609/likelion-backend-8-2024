package com.example.article;

import com.example.article.dto.ArticleDto;

import java.util.ArrayList;
import java.util.List;

// Singleton Pattern
public class ArticleStoreSingleton {
    private final List<ArticleDto> articleDtoList = new ArrayList<>();
    private static ArticleStoreSingleton instance;
    private ArticleStoreSingleton () {}
    public static ArticleStoreSingleton getInstance() {
        if (instance == null)
            instance = new ArticleStoreSingleton();
        return instance;
    }
}
