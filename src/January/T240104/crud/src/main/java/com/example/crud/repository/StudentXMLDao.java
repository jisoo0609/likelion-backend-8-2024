package com.example.crud.repository;

import com.example.crud.mapper.StudentMapper;
import com.example.crud.mapper.StudentXMLMapper;
import com.example.crud.model.StudentDto;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StudentXMLDao {
    private final SqlSessionFactory sessionFactory;

    public List<StudentDto> readStudentsAll() {
        // 사용방식은 annotation 기반과 동일하다.
        try (SqlSession session = sessionFactory.openSession()) {
            StudentXMLMapper mapper = session.getMapper(StudentXMLMapper.class);
            return mapper.selectStudentAll();
        }
    }

    public StudentDto readStudent(Long id) {
        try (SqlSession session = sessionFactory.openSession()) {
            StudentXMLMapper mapper = session.getMapper(StudentXMLMapper.class);
            return mapper.selectStudent(id);
        }
    }

    public void insertStudent(StudentDto dto) {
        try (SqlSession session = sessionFactory.openSession()){
            StudentXMLMapper mapper = session.getMapper(StudentXMLMapper.class);
            mapper.insertStudent(dto);
        }
    }
}
