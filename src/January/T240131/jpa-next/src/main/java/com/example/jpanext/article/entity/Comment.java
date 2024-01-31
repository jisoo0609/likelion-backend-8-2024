package com.example.jpanext.article.entity;

import com.example.jpanext.entity.BaseEntity;
import jakarta.persistence.*;

@Entity
public class Comment extends BaseEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
    private String content;
    private String writer;

    @ManyToOne
    private Article articles;
}
