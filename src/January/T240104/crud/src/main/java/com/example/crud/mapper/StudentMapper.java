package com.example.crud.mapper;

import com.example.crud.model.StudentDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentMapper {
    // MyBatis는 Interface의 메서드에 SQL을 연결한다
    // MyBatis의 세션을 이용해 interface의 메서드를 실행하면
    // 연결된 SQL이 실행된다
    @Select("SELECT * FROM student;")
    List<StudentDto> selectStudentAll();

    // SELECT, INSERT, UPDATE, DELETE

    // INSERT
    @Insert("INSERT INTO student(name, email)" +
            "VALUES(#{name}, #{email});")
    void insertStudent(StudentDto dto);

    // Select One
    @Select("SELECT * FROM student WHERE id = #{id};")
    StudentDto selectStudent(Long id);

    // Update
    @Update("UPDATE student SET " +
            "name = #{name}, " +
            "email = #{email} " +
            "WHERE id = #{id}")
    void updateStudent(StudentDto dto);

    // Delete
    @Delete("DELETE FROM student " +
            "WHERE id = #{id}")
    void deleteStudent(Long id);
}
