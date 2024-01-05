package com.example.jpademo.repository;

import com.example.jpademo.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository <ArticleEntity, Long> {

}
