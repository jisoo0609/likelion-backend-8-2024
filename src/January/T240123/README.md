# Exception

## ResponseStatusException

- 어디서든 간편하게 사용 가능
- 별도의 작업 없이 사용 가능
- 똑같은 코드 반복하게 되는 단점 존재

```java
throw new ResposneStatusException(HttpStatus.*NOT_FOUND*);
```

## `@ExceptionHandler`

- Controller 내부에서 지정한 예외가 발생하면 실행되는 메서드
- RequestMapping 메서드 처럼 응답

## `@ControllerAdivce` & `@RestControllerAdvice`

- 프로젝트 전역 예외 처리
- `@ExceptionHandler` 를 모아두기 위한 Component의 일종
- `@ControllerAdivce` & `@RestControllerAdvice` 는 `@Controller` & `@RestController` 와 같은 관계

### GlobalControllerAdvice.java

- 예외처리를 위한 ExceptionHandler 모아놓기 위한 클래스
- 전체 애플리케이션에서 발생하는 예외를 처리할 수 있음
- 사용자에게 유효한 응답 만들 수 있음

```java
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
}
```

## Custom Exception

- 프로젝트가 커짐에 따라 일관성 있는 예외처리를 위해 예외를 직접 정의 가능
- 이름에 상황에 대한 정보 바로 표현 가능
- 상속 관계를 활용하여 `ExceptionHandler` 에서 동시에 예외처리 가능

### Status400Exception.java

- 400 응답을 발생시키기 위한 부모 추상 예외 클래스

```java
public class Status400Exception extends RuntimeException {
    public Status400Exception(String message) {
        super(message);
    }
}
```

### UsernameExistException.java

