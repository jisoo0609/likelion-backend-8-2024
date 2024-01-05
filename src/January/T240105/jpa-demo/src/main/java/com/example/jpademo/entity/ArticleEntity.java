package com.example.jpademo.entity;

import jakarta.persistence.*;
import lombok.Data;

/*
CREATE TABLE article (
    id INTEGER PRIMARY KEY AUTOINCREMENT, -- Long
    title TEXT,    -- String
    content TEXT   -- String
);
 */
@Data
@Entity  // 이 클래스가 데이터베이스 상의 어떤 테이블을 나타내는 클래스임을 알려주는 어노테이션
@Table(name = "article")
public class ArticleEntity {
    @Id  // 이 속성(필드)가 테이블의 PK(Identity)컬럼임을 나타내는 어노테이션
    // 이 속성의 컬럼 데이터는 데이터베이스에서 자동으로 부여하는 값 및 어떻게 부여할지를 정의하는 어노테이션
    // 쉽게말해 AUTOINCREMENT다~ 라는 뜻
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;

    // 실제 테이블의 컬럼 설정 전달
    @Column (name = "username")
    private String writer;
    private String category;
}