# Spring MVC CRUD

---

# CRUD - Create

## DTO

- Data Transfer Object
- 해당 객체가 통신을 통해 오가는 데이터를 나타내는 객체임을 명시
- Student.class

```java
package com.example.crud.model;

import lombok.Data;

// DTO - Data Transfer Object
// VO - Value Object
// DO - Data Object
// 학생의 데이터를 담기 위한 클래스이다

// Getter, Setter, EqualAndHashCode
@Data
public class StudentDto {
    // 데이터베이스의 PK
    private Long id;
    // 이름 정보
    private String name;
    // 이메일
    private String email;

}
```

- create.html

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<!-- 사용자가 데이터를 읿력할 수 있는 창구 -->
<h1>Create Student</h1>

<!-- 사옹자의 데이터를 받기 위한 form -->
<form action="/create" method="post">
<!-- 이름을 입력하기 위한 input -->
    <label for="name-input">
        Name: <input id="name-input" name="name">
    </label><br>
<!-- 이메일을 입력하기 위한 input -->
    <label for="email-input">
        Email: <input id="email-input" name="email">
    </label><br>
    <!-- 제출 버튼 -->
    <input type="submit">
</form>
</body>
</html>
```

- StudentController.java

```java
package com.example.crud.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class StudentController {
    // private static final Logger log = LoggerFactory.getLogger(StudentController.class);
    // /create-view로 요청이 왔을 때
    // create.html을 반환하는 메서드
    // @RequestMapping("/create-view")
    @GetMapping("/create-view")
    public String createView() {
        return "create";
    }

    // /create로 이름과 데이터를 보내는 요청을 받는 메서드
    @PostMapping("/create")
    public String create(
            @RequestParam("name") String name,
            @RequestParam("email") String email) {
        log.info(name);
        log.info(email);
        return "create";
    }
}
```

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/d2152b7f-cc08-4868-88c2-80db2f3750de/50d8c2a3-9100-481a-a15e-eae7ad307028/Untitled.png)

- studentService.java

```java
@Service
public class StudentService {
    // 현재 몇번째 데이터가 입력되었는지
    // 나중에 데이터베이스의 PK 역할
    private Long nextId = 1L;
    // 데이터를 담기 위한 리스트
    private final List<StudentDto> studentList = new ArrayList<>();
    // 사용자의 데이터를 받아
    // 새로운 학생 객체를 생성해 리스트에 저장
    public StudentDto createStudent(String name, String email) {
        StudentDto newStudent = new StudentDto(nextId, name, email);
        nextId++;
        studentList.add(newStudent);
        return newStudent;
    }
}
```

- 데이터가 생성되는것 확인 가능
## Post → redirect → Get

- 학생 데이터를 하나 생성한 후 새로고침을 누르게 되면 양식 다시 제출 확인이 뜸
- 이때 계속을 누르면 똑같은 데이터가 반복적으로 생성됨
- 계속은 브라우저에서 마지막으로 요청을 다시 보내는데,
- 마지막 요청은 html만 가져오는 `“/create-view”`가 아닌 해당 요청이기 때문

```java
    // /create로 이름과 데이터를 보내는 요청을 받는 메서드
    @PostMapping("/create")
    public String create(
            @RequestParam("name") String name,
            @RequestParam("email") String email) {
        log.info(name);
        log.info(email);
        return "create";
    }
```

→ Double Post Problem

- post가 연속적으로 보내진다
- 이를 해결하기 위해서, `“/create”`의 결과로는 다시 `“/create-view”`로 이동 필요
- `“/create”`의 return 값으로 `redirect` 설정

```java
return "redirect:/create-view";
```

---

# CRUD - Read

## Read All

- list에 존재하는 학생을 모두 가져옴
- StudentService.java
    - studentService에 존재하는 모든 학생을 반환하는 메서드 추가

```java
public List<StudentDto> readStudentAll() {
	 return studentList;
}
```

- home.html

```java
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org/">
<head>
    <meta charset="UTF-8">
    <title>Read All</title>
</head>
<body>
<h1>Student List</h1>
<!-- 학생이 없을 경우 -->
<div th:if="${studentList.isEmpty()}">
    <p>No Student Here...</p>
</div>

