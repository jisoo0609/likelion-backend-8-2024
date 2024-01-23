package com.example.contents;

import com.example.contents.dto.ErrorDto;
import com.example.contents.exceptions.Status400Exception;
import com.example.contents.exceptions.UsernameExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

// Controller는 아니고
// 예외처리를 위한 ExceptionHandler를 모아놓기 위한

// 전체 애플리케이션에서 발생하는 예외를 처리할 수 있음
// 사용자에게 유효한 응답 만들 수 있음
@ControllerAdvice
public class GlobalControllerAdvice {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDto> handleIllegalArgument(
            final IllegalArgumentException exception
    ) {
        ErrorDto dto = new ErrorDto();
        dto.setMessage(exception.getMessage());
        return ResponseEntity
                .badRequest()
                .body(dto);
    }
 /*
    @ExceptionHandler(UsernameExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleUsernameExists(final UsernameExistException exception) {
        ErrorDto dto = new ErrorDto();
        dto.setMessage(exception.getMessage());
        return dto;
    }

 */
    @ExceptionHandler(Status400Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handle400(final Status400Exception exception) {
        ErrorDto dto = new ErrorDto();
        dto.setMessage(exception.getMessage());
        return dto;
    }
}