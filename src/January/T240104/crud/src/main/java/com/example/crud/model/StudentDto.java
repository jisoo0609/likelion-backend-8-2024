package com.example.crud.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Data Transfer Object
// Value Object
// Data Object
// 학생의 데이터를 담기 위한 클래스이다.
// Getter, Setter, EqualAndHashCode
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
    // 데이터베이스의 PK
    private Long id;
    // 이름 정보
    private String name;
    // 이메일
    private String email;
}