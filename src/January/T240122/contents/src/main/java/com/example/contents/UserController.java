package com.example.contents;

import com.example.contents.dto.ErrorDto;
import com.example.contents.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @PostMapping
    public UserDto create(
            UserDto dto
    ) {
        return service.create(dto);
    }

    @GetMapping("/{username}")
    public UserDto read(
            @PathVariable("username")
            String username
    ) {
        return service.readUserByUsername(username);
    }

    @PutMapping("/{userId}/avatar")
    public UserDto avatar(
            @PathVariable("userId")
            Long userId,
            @RequestParam("image")
            MultipartFile imageFile

    ) {
        return service.updateUserAvatar(userId, imageFile);
    }

    // 컨트롤러 단위에서 예외처리를 하고싶은 경우
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDto handleIllegalArgument(final IllegalArgumentException exception) {
        log.warn(exception.getMessage());
        ErrorDto dto = new ErrorDto();
        dto.setMessage(exception.getMessage());
        return dto;
    }
}