- 사용자 이름이 중복일 때 발생하는 예외
- [Status400Exception.java](http://Status400Exception.java) 를 상속받음

```java
public class UsernameExistException extends Status400Exception {
    public UsernameExistException() {
        super("user exists");
    }
}
```

### GlobalControllerAdvice.java

- ControllerAdvice에서 활용
- `@ExceptionHandler` 에 클래스를 받아 하나의 `@ExceptionController` 에서 관리 가능
- Status400Exception

```java
@ExceptionHandler(Status400Exception.class)
@ResponseStatus(HttpStatus.BAD_REQUEST)
public ErrorDto handle400(final Status400Exception exception) {
    ErrorDto dto = new ErrorDto();
    dto.setMessage(exception.getMessage());
    return dto;
}
```

- UsernameExistException

```java
@ExceptionHandler(UsernameExistException.class)
@ResponseStatus(HttpStatus.BAD_REQUEST)
public ErrorDto handleUsernameExists(final UsernameExistException exception) {
    ErrorDto dto = new ErrorDto();
    dto.setMessage(exception.getMessage());
    return dto;
}
```

---

# 유효성 검사 (Validation)

- 사용자가 입력한 데이터가 허용된 형태인지 검사하는 과정

## Spring-boot-starter-validation

- Jakarta Bean Validation
    - 유효성 검증을 위한 기술 명세 → `@Valid`
    - 어떤 항목이 어떤 규칙을 지켜야 하는지를 표시하는 기준
- Hibernate Validation
    - Jakarta Bean Validation을 토대로 실제 검증해주는 프레임워크
- 둘의 관계는 JPA - Hibernate ORM과 유사

```java
implementation 'org.springframework.boot:spring-boot-starter-validation'
```

# `@Valid` 사용하기

## UserDto

```java
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    // 사용자가 입력하는 데이터 중 검증하고 싶은 데이터
    // 사용자 계정은 8글자 이상
    @NotNull
    @Size(min = 8, message = "8자는 넣어주세요.")
    private String username;
    private String email;
}
```

- `@NotNull`
    - 제약사항을 명시하는 Annotation
    - Username의 값 Null 허용하지 않음

## UserController

```java
@Slf4j
@Validated
@RestController
public class UserController {
@PostMapping("/validate-dto")
public String validateDto(
        // 이 데이터는 입력을 검증해야 한다
        @Valid
        @RequestBody
        UserDto dto
) {
    log.info(dto.toString());
    return "done";
}
```

- `@Valid`
    - 해당 인자에 대해 유효성 검증
    - username 없이 요청을 보내면 400 에러 발생시킴

## `@Valid`

- 사용 가능 어노테이션
- `@NotNull`
    - Null 값 허용하지 않음
- `@NotEmpty`
    - String, Collection, Array의 길이가 0이 아님
    - .size() 등
- `@NotBlank`
    - 문자열이 공백이 아님
    - 공백으로 표현되는 문자 외의 문자가 존재

## MethodArgumentNotValidException

- 응답이 복잡해지고, 지나치게 많은 정보가 포함된다 판단 될 때, 유효성 검사 실패 시 응답은 `MethodArgumentNotValidException` 대상으로 예외처리 한다.

### 1. /groups/MandatoryStep.java Interface 만듦

```java
public interface MandatoryStep {}
```

### 2. UserPartialDto.java에 `groups = MandatoryStep.class` 로 검증용 데이터 하나로 묶음

- UserPartialDto.java

```java
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserPartialDto {
    // 두가지는 회원가입 단계에서 반드시 첨부해야 하는 데이터
    // 단 회원 정보 업데이트 단계에서는 반드시는 아님
    @Size(min = 8, groups = MandatoryStep.class)
    @NotNull(groups = MandatoryStep.class)
    private String username;
    @Size(min = 10, groups = MandatoryStep.class)
    @NotNull(groups = MandatoryStep.class)
    private String password;

    // 두가지는 회원가입 완료 후 추가 정보 기입 단계에서 첨부하는 데이터
    // 단, 추가정보 기입시에는 반드시 포함해야 한다.
    @NotNull
    @Email
    private String email;
    @NotNull
    private String phone;
}
```

### 3. Controller에서 `@Validated` 사용 해 검증

- UserController.java

```java
@ExceptionHandler(MethodArgumentNotValidException.class)
@ResponseStatus(HttpStatus.BAD_REQUEST)
public Map<String, Object> handleValidationException(
        final MethodArgumentNotValidException exception
) {
    Map<String, Object> errors = new HashMap<>();
    // 예외가 가진 데이터를 불러오기
    List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

    // 각각의 에러에 대해서 순환하며
    for (FieldError error : fieldErrors) {
        String fieldName = error.getField();;
        String errorMessage = error.getDefaultMessage();
        errors.put(fieldName, errorMessage);
    }
    return errors;
}
```

```java
@ExceptionHandler(ConstraintViolationException.class)
@ResponseStatus(HttpStatus.BAD_REQUEST)
public Map<String, Object> handleConstraintException(
        final ConstraintViolationException exception
) {
    Map<String, Object> errors = new HashMap<>();
    Set<ConstraintViolation<?>> violations
            = exception.getConstraintViolations();
    for (ConstraintViolation<?> violation: violations) {
        String property = violation.getPropertyPath().toString();
        String message = violation.getMessage();
        errors.put(property, message);
    }
    return errors;
}
```
# Validation

## 사용자 지정 유효성 검사

1. Annotation 만들기
2. ConstraintValidator

## WhiteList Annotation

### 1. /annotations/EmailWhiteList.java Interface 구현

```java
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailWhiteList {
    String message() default  "Email not in whitelist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
```

- `@Target` : Annotation이 첨부될 수 있는 영역
- `@Retention` : Annotation이 언제까지 붙어있어야 할지 (Compile, Runtime 등)
- `@Documented` : Annotation이 문서에도 붙어있어야 하는지

### 2. /EmailWhitelistValidator.java 구현

- 실제 유효성 검사 로직이 담기는 클래스

```java
public class EmailWhitelistValidator implements ConstraintValidator<EmailWhiteList, String> {

    private final Set<String> whiteList;
    public EmailWhitelistValidator() {
        this.whiteList = new HashSet<>();
        this.whiteList.add("gmail.com");
        this.whiteList.add("naver.com");
        this.whiteList.add("kakao.com");
    }

    @Override
    public boolean isValid(
            // value: 실제로 사용자가 입력한 내용이 여기 들어온다.
            String value,
            ConstraintValidatorContext context
    ) {
        // value가 null인지 체크하고, (null이면 false)
        if (value == null) return false;
        // value에 @가 포함되어 있는지 확인하고, (아니면 false)
        if (!value.contains("@")) return false;
        // value를 @ 기준으로 자른 뒤, 제일 뒤가 `this.whiteList`에
        // 담긴 값 중 하나인지 확인을 한다.
        String[] split = value.split("@");
        String domain = split[split.length - 1];
        return whiteList.contains(domain);
    }
}
```

- 생성자 추가
- `isValid()`
  1. value가 null인지 체크해서 null이면 false
  2. value에 @가 포함되어있지 않으면 false
  3. @를 기준으로 제일 위가 `this.whiteList` 인지 확인한다

### 3. /annotations/EmailWhiteList.java Interface에 `@Constraint` 추가

```java
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailWhitelistValidator.class)
public @interface EmailWhiteList { ... }
```

### 4. Dto에 `@EmailWhiteList` 추가해서 사용

```java
public class UserDto {
		...  	
		@Email
		@EmailWhiteList
    private String email;
		...
}
```

## BlackList Annotation

### 1. /annotations/EmailBlackList.java Interface 구현

```java
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailBlackList {
    String message() default "Email in blackList";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String[] blacklist() default {};
}
```

### 2. /EmailBlacklistValidator.java 구현

```java
public class EmailBlacklistValidator implements ConstraintValidator<EmailBlackList, String> {
    private Set<String> blackList;

    @Override
    public void initialize(EmailBlackList annotation) {
        this.blackList = new HashSet<>();
        for (String blocked : annotation.blacklist()) {
            this.blackList.add(blocked);
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        //value가 null인지 체크하고 (null 이면 false)
        if (value == null) return false;
        // value에 @가 포함되어 있는지 확인하고 (아니면 false)
        if (!value.contains("@")) return false;
        // value를 @ 기주능로 자른 뒤, 제일 뒤가 `this.blackList`에
        // 담긴 값이 아닌지 확인한다
        String[] split = value.split("@");
        String domain = split[split.length - 1];

        return !blackList.contains(domain);
    }
}
```

- `isValid()`
  1. value가 null인지 체크해서 null이면 false
  2. value에 @가 포함되어있지 않으면 false
  3. @를 기준으로 자른 뒤, 제일 뒤가 `this.blacklist`에 담긴 값이 아닌지 확인한다. → 아니면 true

### 3. /annotations/EmailBlackList.java Interface에 `@Constraint` 추가

```java
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailBlacklistValidator.class)
public @interface EmailBlackList {...}
```

### 4. Dto에 `@EmailBlackList` 추가해서 사용

```java
public class UserDto {
		...
    @Email(message = "Email을 넣어주세요.")
    @EmailBlackList(blacklist = "malware.good")
    private String email;
		...
}
```