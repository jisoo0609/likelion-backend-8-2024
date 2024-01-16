package com.example.http;

import com.example.http.dto.ArticleDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class ArticleController {
    // 사용자가 /articles/create로
    // 데이터를 첨부해 요청을 보낼때,
    // 그 데이터를 ArticleDto의 형태로 받는 것
    @PostMapping("articles/create")
    // 메서드의 반환값을
    // View로 취급하지 않고,
    // 순수한 전달될 HTTP Response Body로 취급하는 어노테이션
    @ResponseBody
    public ArticleDto create(
            @RequestBody
            ArticleDto dto
    ) {
        log.info(dto.toString());
        String title = dto.getTitle();
        String content = dto.getContent();
        dto.setTitle(content);
        dto.setContent(title);
        return dto;
    }
}

