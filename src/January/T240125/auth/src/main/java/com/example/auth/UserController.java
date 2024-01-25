package com.example.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    // @Bean으로 등록되어있어 컨트롤러에서 가져와서 사용 가능
    private final UserDetailsManager manager;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginForm() {
        return "login-form";
    }

    // 로그인 한 후에 내가 누군지 확인하기 위한
    @GetMapping("/my-profile")
    public String myProfile() {
        return "my-profile";
    }

    // 회원가이 확인
    @GetMapping("/register")
    public String signUpForm() {
        return "register-form";
    }

    @PostMapping("/register")
    public String signUpRequest(
            @RequestParam("username")
            String username,
            @RequestParam("password")
            String password,
            @RequestParam("password-check")
            String passwordCheck
    ) {
        // TODO password == passwordCheck
        if (password.equals(passwordCheck))
            // TODO 주어진 정보를 바탕으로 새로운 사용자 생성
            manager.createUser(User.withUsername(username)
                    .password(passwordEncoder.encode(password))
                    .build());
        // 회원가입 성공 시에 로그인 페이지로 이동
        return "redirect:/users/login";
    }
}
