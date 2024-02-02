# Spring Boot

---

# Client - Server Model

- client가 server에 요청을 보내면 server에서 적절한 응답을 돌려줌

![Untitled](/Training/image/Untitled.png)
## Client

- 인터넷에 연결된 장치 or 소프트웨어 프로세스
- 서비스를 요청하는 주체

## Server

- 사용자에게 전송될 데이터 또는 기능이 저장된 프로세스(컴퓨터)
- 요청에 대한 적당한 응답을 하는 주체

## URL

- 브라우저의 주소창
- 어디에 요청을 보낼지를 입력하는 창

```jsx
<scheme>://<authority>/<path>?<query>#<anchor>
```

- 백엔드 개발자는 URL을 기준으로 요청이 들어왔을 때 어떤 응답을 반환하도록 하며 데이터를 관리함
- Path와 Query를 기준으로 어떤 응답을 할 것 인지 결정

## Framework

- `new ServerSocket(port)`를 사용해 사용자에게 요청 받는 코드 작성 가능하지만 매번 작성하기엔 어려움 존재 → 간소화
    - 인터넷으로 들어온 요청을 처리할 때
        1. 서버 프로세스 실행
        2. 특정 포트에서 들어오는 신호 받음
        3. 해당 신호를 데이터의 형태로 해석
        4. 요청을 보낸 컴퓨터로 응답 전달
- 비즈니스 로직만 생각하면 되도록 반복된 기능을 만들어 둠
- 개발에 필요한 기능을 미리 구현해 놓은 것
- Spring Boot - 웹 서비스 개발을 위한 프레임워크

---

# Spring Boot 시작하기

## Spring Intialzr

- Group - 주로 프로젝트 조직. domain과 반대로 작성
- Packaging 형식 지정 - Jar, War
- Dependencies - 의존성

---

- DemoApplication.java

```java
@SpringBootApplication
public class DemoApplication {
	SpringApplication.run(DemoApplication.class, args);
}
```

## Controller로 HTML 전달

- `@Controller` / `@RequestMapping` 사용
- `@Controller` - URL에 따른 요청을 처리하는 메서드를 담아두는 클래스임을 나타냄
- `@RequestMapping` - 어떤 URL 요청에 대하여 실행되는 메서드임을 명시

---

- DemoController.java

```java
@Controller
public class DemoController {
		@RequestMapping("home")
    public String home() {
        component.sayHello();
        return "home.html";
    }
}
```

