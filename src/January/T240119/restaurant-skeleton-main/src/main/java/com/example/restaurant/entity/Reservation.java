package com.example.restaurant.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private Integer date; // 예약 날짜 :: 다른 날임을 구분하기 위한 임의 데이터
    @Setter
    private Integer reserveHour; // 예약 시작 시간
    @Setter
    private Integer people; // 예약 인원
    @Setter
    private Integer duration; // 체류 시간

    @Setter
    @ManyToOne
    private Restaurant restaurant;

    public Reservation(Integer date, Integer reserveHour, Integer people, Integer duration, Restaurant restaurant) {
        this.date = date;
        this.reserveHour = reserveHour;
        this.people = people;
        this.duration = duration;
        this.restaurant = restaurant;
    }
}
