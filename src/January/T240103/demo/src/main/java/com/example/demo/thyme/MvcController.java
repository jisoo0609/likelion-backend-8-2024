package com.example.demo.thyme;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MvcController {
    // text로 요청이 오면 text.html을 반환하는 메서드
    @RequestMapping("/text")
    // Model은 View애 사용할 데이터를 모아두는 객체
    public String text(Model model) {
        // attiributeValue로 String 값
        model.addAttribute("message", "Hello Templates!");
        return "text";
    }

    // text-obeject로 요청이 오면
    // text-object.html을 반환하는 메서드
    @RequestMapping("/text-object")
    public String textObject(Model model) {
        Student alex = new Student("Alex", "alex@gmail.com");
        // attiributeValue로 객체 전달
        model.addAttribute("object",alex);
        return "text-object";
    }

    // is-logged-in으로 요청이 오면
    // if-unless.html을 반환하는 메서드
    @RequestMapping("/is-logged-in")
    public String isLoggedIn(Model model) {
        model.addAttribute("isLoggedIn", true);
        return "if-unless";
    }

    // /each로 요청이 오면 each.html을 반환하는 메서드
    @RequestMapping("/each")
    public String each(Model model) {
        // 여러 데이터를 가진 객체를 model에 전달
        List<String> listOfStrings = new ArrayList<>();
        listOfStrings.add("Alex");
        listOfStrings.add("Brad");
        listOfStrings.add("Chad");
        model.addAttribute("itemList", listOfStrings);

//        for (String item : listOfStrings) {
//            // listOfStrings 의 아이템을 하나씩 출력한다
//            System.out.println(item);
//        }

        // 여러 학생(Student) 데이터를 담은 리스트를 모델에 전달
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student("Alex", "alex@gmail.com"));
        studentList.add(new Student("Brad", "brad@gamil.com"));
        studentList.add(new Student("Chad", "chad.com"));
        studentList.isEmpty();
        model.addAttribute("studentList", studentList);
        return "each";
    }
}
