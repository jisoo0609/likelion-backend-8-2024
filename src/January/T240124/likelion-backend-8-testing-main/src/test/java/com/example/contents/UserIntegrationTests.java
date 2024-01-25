package com.example.contents;

import com.example.contents.dto.UserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = ContentsApplication.class)
@AutoConfigureMockMvc
public class UserIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("User 생성 통합 테스트")
    public void whenPostUserDto_thenReturnJson()
            throws Exception {
        // Post할 UserDto 준비
        String username = "edujeeho";
        String email = "edujeeho@gmail.com";
        String phone = "01012345678";
        UserDto userDto = new UserDto(
                null,
                username,
                email,
                phone,
                null,
                null
        );

        // mockMvc를 이용해 UserDto를 보낸 결과를 받고
        ResultActions resultActions = mockMvc.perform(post("/user")
                .content(JsonUtil.toJson(userDto))
                .contentType(MediaType.APPLICATION_JSON));


        // 해당 mockMvc가 반환한 결과를 기준을 가지고 확인한다.
        resultActions.andExpectAll(
                status().is2xxSuccessful(),
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("$.id", notNullValue()),
                jsonPath("$.username", is(username)),
                jsonPath("$.email", is(email)),
                jsonPath("$.phone", is(phone))
        );

        // DB에 잘 들어갔는지도 확인한다.
        assertTrue(userRepository.existsByUsername(username));
    }
}

