package com.example.demo.form;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class FormController {
    // 1. 사용자에게 표기하고 싶은 메시지를 전달할 수 있는
    // HTML을 반환하는 메서드
    /* @RequestMapping("/send") */
    @GetMapping("/send")
    public String getForm() {
        return "send";
    }

    // 2. 사용자가 전달한 데이터를 처리할 수 있는 메서드
    /*
    // method 인자로 들어오는 요청의 HTTP method를 한정시킬 수 있다
    @RequestMapping(value="/receive", method = RequestMethod.POST)
     */
    // PostMapping은 명시적으로 Post 요청만 처리한다
    @PostMapping("/receive")
    public String receiveData(
            // @RequestParam
            // 사용자가 보낸 요청의 데이터를 받는 목적의 매개변수임을 표기
            @RequestParam("message")
            String message,
            Model model
    )
    {
        System.out.println(message);
        model.addAttribute("message", message);
        return "receive";
    }
}
