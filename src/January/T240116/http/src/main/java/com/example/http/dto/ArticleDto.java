package com.example.http.dto;

// 사용자가 작성하고 싶은 게시글 데이터를 의미한다.
// 사용자가 보내주는 데이터

// 현재는 단순한 용도이기 때문에 @Data 추가
import lombok.Data;

// 제목: 직렬화 역직렬화
// 내용: Serialization

/*
// JSON으로 표현된 데이터
{
    "title": "직렬화 역직렬화",
    "content": "Serialization"
}

// 자바에서 실제로 사용하는 객체
Article dto = new ArticleDto();
dto.setTitle("직렬화 역직렬화");
dto.setContent("Serialization");
 */
@Data
public class ArticleDto {
    private String title;
    private String content;
}
