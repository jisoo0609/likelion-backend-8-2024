package com.example.crud.mapper;

import com.example.crud.model.StudentDto;

import java.util.List;

public interface StudentXMLMapper {
    List<StudentDto> selectStudentAll();
    StudentDto selectStudent(Long id);
    void insertStudent(StudentDto dto);
}
