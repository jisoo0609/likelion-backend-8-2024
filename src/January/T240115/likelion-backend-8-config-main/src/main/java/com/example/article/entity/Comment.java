package com.example.article.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String content;
    @Setter
    private String writer;

    @Setter
    @ManyToOne
    private Article article;

    public Comment() {
    }

    public Comment(String content, String writer, Article article) {
        this.content = content;
        this.writer = writer;
        this.article = article;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", articleId=" + article.getId() +
                ", content='" + content + '\'' +
                ", writer='" + writer + '\'' +
                '}';
    }
}