<!-- 학생이 있을 경우 -->
<div th:unless="${studentList.isEmpty()}" th:each="student: ${studentList}">
    <p>번호: [[${student.id}]]</p>
    <p>이름: <a th:href="@{/read/{id}(id=${student.id})}">[[${student.name}]]</a></p>
    <p>이메일: [[${student.email}]]</p>
</div>
<a href="/create-view">Create</a>
</body>
</html>
```

- `<a href="/create-view">Create</a>` 생성 페이지로 이동 링크 생성
- create.html 에도 `<a href="/home">Back</a>` 추가해 홈페이지로 이동할 수 있게 링크 생성
- StudentController.java

```java
// /home으로 요청을 받으면
// home.html에 studentList를 포함해 반환하는 메서드
@GetMapping("/home")
public String home(Model model) {
    model.addAttribute("studentList", service.readStudentAll());
    return "home";
}
```

## Read One

- id를 기준으로 원하는 학생을 찾아옴
- StudentService.java
    - `readStudent(Long id)`

```java
public StudentDto readStudent(Long id) {
    // studentList의 데이터를 하나씩 확인해서
    // getId가 id인 데이터를 반환하고
    for (StudentDto studentDto : studentList) {
        if(studentDto.getId().equals(id)) {
            return studentDto;
        }
    }
    // 없을 경우 null을 반환
    return null;
}
```

- read.html

```java
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org/">
<head>
    <meta charset="UTF-8">
    <title>Read One</title>
</head>
<body>
    <!--  학생 번호(id)와 이름을  -->
    <!--  id. name  -->
    <h1>[[${student.id}]]. [[${student.name}]]</h1>
    <!--  학생의 이메일을 출력  -->
    <!--  이메일: email  -->
    <p>이메일: [[${student.email}]]</p>
    <!--  홈으로 가는 링크  -->
    <a href="/home">Back</a>
</body>
</html>
```

- StudentController.java
    - `@PathVariable` : 경로상의 변수를 매개변수에 할당할 때 사용

```java
// /read로 요청을 받으면
// read.html에 student 정보를 포함해 반환하는 메서드
// Mapping에 {}를 넣으면 그 안에 들어가 있는 데이터를
// 매개변수에 할당해 줄 수 있다
@GetMapping("/read/{id}")
public String readOne(
        @PathVariable("id") Long id,
        Model model) {
    StudentDto dto = service.readStudent(id);
    model.addAttribute("student", dto);
    return "read";
}
```

- /home 에서 /read로 이동
    - home.html의 이름 옆에 해당 코드 추가

```java
<a th:href="@{/read/{id}(id=${student.id})}">[[${student.name}]]</a>
```

- Thymeleaf 활용
    - `th:` 를 추가하면 해당 속성은 Thymeleaf가 동적으로 할당함

# CRUD - Update

- 데이터 갱신
- 대상 데이터를 가져온 다음 갱신할 새로운 데이터 제공해야 함
- update.html

```java
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Update</title>
</head>
<body>
<!-- 사용자가 데이터를 입력할 수 있는 창구 -->
<h1>Update: [[${student.id}]]. [[${student.name}]]</h1>
<!-- 사옹자의 데이터를 받기 위한 form -->
<form th:action="@{/update/{id}(id=${student.id})}" method="post">
    <!-- 이름을 입력하기 위한 input -->
    <label for="name-input">
        <!-- 원본의 데이터를 기본값으로 설정해야 한다 -->
        Name: <input id="name-input" name="name" th:value="${student.name}">
    </label><br>
    <!-- 이메일을 입력하기 위한 input -->
    <label for="email-input">
        Email: <input id="email-input" name="email" th:value="${student.email}">
    </label><br>
    <!-- 제출 버튼 -->
    <input type="submit">
