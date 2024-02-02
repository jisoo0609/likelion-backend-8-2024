# MyBatis

---

# XML로 SQL 작성

- XML과 interface의 연결 필요
- StudentXMLMapper.java

```sql
public interface StudentXMLMapper {

}
```

- StudentMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!-- 어떤 interface와 연결할 xml 인지-->
<mapper namespace="com.example.crud.mapper.StudentXMLMapper">
```

### Select

- StudentMapper.xml

```xml
<!-- 실행하고 싶은 SQL을 넣을 수 있음-->
<select id="selectStudentAll" resultType="com.example.crud.model.StudentDto">
    SELECT * FROM student;
</select>
```

```xml
<select id="selectStudent" parameterType="Long" resultType="com.example.crud.model.StudentDto">
    SELECT * FROM student WHERE id = #{id};
</select>
```

- `id` : 연결하고자 하는 interface의 메서드 이름
- `parameterType` : 어떤 자료형의 매개변수를 사용하는지
- `resultType` : SQL의 결과를 담기 위한 자료형 (클래스)
- StudentXMLMapper.java

```java
public interface StudentXMLMapper {
    List<StudentDto> selectStudentAll();
    StudentDto selectStudent(Long id);
}
```

- StudentDao.java
    - 사용법은 Annotation과 동일

```java
@Repository
@RequiredArgsConstructor
public class StudentXMLDao {
    private final SqlSessionFactory sessionFactory;

    public List<StudentDto> readStudentsAll() {
        // 사용방식은 annotation 기반과 동일하다.
        try (SqlSession session = sessionFactory.openSession()) {
            StudentXMLMapper mapper = session.getMapper(StudentXMLMapper.class);
            return mapper.selectStudentAll();
        }
    }

