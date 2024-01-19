package com.example.restaurant.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@NoArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String name; // 이름
    @Setter
    private String category; // 분식, 한식, 양식 등의 카테고리
    @Setter
    private Integer openHours; // 여는시간
    @Setter
    private Integer closeHours; // 닫는시간

    public Restaurant(String name, String category, Integer openHours, Integer closeHours) {
        this.name = name;
        this.category = category;
        this.openHours = openHours;
        this.closeHours = closeHours;
    }
}
