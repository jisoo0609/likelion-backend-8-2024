package com.example.article;

import com.example.article.entity.Article;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ArticleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArticleApplication.class, args);
//		ArticleStoreSingleton store = new ArticleStoreSingleton();
		ArticleStoreSingleton store = ArticleStoreSingleton.getInstance();
	}

}