</form>
<!-- 수정중에 뒤로가면, 원래 데이터 읽기 창으로 -->
<a th:href="@{/{id}(id=${student.id})}">Back</a>
</body>
</html>
```

- `Update: [[${student.id}]]. [[${student.name}]]` : 갱신 대상 정보 표현
- `th:value="${student.name}"` , `th:value="${student.email}"` : 이미 가지고 있는 데이터 기본값으로 사용
- StudentController.java
    - `updateView()` 추가

```java
// /update-view/{id}로 요청을 받으면
// update.html에 student 정보를 포함해 반환하는 메서드
@GetMapping("/update-view/{id}")
public String updateView(
        @PathVariable("id") Long id,
        Model model
) {
    StudentDto dto = service.readStudent(id);
    model.addAttribute("student", dto);
    return "update";
}
```

- StudentService.java

```java
// 어떤 학생의 정보를 바꿀건지를 나타내는 id
// 그 학생의 새로운 정보 name, email
public StudentDto updateStudent(Long id, String name, String email) {
    for (StudentDto studentDto : studentList) {
        if (studentDto.getId().equals(id)) {
            studentDto.setName(name);
            studentDto.setEmail(email);
            return studentDto;
        }
    }
    return null;
}
```

- StudentController.java
    - `update()` 추가
    - update된 데이터 전송 하면 `read/{id}`에서 수정된 페이지 보여줌

```java
@PostMapping("/update/{id}")
public String update(
        @PathVariable("id") Long id,
        @RequestParam("name") String name,
        @RequestParam("email") String email
) {
    StudentDto dto = service.updateStudent(id, name, email);
    return String.format("redirect:/read/%s",dto.getId());
}
```

# CRUD - Delete

- StudentService.java
    - `deleteStudent()` 메서드 추가

```java
// id를 바탕으로 학생을 제거하는 메서드
public void deleteStudent(Long id) {
    // 리스트의 각 원소를 확인하면서
    int target = -1; // 몇 번째 원소를 저장하면 되는지
    // 주어진 id와 일치하는 원소가 있으면, 그 index를 저장하고
    for (int i = 0; i < studentList.size(); i++) {
        if (studentList.get(i).getId().equals(id)) {
            target = i;
            break;
        }
    }

    // 그 index의 위치에 있는 데이터를 studentList에서 제거
    if (target != -1) {
        studentList.remove(target);
    }
}
```

- StudentController.java
    - `delete()` 추가

```java
@PostMapping("/delete/{id}")
public String delete(@PathVariable("id") Long id) {
    service.deleteStudent(id);
    return "redirect:/home";
}
```

---

# MyBatis

![Untitled](/Training/image/240104.png)

- JDBC를 활용해 만들어진 프레임워크
- JDBC를 사용하는 방법을 높은 수준에서 추상화
- interface 메서드에 SQL 연결 → 메서드 호출 시 SQL 실행
- 인자와 결과를 java 객체로서 활용 가능
- 동적인 SQL 사용 가능

## Spring Boot에서 MyBatis 사용하기

- Spring Boot에서 많이 사용하지만, Spring Boot의 일부는 아님
- build.gradle 의 `dependencies` 에 추가 필요
    - SQLite를 사용하기 위해, JDBC 드라이버 수동 설치

```java
implementation 'org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.3'
// sqlite
runtimeOnly 'org.xerial:sqlite-jdbc:3.41.2.2'
```

- application.yaml
    - `spring.datasource.url` : 데이터베이스에 접속하기 위한 URL
    - `spring.datasource.driver-class-name` : 데이터베이스를 사용할 때 사용할 JDBC 드라이버, 사용하는 RDBMS에 따라 다름
    - `spring.datasource.username / password` : 데이터베이스의 접속 정보 작성
    - `mybatis.mapper-locations` : SQL이 정의된 mapper.xml 파일의 위치
    - `mybatis,type-aliases-package=com.example.mybatis.model`: 위에서 언급한 XML 파일에서 사용할 JAVA 클래스의 패키지
    - `mybatis.configuration.map-underscore-to-camel-case=true` : snake_case와 camelCase간 자동 변환

```java
spring:
  # Spring boot 내부에서 데이터베이스 사용시 공유하는 설정
  datasource:
    # 예전에 JDBC Connection 만들때 제공했던 JDBC URL
    url: jdbc:sqlite:db.sqlite
    # 어떤 데이터베이스를 쓰느냐에 따라 어떤 Driver를 사용해야 하는지가 달라진다.
    driver-class-name: org.sqlite.JDBC
    # 다른 데이터베이스의 경우 아이디와 비밀번호가 필요하다.
    # username: sa
    # password: password
    
# MyBatis 관련 설정
mybatis:
  mapper-locations: "classpath:/mybatis/mapper/*.xml"
  type-aliases-package: "com.example.crud.model"
  configuration:
    map-underscore-to-camel-case: true
