package com.example.contents;

import com.example.contents.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{userId}/avartar")
    public UserDto avatar(
            @PathVariable("userId")
            String userId
    ) {
        return service.updateUserAvatar(null, null);
    }
}
