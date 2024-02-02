# Spring Bean

## Bean

- Spring Container에서 관리하는 객체

## IoC Container

- Singleton Pattern 기반
- Bean객체 만든 뒤 IoC Container가 가지고 있다가 필요한 곳에 가져다 사용

## Spring Beans

- 구현된 기능상의 차이도 있지만 주된 목적은 역할의 표시
- 해당 클래스가 어떤 역할을 하는지 명확히 확인 가능
- Annotation 기반 관점 지향 프로그래밍
- 실제 구현보다 역할적인 구분에 초점이 맞춰져 있다.

### @Component

- 가장 기초가 되는 Annotation
- `@ComponentScan`의 대상

### @Controller

- MVC 패턴의 Controller
- 사용자 입력 담당 요소
- 요청을 받는 클래스임을 나타냄
- 주로 `@RequestMapping과 함께 사용

### @Service

- 서비스의 주요 흐름, 비즈니스 로직을 담당하는 요소
- 여러 요소들의 기능을 조합해 실제 서비스를 제공하기 위한 주요 기능 담당
- 데이터베이스를 조작한 결과를 가지고 동작을 결정
- `@Controller`로 부터 입력을 받고, `@Repository`로 부터 데이터 받아 이를 바탕으로 결정을 전달

### @Repository

- 데이터베이스와의 소통을 목적으로 하는 요소
- 데이터베이스와 소통하는 객체임을 나타내는 annotation

### @Configuration

- 프로젝트 내부에서 사용하기 위한 설정을 담고 있는 요소
- 컴포넌트의 동작과 직접적인 연관성이 적음

```java
// 가장 기초적인 Bean Annotation
@Component
public class AppComponent {
    private final AppConfigData configData;
    private AppComponent(AppConfigData configData) {
        this.configData = configData;
    }
}
```

### @Bean

- 메서드의 결과를 Bean 객체로 관리하고 싶을 때 사용

```java
@Configuration
public class AppConfig {
    @Bean
    // 메서드의 결과로 반환되는 객체를 Bean 객체로 활용
    public AppConfigData configData() {
        return new AppConfigData("url", "apiKey");
    }
}
```

- `Gson`
    - 외부 라이브러리 객체를 Bean으로 등록하는데 활용 가능

---

# Logging

- 코드가 일으킨 결과를 확인할 때 사용하던 것 → `System.out.println()`
- 애플리케이션이 실행중일 때 일어난 일 기록 → Log 남김
- Logging: 로그를 남기는 행위
- Logger: 로그를 작성하기 위해 사용하는 객체4

## Spring Boot 로깅

- 기본적인 로깅 의존성 다 포함되어 있음
- `Slf4j`
    - Simple Logging Facade 4 Java
    - java에서 로그를 남기는 방법을 통일해주는 프레임워크
    - 다양한 로그 프레임워크를 사용하는 방법의 Facade(디자인 패턴)
- Logback
    - 현재 Spring Boot에서 기본으로 사용하는 Logging 프레임워크
    - XML을 이용해 설정

### Logging 동작

- RootController.java
    - Slf4j에서 로거를 만들어주는 메서드
    - `@Slf4j` 는 이 부분을 자동으로 만들어주는 Lombok

```java
private static final Logger log = LoggerFactory.getLogger(RootController.class);
```

- 로거의 다섯가지 메서드

```java
log.trace("TRACE message");
log.debug("DEBUG message");
log.info("INFO message");
log.warn("WARN message");
log.error("ERROR message");
```

### Log Level

- 로그를 남길 때 정할 수 있는 중요도
- 중요도에 따라 출력되는 로그 조절 가능
- 어느 레벨을 정하면 그보다 더 높거나 같은 레벨의 로그만 보인다.
    - `TRACE` : 아주 단순한 작업에도 남기는 레벨
    - `DEBUG` : 구체적인 내부 플로우에 대하여 기록을 남기는 레벨, 디버깅에 활용하는 수준
    - `INFO` : 애플리케이션이 실행중일 때 정보 제공의 목적으로 남기는 레벨
    - `WARN` : 아직 문제가 되진 않았지만, 악영향이 있을 수 있는 레벨
    - `ERROR` : 애플리케이션이 정삭적으로 동작하지 않았을 때 남기는 레벨
- application.yaml

```java
logging:
	level:
		root: trace
```

## Logback 설정

- 특정 파일에 로그를 남기거나, 특정 날짜 까지의 기록만 남겨두고 싶을 때
- Spring Boot는 resources의 logback.xml 또는 logback-spring.xml 파일을 자동으로 사용

### `<appender>`

- Logback의 Appender인터페이스 설정
- 로그를 남기는 방식을 다양한 방식으로 제공
- `ConsoleAppender` : 터미널 화면에 로그를 남기기 위한 Appender
- `FileAppender` : 특정 파일에 로그를 남기기 위한 Appender
- `RollingFileAppender` : 특정 조건에 따라 파일의 개수 및 크기를 유지하면서 로그를 남기기 위한 Appender

### `<RollingPolicy>`

- 서버에 남아있는 로그를 일정한 양만 남기는 용도

```java
<rollingPolicy
        class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
    <fileNamePattern>${LOGS}/archived/rolling-file-log-%d{yyyy-MM-dd_HH-mm-ss}.%i.log
    </fileNamePattern>
    <maxFileSize>100MB</maxFileSize>
    <maxHistory>10</maxHistory>
    <totalSizeCap>1GB</totalSizeCap>
</rollingPolicy>
```

- `rolling-file-log-%d{yyyy-MM-dd_HH-mm-ss}.%i.log`
    - 시간이 바뀌는 주기마다 새로운 로그파일 생김
- `maxFileSize` : 로그 파일 당 크기 100MB
- `maxHistory` : 이 Appender가 유지하는 총 로그 파일의 개수
- `totalSizeCap` : 모든 로그 파일의 크기 합

### `<root>` & `<logger>`

- `LoggerFacotry` 로 만들어진 로거가 어떤 Appender를 사용할지 정하는 설정
- 패키지 단위로 로그의 레벨과 Appender를 설정 가능

### application.yaml 에서 설정파일 지정

- 어떤 Logback 설정을 사용할지를 전달

```java
logging:
	config: file:logback-spring.xml
```

---

# Profiles

## Spring Profiles

- 다양한 설정을 Profile 이라는 단위로 나누어서 사용할 설정을 애플리케이션 실행 시 결정할 수 있게 해주는 Spring의 기능
- 개발단계에서 사용할 데이터베이스와 테스트 코드용 데이터베이스를 나누거나 서비스할 때 로그를 줄이고 싶은 경우

### application-{profile}.yaml

- 설정 파일 나눠서 관리 가능
- 사용하고 싶은 profile 이름 정한다 → dev, test, prod 등
- 그 이름이 포함된 application-{profile}.yaml 을 만든다

## 기본 profile

- application.yaml 설정은 기본으로 실행된다
- 애플리케이션 실행 시 profile을 정하지 않았을 때 실행할 profile은 `spring.profiles.default`

### `spring.profiles.active`

- Spring Boot 실행 시 전달하여 사용할 profile 정하기

```java
java -Dspring.profiles.active=test -jar build/libs/contents-0.0.1-SNAPSHOT.jar
```

- 환경 변수를 사용할 수도 있음

```java
SPRING_PROFIES_ACTIVE=test java -jar build/libs/contents-0.0.1-SNAPSHOT.jar
```

## @Profile

- Profile에 따라 만들어질 Bean 객체 구별
- `@Value` : 설정 값을 필드에 할당