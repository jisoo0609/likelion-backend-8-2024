package com.example.jpanext.school.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "attending lectures")
public class AttendingLectures {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "studnet id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "lecture id")
    private Lecture lecture;

    private Integer midTermScore;
    private Integer finalsScore;
}
