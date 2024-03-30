package com.example.todo.dto;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TodoDto {
    private Long id;
    private String todo;
    private Boolean done;
}
