package com.example.contents;

import com.example.contents.dto.UserDto;
import com.example.contents.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

// 다른것들은 잘 동작한다고 가정
@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    @Mock
    // 실제 UserRepository가 아니라
    // UserRepository의 메서드를 다 가지고 있지만
    // 동작은 다르게 하는 Mock 객체
    private UserRepository userRepository;

    @InjectMocks
    // 실제 UserRepository가 아닌
    // 위에 만든 짝퉁 userRepository를 의존성으로 사용
    private UserService userService;

    // UserDto를 인자로 받아 User를 생성하고
    // 그 결과를 UserDto로 반환
    @Test
    @DisplayName("UserDto로 사용자 생성")
    public void testCreateUser() {
        // given
        // 1. userRepository가 특정 User를 전달받을것을 가정한다.
        String username = "edujeeho";
        // userRepository가 입력받을 user
        User userIn = new User(username, null, null, null);

        // 2. userRepository가 반환할 user
        User userOut = new User(username, null, null, null);
        userOut.setId(1L);

        // 3. userRepository.save(userIn)의 결과를 userOut으로 설정
        when(userRepository.save(userIn))
                .thenReturn(userOut);
        when(userRepository.existsByUsername(username))
                .thenReturn(false);

        // when - UserDto를 전달한다.
        UserDto userDto = new UserDto(
                null, username,
                null,
                null,
                null,
                null
        );
        UserDto result = userService.create(userDto);

        // then - 돌아온 result를 검사한다.
        assertEquals(username, result.getUsername());
    }
}