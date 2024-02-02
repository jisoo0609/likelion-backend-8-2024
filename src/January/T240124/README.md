# Testing

## 소프트웨어 개발의 테스트

![Untitled](/Training/image/240124.png)

### Unit Test (단위 테스트)

- 개별 코드 단위 (주로 메서드)를 테스트하는 단계
- Controller, Service, Repository의 개별 메서드

### Integration Test (통합 테스트)

- 서로 다른 모듈이 상호작용하는 것을 테스트하는 단계
- Controller부터 Repository까지 이어진 기능

### System Test

- 통합되어 구축된 시스템을 테스트하는 단계

## 소프트웨어 개발의 테스트

### 단점

- 결국엔 코드 작성하는 과정이기 때문에 개발 시간이 늘어난다
- 테스트 코드도 유지보수가 필요해 유지보수 비용이 늘어난다
- 테스트 작성법을 따로 익혀야 한다

### 장점

- TDD (Test-driven development)
- 잘못된 방향의 개발을 막는다
- 전체적인 코드의 품질이 상승한다
- 최종적으로 오류 상황에 대한 대처가 좋아져 전체적인 개발 시간이 줄어들 수 있다

## Spring Boot 테스트

- 기본적으로 테스트 용 도구가 포함되어 있다.

```java
dependencies {
	...
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
}
```

- JUnit
    - 사실상의 (de-facto) Java 어플리케이션 Testing 표준 라이브러리
- AssertJ
    - 가독성 높은 Test 작성을 위한 라이브러리
- Spring Test
- Hamcrest
- Mockito
- JSONPath 등

## given - when - then 패턴

- 테스트를 가독성이 좋게 작성하기 위한 패턴
- given
    - 테스트가 진행되기 위한 기본 조건을 준비하는 부분
- when
    - 테스트를 진행할 때 실제로 테스트하고 싶은 기능을 실행하는 부분
- then
    - 실행된 테스트의 결과가 기대한 것과 동일한지 검증하는 부분

# Repository 단위 테스트

## UserRepositoryTests

```java
@DataJpaTest
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;
		...
}
```

- `@DataJpaTest` : JPA 기능만 테스트하기 위한 어노테이션
- `@Autowired` : 의존성 주입 어노테이션’

### `testCreateUser()`

- 새로운 User 생성 테스트

```java
@Test
@DisplayName("새 user 생성)
public void testCreateUser() {
    // 테스트의 가독성을 높이는 패턴
    // given - 테스트를 진행하기 위한 기본 조건을 만들어 두는 부분
    // 내가 만들고자 하는 User 엔티티가 있는 상황에서
    String username = "gildong";
    User user = new User(username, null, null, null);

    // when - 실제로 테스트를 진행하는 부분
    // userRepository.save(user)를 진행한다.
    User result = userRepository.save(user);

    // then - 내가 기대한데로 동작했는지 검증
    // userRepository.save()의 결과의 username이 본래 User의 username과 일치했는지
    assertEquals(username, result.getUsername());
    // userRepository.save()의 결과의 id가 null이 아닌지
    assertNotNull(result.getId());
}
```

- `Assertion`
    - 테스트 과정에서 기대한대로 결과가 드러났는지를 확인하기 위한 코드
- `@DisplayName` : 표시되는 방식 바꿀 수 있음

### `testCreateUserFail()`

- 새로운 User 생성 실패 테스트
- 하나의 username이 있다고 가정하고, 같은 username을 사용해서 User를 생성할때는 실패해야 함

```java
@Test
public void testCreateUserFail() {
    // given - 어떤 특정 username을 가진 User가 이미 저장된 상황에서
    String username = "gildong";
    User userGiven = new User(username, null, null, null);
    userRepository.save(userGiven);

    // when - 동일한 username을 가진 User를 저장하려고 하면
    User newUser = new User(username, null, null, null);

    // then - 실패한다.
    assertThrows(Exception.class, () -> userRepository.save(newUser));
}
```

### `testReadUser()`

- 사용자를 조회하는 테스트

```java
@Test
@DisplayName("username으로 존재하는 사용자 조회")
public void testReadUser() {
    // given
    // 내가 읽고자 하는 특정 username의 User가 데이터베이스에 저장된 이후의 상황에서
    String username = "edujeeho";
    User userGiven = new User(username, null, null, null);
    userRepository.save(userGiven);

    // when
    // 해당하는 username 가지고 userRepository.findByUsername(username);의 결과를 받아오면
    Optional<User> optionalUser = userRepository.findByUsername(username);

    // then
    // 돌아온 결과 Optional.isPresent() == true이고, (assertTrue)
    assertTrue(optionalUser.isPresent());
    // 돌아온 결과 Optional.get().getUsername == username이다.
    assertEquals(username, optionalUser.get().getUsername());
}
```

