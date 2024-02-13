package com.example.redis.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PersonDto implements Serializable {
    private String name;
    private Integer age;
    private String major;
}
