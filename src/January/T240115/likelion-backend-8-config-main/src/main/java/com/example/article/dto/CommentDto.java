package com.example.article.dto;

import com.example.article.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class CommentDto {
    private Long id;
    @Setter
    private String content;
    @Setter
    private String writer;

    public CommentDto(String content) {
        this.content = content;
    }

    public CommentDto(String content, String writer) {
        this.content = content;
        this.writer = writer;
    }

    public static CommentDto fromEntity(Comment entity) {
        CommentDto dto = new CommentDto();
        dto.id = entity.getId();
        dto.content = entity.getContent();
        dto.writer = entity.getWriter();
        return dto;
    }
}
