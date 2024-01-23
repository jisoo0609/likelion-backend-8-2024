package com.example.contents.exceptions;

// 사용자 이름이 중복일 때 발생하는 예외
public class UsernameExistException extends Status400Exception {
    public UsernameExistException() {
        super("user exists");
    }
}
