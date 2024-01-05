package com.example.jpa;

import com.example.jpa.dto.InstructorDto;
import com.example.jpa.entity.Instructor;
import com.example.jpa.repository.InstructorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class InstructorService {
    private final InstructorRepository instructorRepository;

    public Instructor readInstructor(Long id) {
        return instructorRepository.findById(id).orElse(null);
    }

    public List<Instructor> readInstructorAll() {
        return instructorRepository.findAll();
    }

}