    public StudentDto readStudent(Long id) {
        try (SqlSession session = sessionFactory.openSession()) {
            StudentXMLMapper mapper = session.getMapper(StudentXMLMapper.class);
            return mapper.selectStudent(id);
        }
    }
}
```

- `@RequiredArgsConstructor` : 생성자를 자동으로 추가해주는 어노테이션

### Insert

- StudentMapper.xml

```java
<!-- parameterType은 일반적인 Java 클래스를 사용할 수 있다 -->
<insert id="insertStudent" parameterType="StudentDto">
    INSERT INTO student(name, email)
    VALUES (#{name}, #{email});
</insert>
```

- StudentXMLMapper.java

```java
void insertStudent(StudentDto dto);
```

- StudentDao.java

```java
public void insertStudent(StudentDto dto) {
    try (SqlSession session = sessionFactory.openSession()){
        StudentXMLMapper mapper = session.getMapper(StudentXMLMapper.class);
        mapper.insertStudent(dto);
    }
}
```

---

# JPA

---

# ORM (Object Relational Mapping)

- 객체와 객체가 상호작용
- 관계형 데이터베이스에서는 한 컬럼에 다른 객체에 대한 정보를 직접 다룰 수 없어 조회 후 별도로 Join이 필요함
- 객체지향적 관점에서 객체를 사용하여 관계형데이터베이스를 사용하는 기술
- 객체를 테이블 및 레코드에 매핑
- 생산성이 증가하고, 중복 감소, 데이터베이스 의존성 감소
- 실제 SQL을 작성하지 않고 위임하기 때문에 성능에 영향이 없지 않음

# JPA (Java Persistence API (Jakarta Persistence)

- 데이터가 어떻게 테이블에 매핑되는지 명세하기 위함
- 인터페이스와 어노테이션으로 구성
- Student.java

```java
@Entity
public class Student {}
```

- `@Entity` : 이 객체가 데이터베이스 엔티티임을 표시

## Hibernate

- JPA 명세를 바탕으로 작동하는 ORM 프레임워크
- JPA로 표현된 객체를 실제 데이터베이스에 적용 및 사용

## JPA 프로젝트 설정

- application.yaml

```yaml
jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    database-platform: org.hibernate.community.dialect.SQLiteDialect
```

- `spring.jpa.hibernate.ddl-auto` : Hibernate가 Entity 객체를 적용하기 위한 DDL을 어떻게 실행할지
- `spring.jpa.show-sql` : Hibernate에서 실제로 실행한 SQL을 콘솔에서 확인
- `spring.jpa.database-platform` : Hibernate에서 사용할 SQL Direct

## Entity 작성

- Entity 바탕으로 Repository 만들어 사용
- Student.java

```java
@Data
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int age;
    private String phone;
    private String email;
}
```

- `@Id` : 해당 필드가 PK 임을 표시
- `@GeneratedValue` : PK 자동 증가 관련 설정

## JPA Repository

- `JpaRepository` 사용
- StudentRepositor.java

```java
public interface StudentRepository extends JpaRepository<Student, Long> {}
```

- `<Student, Long>` : 사용할 Entity 클래스와 해당 클래스의 PK 타입

# CRUD

## CREATE

- 새 객체 만들고 `save()`
- StudentSevice.java

```java
public void create(
      String name,
      Integer age,
      String phone,
      String email,
      // 지도교수의 PK를 받아온다
      Long advisorId
) {
  // 주어진 정보로 새로운 Student 객체를 만든다.
  Student student = new Student();
  student.setName(name);
  student.setAge(age);
  student.setPhone(phone);
  student.setEmail(email);
  // repository의 save 메서드를 호출한다.
  studentRepository.save(student);
}
```

## READ

### 전체 조회

```java
public List<Student> readStudentAll() {
    List<Student> students = studentRepository.findAll();
//        for (Student student: students) {
//            System.out.println(student.toString());
//        }
    return students;
}
```

### 단일 조회

```java
public Student readStudent(Long id) {
    Optional<Student> optionalStudent
            = studentRepository.findById(id);
    // 실제 데이터가 있으면 해당 데이터를,
    return optionalStudent
            // 없으면 null을 반환한다.
            .orElse(null);
}
```

## Update

- Entity 조회, 수정 후 `save()`

```java
public void update(
        // 수정할 데이터의 PK가 무엇인지
        Long id,
        // 수정할 데이터
        String name,
        Integer age,
        String phone,
        String email
) {
    // 1. 업데이트할 대상 데이터를 찾고,
    Student target = readStudent(id);
    // 2. 데이터의 내용을 전달받은 내용으로 갱신하고,
    target.setName(name);
    target.setAge(age);
    target.setPhone(phone);
    target.setEmail(email);
    // 3. repository를 이용해 저장한다.
    studentRepository.save(target);
}
```

## Delete

- 삭제하고 싶은 객체 또는 PK 전달

```java
public void deleteStudent(Long id) {
    studentRepository.deleteById(id);
}
```

```java
public void deleteStudent(Long id) {
		Student targetEntity = studentRepository.findById(id).orElse(new Student());
		studentRepositroy.delete(targetEntity);
}
```

---

# Many to One & One to Many

---

# RBD에서의 관계

- 일반적으로 데이터베이스 테이블에서 관계는 외래키를 이용해 표현
- 2개 이상의 테이블은 `JOIN` 필요

## 1:1, One to One 관계

- 한 테이블의 레코드 하나가 다른 테이블의 레코드 하나와 연관된 관계
- 특정 데이터를 성능 또는 보안적 측면에서 나눌 때 사용

## N:1, Many to One 관계

- 한 테이블의 레코드 0개 이상이 다른 테이블의 레코드 하나와 연관된 관계
- 일반적인 데이터베이스의 가장 흔한 관계

## M:N, Many to Many 관계

- 한 테이블의 레코드 0개 이상이 다른 테이블의 레코드 0개 이상과 연관된 관계
- 양쪽 테이블의 PK를 Foreign Key로 가진 Join Table, Associative Table 활용

# ORM - JPA

- 하나의 테이블에는 다른 테이블의 레코드를 넣지 못함
- 테이블 데이터를 표현하기 위해 ORM이 등장
- ORM을 사용하면  테이블 간 관계를 Entity 필드로 표현 가능

## Lecture & Student

- Entity 만들기
- Student.java
    - `@ManyToOne` 어노테이션을 이용해 다른 엔티티를 첨부하여 관계 표현
    - `@JoinColumn` : Foreign Key 컬럼의 이름 변경하고 싶을 때 사용

```java
@Data
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int age;
    private String phone;
    private String email;

    // FK column -> Join Column
    @ManyToOne
    private Instructor advisor;
}
```

- Instructor.java
    - `@OneToMany` 어노테이션 사용

```java
@Data
@Entity
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;

    @OneToMany(mappedBy = "advisor")
    private List<Student> advisingStudents;
}
```

# CRUD

## CREATE

- StudentController.java

```java
@PostMapping("create")
public String create(
        @RequestParam("name")
        String name,
        @RequestParam("age")
        Integer age,
        @RequestParam("phone")
        String phone,
        @RequestParam("email")
        String email,
        @RequestParam("advisor-id")
        Long advisorId
) {
    studentService.create(name, age, phone, email, advisorId);
    return "redirect:/student";
}
```

- StudentService.java

```java
public void create(
      String name,
      Integer age,
      String phone,
      String email,
      // 지도교수의 PK를 받아온다
      Long advisorId
) {
  // 주어진 정보로 새로운 Student 객체를 만든다.
  Student student = new Student();
  student.setName(name);
  student.setAge(age);
  student.setPhone(phone);
  student.setEmail(email);
  // 지도교수님을 찾는다
  Optional<Instructor> optionalInstructor = instructorRepository.findById(advisorId);
  // 학생에 지도교수를 할당한다
  student.setAdvisor(optionalInstructor.orElse(null));
  // repository의 save 메서드를 호출한다.
  studentRepository.save(student);
}
```

## READ

- StudentController.java
    - `@ManyToOne` 속성을 그대로 활용해 연관관계 Entity 가져올 수 있음

```java
@GetMapping("{id}")
public String readOne(
        @PathVariable("id")
        Long id,
        Model model
) {
    Student student = studentRepository.findById(id).orElse(null);
		model.addAttribute("student", student);
		model.addAttribute("instructor", stduent.getAdvisor());
    return "student/read";
}
```