package com.example.contents;

import com.example.contents.dto.UserDto;
import com.example.contents.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

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
        // 1. userService가 받을 UserDto의 username
        String username = "gildong";

        // 2. userRepository가 반환할 user, UserDto의 username과 동일할것이라 가정
        User userOut = new User(username, null, null, null);

        // 3. userRepository.save(User)의 결과를 userOut으로 설정
        when(userRepository.save(any()))
                .thenReturn(userOut);
        // 정의하지 않아도, boolean 반환값의 기본값은 false이다.
//        when(userRepository.existsByUsername(username))
//                .thenReturn(false);

        // when - UserDto를 전달한다.
        UserDto userDto = new UserDto(
                null, username,
                null,
                null,
                null,
                null
        );
        // userService 내부에서 새 User 객체를 만들고,
        UserDto result = userService.create(userDto);

        // then - 돌아온 result를 검사한다.
        // 정상적으로 동작하면 userService가 반환한 UserDto는
        // 동일한 username을 가진 UserDto를 반환한다.
        assertEquals(username, result.getUsername());
    }

    // readUserByUsername
    @Test
    @DisplayName("username으로 UserDto 반환")
    public void testReadUserByUsername() {
        // given
        // 특정 username을 기준으로
        String username = "gildong.dev";
        User user = new User(
                username, null, null, null);
        // findByUsername은 해당 username을 가진 entity를 반환한다.
        when(userRepository.findByUsername(username))
                .thenReturn(Optional.of(user));

        // when
        // userService는 userName을 바탕으로 userRepository의 결과를
        // DTO의 형태로 반환한다.
        UserDto result = userService.readUserByUsername(username);

        // then
        // 둘의 username이 일치하는지 확인
        assertEquals(username, result.getUsername());
    }
}