```

# Annotation을 이용한 SQL 작성

- StudentDao.java
    - DAO - Data Access Object
    - `@Repository` 이용해 Spring Bean으로 등록

```java
@Repository
public class StudentDao {
    // MyBatis와 데이터베이스를 연결해주는 객체
    private final SqlSessionFactory sessionFactory;
    // Spring Boot안에 만들어진 SqlSessionFactory Bean이 자동으로 주입된다.
    public StudentDao(SqlSessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
```

## Create

- StudentMapper.java

```java
// INSERT
@Insert("INSERT INTO student(name, email)" +
        "VALUES(#{name}, #{email});")
void insertStudent(StudentDto dto);
```

- StudentDao.java

```java
// StudentDto를 받아 학생을 데이터베이스에 추가하는 메서드
public void createStudent(StudentDto dto) {
    try (SqlSession session = sessionFactory.openSession()){
        StudentMapper mapper = session.getMapper(StudentMapper.class);
        mapper.insertStudent(dto);
    }
}
```

- StudentService.java

```java
public void createStudent(String name, String email) {
    StudentDto dto = new StudentDto();
    dto.setName(name);
    dto.setEmail(email);
    dao.createStudent(dto);
}
```

## Select

### Select All

- StudentMapper.java
    - `electStudentAll()` 호출 시 `"SELECT * FROM student;"` 실행

```java
@Mapper
public interface StudentMapper {
    // MyBatis는 Interface의 메서드에 SQL을 연결한다
    // MyBatis의 세션을 이용해 interface의 메서드를 실행하면
    // 연결된 SQL이 실행된다
    @Select("SELECT * FROM student;")
    List<StudentDto> selectStudentAll();
}
```

- StudentDao.java

```java
// 데이터베이스에서 학생 데이터를 전부 불러오는 메서드
public List<StudentDto> readStudentsAll() {
    // SqlSession은 MyBatis와 데이터베이스가 연결되었다는 것을 상징하는 객체
    try (SqlSession session = sessionFactory.openSession()) {
        // Mapper 인터페이스를 가져온다.
        StudentMapper mapper = session.getMapper(StudentMapper.class);
        return mapper.selectStudentAll();
    }
}
```

- `SqlSession`  : DataBase와 세션을 나타내는 객체, Mapper interface 제공
- `mapper.selectStudentAll()` : Mapper interface의 SQL문이 실행됨
- StudentService.java

```java
// 현재 등록된 모든 학생을 반환한다
public List<StudentDto> readStudentAll() {
//        return studentList;
    return dao.readStudentsAll();
}
```

### Select One

- 특정 id를 기준으로 학생을 조회
- StudentMapper.java

```java
// Select One
@Select("SELECT * FROM student WHERE id = #{id};")
StudentDto selectStudent(Long id);
```

- StudentDao.java
    - `readStudent()`

```java
// id를 Long으로 받아 데이터베이스에서 id가 같은 줄을 반환하는 메서드
public StudentDto readStudent(Long id) {
    try (SqlSession session = sessionFactory.openSession()) {
        StudentMapper mapper = session.getMapper(StudentMapper.class);
        return mapper.selectStudent(id);
    }
}
```

- StudentService.java

```java
public StudentDto readStudent(Long id) {
    return dao.readStudent(id);
}
```

## Update

- StudentMapper.java

```java
// Update
@Update("UPDATE student SET " +
        "name = #{name}, " +
        "email = #{email} " +
        "WHERE id = #{id}")
void updateStudent(StudentDto dto);
```

- StudentDao.java

```java
public void updateStudent(StudentDto dto) {
    try (SqlSession session = sessionFactory.openSession()) {
        StudentMapper mapper = session.getMapper(StudentMapper.class);
        mapper.updateStudent(dto);
    }
}
```

- StudentService.java

```java
public void updateStudent(StudentDto dto) {
    dao.updateStudent(dto);
}
```

## Delete

- StudentMapper.java

```java
// Delete
@Delete("DELETE FROM student " +
        "WHERE id = #{id}")
void deleteStudent(Long id);
```

- StudentDao.java

```java
public void deleteStudent(Long id) {
    try (SqlSession session = sessionFactory.openSession()) {
        StudentMapper mapper = session.getMapper(StudentMapper.class);
        mapper.deleteStudent(id);
    }
}
```

- StudentService.java

```java
public void deleteStudent(Long id) {
		dao.deleteStudent(id);
}
```