# Service 단위 테스트

## UserServiceTests

- UserService는 `UserRepository`를 필요로 함
- 단위 테스트에서는 실제 Repository를 가져와서 테스트하지 않음
- 실제 `UserRepository`가 아니라 userRepository의 메서드를 다 가지고 있지만 동작은 다르게 하는 Mock 객체 사용

```java
@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;
		...
}
```

- `Mock` 객체: Repository의 기능을 흉내내는 모조 객체
- `@ExtendWith` : Mock 기능 사용
- `@Mock`
    - 이 객체는 실제가 아닌 Mock 객체임을 명시

  실제 UserRepository가 아니라 UserRepository의 메서드를 다 가지고 있지만, 동작은 다르게 하는 Mock 객체

- `@InjectMocks`
    - 이 객체의 의존성은 실제 Mock 객체를 사용
    - 실제 `UserRepository`가 아닌, 위에 만든 짝퉁 userRepository를 의존성으로 사용

### `testCreateUser()`

- UserDto를 인자로 받아 User를 생성하고 그 결과를 UserDto로 반환

```java
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
```

# Controller 단위 테스트

## UserControllerTests

- Controller는 테스트하기 위해 HTTP 요청이 필요하다
- HTTP 요청이 보내진 상황을 가정하기 위한 `MockMvc` 사용

```java
@ExtendWith(MockitoExtension.class)
public class UserControllerTests {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    // 이 테스트 클래스의 개별 클래스 전에 실행할 코드
    @BeforeEach
    public void beforeEach() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .build();
    }
		...
}
```

- `Mockmvc` : HTTP 요청을 보내는 객체
- `@BeforeEach` : 각 테스트 이전에 설정

### `testCreate()`

```java
@Test
@DisplayName("UserDto를 표현한 JSON 요청을 보내면 UserDto를 표현한 JSON을 응답")
public void testCreate() throws Exception {
    // given
    // userService.createUser 정의
    String username = "gildong";
    UserDto requestDto = new UserDto(
            null, username, null, null, null, null
    );
    UserDto responseDto = new UserDto(
            1L, username, null, null, null, null
    );
    when(userService.create(any()))
            .thenReturn(responseDto);

    // when
    // perform: HTTP 요청이 보내졌다고 가정
    ResultActions result = mockMvc.perform(
            // post 요청을 보낸다.
            post("/user")
                    // 이 요청의 Body는 requestDto를 JSON으로 변환한 것
                    .content(JsonUtil.toJson(requestDto))
                    // 이 요청의 Body는 JSON이라고 생각하고 이해할 것
                    .contentType(MediaType.APPLICATION_JSON)
    );

    // then
    // 응답의 코드가 2xx
    // 내용이 JSON
    // username이 변화하지 않았다.
    // id가 null이 아니다.
    result.andExpectAll(
            // 2xx번대(성공) 상태코드
            status().is2xxSuccessful(),
            // JSON 응답을 주었다.
            content().contentType(MediaType.APPLICATION_JSON),
            // JSON에 username이라는 값이 전달된 username과 동일하다.
            jsonPath("$.username", is(username)),
            // id는 null이 아니다.
            jsonPath("$.id", notNullValue())
    );
}
```

---

# Integration Test

- 단위 테스트처럼 `MockMvc` 를 사용한다
- 실제로 데이터베이스에 저장되었는지 확인하는 과정 추가해야한다.
- UserIntegrationTests.java

```java
@SpringBootTest(classes = ContentsApplication.class)
@AutoConfigureMockMvc
public class UserIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository; // 데이터베이스에 반영되었는지 확인
		...
}
```

- `@AutoConfigureMockMvc`
    - MockMvc 자동으로 만들어줌
    - `@Autowired` 가 IoC container에 등록

```java
@Test
@DisplayName("User 생성 통합 테스트")
public void whenPostUserDto_thenReturnJson()
        throws Exception {
    // Post할 UserDto 준비
    String username = "gildongH";
    String email = "gildong@gmail.com";
    String phone = "01012345678";
    UserDto userDto = new UserDto(null, username, email, phone, null, null);

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
```