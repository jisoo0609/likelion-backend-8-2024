package com.example.auth.article;

import com.example.auth.article.dto.ArticleDto;
import com.example.auth.article.entity.Article;
import com.example.auth.article.repository.ArticleRepository;
import com.example.auth.entity.UserEntity;
import com.example.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    // CREATE
    public ArticleDto create(ArticleDto dto) {
        /*
        // SecurityContextHolder에서 사용자 가져오기
        UserDetails userDetails = (UserDetails) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        // 사용자 username 가져오기
        String username = userDetails.getUsername();
        // userEntity 회수
        UserEntity writer = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
         */
        UserEntity writer = getUserEntity();

        Article newArticle = Article.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(writer)
                .build();

        // 저장
        newArticle = articleRepository.save(newArticle);
        return ArticleDto.fromEntity(newArticle);
    }

    private UserEntity getUserEntity () {
        UserDetails userDetails =
                (UserDetails) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    // READ

    // UPDATE

    // DELETE
}