- home.html

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    HELLO WORLD <a href="http://localhost:8080/profile">Profile</a>
</body>
</html>
```

---

# Inversion of Control

- 제어의 역전
- `DemoApplication` 을 인자로 전달 → `SpringApplication.*run*(DemoApplication.class, args)`
- `Democontroller`를 사용하려면 객체를 생성하고 배치하여 사용하는 것이 일반적인데, 객체를 직접 만들지 않고 사용할 수 있음
- 클래스를 만드는 주체는 개발자지만 클래스 인스턴스가 언제 만들어저 실행되는지 시점을 결정하는 주체는 Spring Boot
- 코드를 제어하는 주체가 반전됨 → IoC 패턴

## Spring의 IoC Container

- 개발자가 작성한 클래스와 몇 가지 설정 정보를 바탕으로, 해당 클래스 객체의 Lifecycle을 관리하는 Spring의 IoC 패턴 구현체
- IoC Container가 관리하는 객체 → **Bean**
- Spring은 Bean객체를 만든 뒤 IoC Container가 이를 가지고 있다가 필요한 곳에 가져다 사용함

### `@Controller`

- DemoController.java

```java
@Controller
public class DemoController {
		@RequestMapping("home")
    public String home() {
        component.sayHello();
        return "home.html";
    }
}
```

- 직접 실행하지 않고 IoC Container에서 관리
- 요청이 왔을 때 어떻게 처리해야 할 지에 대한 정보 가지고 있음

### `@SpringBootApplication`

- 실제로는 세가지 어노테이션이 합쳐져 있음
    - `@EnableAutoConfiguration`
        - 클래스를 Spring Boot로서 자동 설정 하게하는 어노테이션
    - `@ComponentScan`
        - 클래스를 기준으로 Bean 객체를 검색하게 하는 어노테이션.
        - `@Component` 스캔
        - 이때 `@Controller`도 `@Component`의 일종
    - `@SpringBootConfiguration`
        - Spring Boot 설정 관련 어노테이션
- DemoApplication.java

```java
@SpringBootApplication
public class DemoApplication {
		SpringApplication.run(DemoApplication.class, args);
}
```

- 이 클래스를 기준으로 Bean 객체를 찾아서 관리
- 클래스가 존재하는 패키지 기준으로 주변의 Bean 객체를 찾아 IoC Container에서 관리함
- 현재 실행중인 IoC Container 반환

```java
ApplicationContext applicationContext = SpringApplication.run(DemoApplication.class, args);
```

- IoC Container가 관리하고 있는 Bean 객체 확인

```java
for (String beanName : applicationContext.getBeanDefinitionNames()) {
			System.out.println(beanName);
}
```

## Dependency Injection

- Spring의 IoC Container는 Bean 객체가 필요한 시점에 적절하게 필요한 Bean을 공급하는 기능을 가지고 있음
- DemoController.java

```java
private HelloComponent component; // 필요. 의존 메서드
// IoC Container가 만들어놓은 컴포넌트를 필요한 시점에 주입
public DemoController(HelloComponent component) {
    this.component = component;
}
```

---

# Spring MVC

## MVC 패턴

![Untitled](/Training/image/Untitled%20(1).png)

- 사용자 인터페이스를 비즈니스 로직으로부터 분리하는 것을 목표로 만들어진 디자인 패턴
- Model
    - 어플리케이션을 표현하기 위한 데이터 관리하는 부분
- View
    - 사용자에게 정보가 표현되는 방식을 관리하는 부분
- Controller
    - 사용자의 입력을 받아, Model이 이해할 수 있는 형태로 변환

## Spring MVC 패턴

![Untitled](/Training/image/Untitled%20(2).png)

---

# Thymeleaf

## Template Engine

- HTML의 내용물을 동적으로 변환해주는 라이브러리의 일종

## Thymeleaf 사용

- MvcController.java

```java
@Controller
public class MvcController {
    // text로 요청이 오면 text.html을 반환하는 메서드
    @RequestMapping("/text")
    // Model은 View애 사용할 데이터를 모아두는 객체
    public String text(Model model) {
        // attiributeValue로 String 값
        model.addAttribute("message", "Hello Templates!");
      return "text";
}
```

- text.html

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org/">
<head>
    <meta charset="UTF-8">
    <title>Thymeleaf Text</title>
</head>
<body>
    <!-- th:text는 요소의 내용 전체를 바꾼다 -->
    <h1 th:text="${message}">여기의 내용을 동적으로 바꾸고 싶습니다</h1>
    <!-- 요소의 일부만 바꾸고 싶다면 [[${data}]] -->
    <p>받은 메시지: [[${message}]]</p>
</body>
</html>
```

- `th:text` - HTML 요소의 내용을 전달받은 값으로 대치
- `${message}` - model에서 넘겨받은 이름이 message인 데이터로 대치
- `[[${message}]]` - HTML 내부의 일부분만 message의 값으로 대치
- 객체를 이용해서 데이터를 표현할 수 있음
    - [student.java](http://student.java) 클래스

    ```java
    public class Student {
        private String name;
        private String email;
    
        public Student(String name, String email) {
            this.name = name;
            this.email = email;
        }
    
        public String getName() {
            return name;
        }
    
        public String getEmail() {
            return email;
        }
    
        public void setName(String name) {
            this.name = name;
        }
    
        public void setEmail(String email) {
            this.email = email;
        }
    }
    ```

- MvcController.java

```java
// text-obeject로 요청이 오면
// text-object.html을 반환하는 메서드
@RequestMapping("/text-object")
public String textObject(Model model) {
    Student alex = new Student("Alex", "alex@gmail.com");
    // attiributeValue로 객체 전달
    model.addAttribute("object",alex);
    return "text-object";
}
```

- text-object.html

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Thymeleaf Text</title>
</head>
<body>
    <!-- 두 개의 p 요소로 이름과 이메일을 나타내기 -->
    <p>이름: [[${object.name}]]</p>
    <!-- Getter가 존재하는 속성 또는 메서드 사용 가능 -->
    <p>이메일: [[${object.email}]]</p>
</body>
</html>
```

- 객체의 속성을 사용할 때는 `public` 속성이거나, `getter` 반드시 생성해야함

## 조건부 표현

### th:if / th:unless

- MvcController.java

```java
// is-logged-in으로 요청이 오면
// if-unless.html을 반환하는 메서드
@RequestMapping("/is-logged-in")
public String isLoggedIn(Model model) {
    model.addAttribute("isLoggedIn", true);
    return "if-unless";
}
```

- if-unless.html

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org/">
<head>
    <meta charset="UTF-8">
    <title>If Unless</title>
</head>
<body>
<!-- 로그인 여부를 Model로 판단하고, -->
<!-- 보이는 문구를 바꾸는 View -->
<h1>Welcome.</h1>
<div th:if="${isLoggedIn}">
    <p>You are logged in.</p>
</div>
<div th:unless="${isLoggedIn}">
    <p>Please log in.</p>
</div>
</body>
</html>
```

- `th:if`  - 주어진 조건이 `true` 일 때 표현
- `th:unless`  - 주어진 조건이 `false` 일 때 표현

## 복수의 데이터 표현

### th:each

- 리스트 데이터 반복
- MvcController.java

```java
// /each로 요청이 오면 each.html을 반환하는 메서드
@RequestMapping("/each")
public String each(Model model) {
    // 여러 데이터를 가진 객체를 model에 전달
    List<String> listOfStrings = new ArrayList<>();
    listOfStrings.add("Alex");
    listOfStrings.add("Brad");
    listOfStrings.add("Chad");
    model.addAttribute("itemList", listOfStrings);
		return "each";
}
```

- each.html

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org/">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h1>Item List</h1>
<div>
    <!--  <p>아이템 하나당 하나의 p</p> -->
    <p th:each="item: ${itemList}">[[${itemList}]]</p>
</div>
</body>
</html>
```

- `th:each="item: ${itemList}">[[${itemList}]]` 은 하위 코드와 동일한 기능을 함

```java
for (String item : listOfStrings) {
     // listOfStrings 의 아이템을 하나씩 출력한다
     System.out.println(item);
}
```

- 객체를 사용한 데이터 반복도 가능
- MvcController.java

```java
// /each로 요청이 오면 each.html을 반환하는 메서드
@RequestMapping("/each")
public String each(Model model) {
// 여러 학생(Student) 데이터를 담은 리스트를 모델에 전달
    List<Student> studentList = new ArrayList<>();
    studentList.add(new Student("Alex", "alex@gmail.com"));
    studentList.add(new Student("Brad", "brad@gamil.com"));
    studentList.add(new Student("Chad", "chad.com"));
    studentList.isEmpty();
    model.addAttribute("studentList", studentList);
    return "each";
}
```

- each.html

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org/">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>e
<body>
<h1>Student List</h1>
<!-- 한 명의 학생마다
    <div>
        <p>이름: 이름</p>
        <p>이메일: 이메일</p>
    </div> -->
<!-- 만약 studentList가 비어있을 경우-->
<div th:if="${studentList.isEmpty()}">
    <p>No Student Here...</p>
</div>
<!-- 한명이라도 student가 있는 경우-->
<div    th:unless="${studentList.isEmpty()}"
        th:each="student: ${studentList}"
>
    <p>이름: [[${student.name}]]</p>
    <p>이메일: [[${student.email}]]</p>
</div>
</body>
</html>
```

---

# HTML Form

## HTTP Method

- HTTP는 통신 규약으로 지켜야하는 일련의 규칙
- `GET` - 어떤 데이터를 조회하기 위한 요청
- `POST` - 어떤 데이터를 전송하기 위한 요청

## Post

- FormController.java

```java
@Controller
public class FormController {
    // 1. 사용자에게 표기하고 싶은 메시지를 전달할 수 있는
    // HTML을 반환하는 메서드
    /* @RequestMapping("/send") */
    @GetMapping("/send")
    public String getForm() {
        return "send";
    }
}
```

## `@RequestParam`

- 요청의 값을 메서드의 인자로 전달하는 표시
- 사용자가 전송한 데이터를 Controller에서 사용하려면 form 내부의 input 요소의 name 속성을 기준으로 사용함
- FormController.java

```java
@Controller
public class FormController {
    // 2. 사용자가 전달한 데이터를 처리할 수 있는 메서드
    /*
    // method 인자로 들어오는 요청의 HTTP method를 한정시킬 수 있다
    @RequestMapping(value="/receive", method = RequestMethod.POST)
     */
    // PostMapping은 명시적으로 Post 요청만 처리한다
    @PostMapping("/receive")
    public String receiveData(
            // @RequestParam
            // 사용자가 보낸 요청의 데이터를 받는 목적의 매개변수임을 표기
            @RequestParam("message")
            String message,
            Model model
    )
    {
        System.out.println(message);
        model.addAttribute("message", message);
        return "receive";
    }
}
```

- send.html

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Send Data</title>
</head>
<body>
<h1>Send Message</h1>
<!-- action: Form이 데이터를 보낼 URL -->
<!-- method: 어떤 요청을 보낼지
(데이터를 조회하는(get) 또는 전송하는(post)) -->
<!-- HTTP GET: 데이터를 조회하는 요청에 적용하는 메서드 -->
<!-- HTTP POST: 데이터를 보내는 요청에 적용하는 메서드 -->
<form action="/receive" method="post">
    <label for="message-input">Message:
        <input type="text" id="message-input" name="message">
    </label><br>
    <!-- 양식 제출 버튼 -->
    <input type="submit" value="Send Message">
</form>
</body>
</html>
```

- receive.html

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <h1>Receive Message</h1>
    <p>Message: [[${message}]]</p>
    <a href="/send">Go Back</a>
</body>
</html>
```