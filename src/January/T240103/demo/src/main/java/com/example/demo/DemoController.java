package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

// @Controller
// URL에 따른 요청을 처리하는 메서드를 담아두는 클래스임을 나타냄
@Controller
public class DemoController {
    // DemoController는 HelloComponent를 사용한다
    // Composition : 구성
    private HelloComponent component; // 필요. 의존 메서드
    // IoC Container가 만들어놓은 컴포넌트를 필요한 시점에 주입
    public DemoController(HelloComponent component) {
        this.component = component;
    }

    // @RequestMapping
    // 어떤 URL 요청에 대하여 실행되는 메서드임을 명시
    @RequestMapping("home")
    public String home() {
        component.sayHello();
        return "home.html";
    }

    // profile로 요청이 들어올 때
    // profile.html을 응답하고 싶다
    @RequestMapping("profile")
    public String profile() {
        return "profile.html";
    }

    // /hobbies로 요청이 들어오면
    // hobbies.html을 응답한다
    @RequestMapping("hobbies")
    public String hobbies() {
        return "hobbies.html";
    }
}
