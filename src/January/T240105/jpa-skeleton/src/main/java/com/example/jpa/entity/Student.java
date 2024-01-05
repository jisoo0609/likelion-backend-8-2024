package com.example.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;
/*
CREATE TABLE student (
    ID INTEGER PRIMARY KEY AUTOINCREMENT,
    NAME VARCHAR(255),
    AGE INTEGER,
    PHONE VARCHAR(255)
    EMAIL VARCHAR(255)
);
 */
@Data
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int age;
    private String phone;
    private String email;

    // FK column -> Join Column
    @ManyToOne
    private Instructor advisor;
}
