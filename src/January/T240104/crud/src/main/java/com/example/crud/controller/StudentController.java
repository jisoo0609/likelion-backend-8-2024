package com.example.crud.controller;

import com.example.crud.model.StudentDto;
import com.example.crud.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class StudentController {
    // private static final Logger log = LoggerFactory.getLogger(StudentController.class);
    // /create-view로 요청이 왔을 때
    // create.html을 반환하는 메서드
    // @RequestMapping("/create-view")

    private StudentService service;

    public StudentController(StudentService studentService) {
        this.service = studentService;
    }

    @GetMapping("/create-view")
    public String createView() {
        return "create";
    }

    // /create로 이름과 데이터를 보내는 요청을 받는 메서드
    @PostMapping("/create")
    public String create(
            @RequestParam("name") String name,
            @RequestParam("email") String email) {
        service.createStudent(name, email);
        return "redirect:/home";
    }

    // /home으로 요청을 받으면
    // home.html에 studentList를 포함해 반환하는 메서드
    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("studentList", service.readStudentAll());
        return "home";
    }

    // /read로 요청을 받으면
    // read.html에 student 정보를 포함해 반환하는 메서드
    // Mapping에 {}를 넣으면 그 안에 들어가 있는 데이터를
    // 매개변수에 할당해 줄 수 있다
    @GetMapping("/read/{id}")
    public String readOne(
            @PathVariable("id") Long id,
            Model model) {
        StudentDto dto = service.readStudent(id);
        model.addAttribute("student", dto);
        return "read";
    }

    // /update-view/{id}로 요청을 받으면
    // update.html에 student 정보를 포함해 반환하는 메서드
    @GetMapping("/update-view/{id}")
    public String updateView(
            @PathVariable("id") Long id,
            Model model
    ) {
        StudentDto dto = service.readStudent(id);
        model.addAttribute("student", dto);
        return "update";
    }

    @PostMapping("/update/{id}")
    public String update(
            @PathVariable("id") Long id,
            @RequestParam("name") String name,
            @RequestParam("email") String email
    ) {
        StudentDto dto = service.updateStudent(id, name, email);
        return String.format("redirect:/read/%s",dto.getId());
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        service.deleteStudent(id);
        return "redirect:/home";
    }

}
