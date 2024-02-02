# OSI

- 응용 계층
- 표현 계층
- 세션 계층
- 전송 계층
- 네트워크 계층
- 데이터 링크 계층
- 물리 계층

# TCP / IP

- 응용 계층
- 전송 계층
- 인터넷 계층
- 네트워크 접근 계층

## 물리 계층

- 전기적, 기계적, 물리적 특성을 이용해 데이터를 전달하는 역할
- 케이블
    - 전압차로 0과 1을 전달
- 리피터
    - 신호의 조작 없이 더 먼곳에 신호를 전달
- 허브

## 데이터 링크 계층

- 데이터를 전송 받을 수 있는 두 기계(Node)사이의 연결 계층
- 각 기계 사이의 데이터를 안전하게 전달하는 역할
- 물리 계층의 오류를 감지, 데이터 재전송
- 데이터 전달을 위해 MAC 주소를 사용
- 랜카드 (Network Interface Controller)
- 스위치
    - MAC 주소를 바탕으로 보내줌

## 네트워크 계층

- Node와 Node는 연결되어 네트워크를 이룬다
- 네트워크 계층은 이런 Node의 네트워크 내부의 데이터 전송
- 네트워크와 네트워크 사이에서 데이터를 어떻게 전송할지 결정
- 데이터가 너무 크다면 데이터의 크기를 조정하는 역할
    - 데이터의 크기 조정; 패킷 크기 조정
- 공유기
- 라우터

### 프로토콜

### IP (Internet Protocol)

- 두 컴퓨터 사이에 연결된 노드들을 통해 데이터를 전달하는 방법을 정의하는 규약
- IP 주소를 통해 네트워크의 컴퓨터를 구분
- 데이터가 온전히 전달되는지를 보장하지는 않음

## 전송 계층

- 실제로 데이터가 사용자(양쪽 프로세스)가 의도한대로 전송되도록 해주는 계층
- 데이터가 손실이 있었다면 판단
- 손실이 발생하면 다시 보내라고 요청하는 등
- IP 주소를 통해 컴퓨터를 결정하면, Port 번호를 통해 특정 프로세스를 결정

### 프로토콜

### TCP (Transmission Control Protocol)

- 실제로 전달할 데이터를 쪼갬
- 쪼개진 데이터가 정상적인지를 판단하기 위한 정보와 함께 패킷의 단위로 보냄

## 세션 계층 & 표현 계층

### 세션 계층

- TCP를 비롯한 전송 계층 통신의 세션을 관리하는 계층
- 두 컴퓨터 연결
- 연결 제어
- 연결을 중단하는 역할

### 표현 계층

- 전달된 데이터를 사용하는 시스템의 형식에 맞게 변환하는 계층
- 하위 계층에서 데이터를 정상적으로 전송할 수 있도록 함
- 전송받은 데이터를 정상적으로 해석할 수 있도록 함

## 응용 계층

- 하위 계층을 통해서 전달된 데이터는 컴퓨터(프로세스)가 해석할 수 있는 형태로, 온전히 전달되었을 것을 기대 가능
- 전달된 데이터가 어떤 형태여야 하는지를 정의
- 어떤 식으로 데이터를 담고 있는지가 중요
- HTTP, SMTP, FTP

---

# HTTP (HyperText Transfer Protocol)

- 통신 계층 이후 전달된 데이터가 어떻게 표현되어 있는지를 나타냄
- 클라이언트와 서버가 이야기를 나눌 때 주고받는 문서의 양식
- HTML 문서와 같은 자원을 주고받을 수 있도록 하는 규약
- 클라이언트가 요청하면 서버 응답 → Client - Server Protocol

## HTTP Request

### Request Line

- HTTP Method (GET, POST…)
- URL 경로
- HTTP 버전

### Request Header

- 요청에 대한 부수적인 정보
- 어떤 방식으로 해석해야 하는지
- 어떤 형태의 응답을 기대하는지

### Request Body

- 요청을 통해 전달되고 싶은 실제 데이터
- 상황에 따라 (조회 요청 등) 생략 가능

## HTTP Request Method

- HTTP 요청이 어떤 목적을 가졌는지 표현하는 동사

### GET

- 데이터 조회를 목적으로 하는 요청 때 (READ)

### POST

- 데이터를 포함한 요청을 보낼 때 (CREATE)

### PUT

- 데이터를 포함한 요청을 보낼 때 (UPDATE)

### DELETE

- 데이터를 삭제하는 요청을 보낼 때 (DELETE)

---

## HTTP Response

### Status Line

- HTTP 버전
- 상태 코드
- 상태 코드 메시지

### Response Header

- 응답에 대한 부수적인 정보
- 어떤 방식으로 해석해야 하는지

### Response Body

- 요청을 전달하고 싶은 실제 데이터
- 응답의 내용이 포함

## HTTP Status Codes

- HTTP 요청의 처리가 어떻게 되었는지 알려주는 상태 코드
- 100 ~ 199
    - 정보 제공 목적
- 200 ~ 299
    - 요청이 성공적으로 처리되었음
    - 일반적으로 기대하는 응답
- 300 ~ 399
    - 요청의 처리를 위해 클라이언트의 추가적인 행동이 필요 (redirect)
- 400 ~ 499
    - 클라이언트가 처리가 불가능한 요청을 한 경우 (400, 401, 403, 404)
- 500 ~ 599
    - 서버에 문제가 생겨서 처리가 불가능해진 경우 (500)

---

# Java EE / Spring

## Web Server

- 최초의 웹에는 데이터에 따라 바뀌는 응답이라는 개념이 없음
- 요청에 해당하는 파일을 돌려주는 것

## Web Application

- 웹 서버 상에서 실행되며, 웹 서버가 전달한 요청에 따라 알맞은 응답을 생성해내는 응용 소프트웨어의 등장
- 브라우저에서 실행되는 응용 소프트웨어

## Web Application Server

- Web Server를 내장시켜 바로 실행할 수 있게 만듦

# Java Enterprise Edition

- Java의 `HttpServlet` 인터페이스를 구현
- Web Server에게 설정 파일을 전달함으로서 특정 요청에 따라 실행될 Java 코드 지정이 가능해짐
- 어떤 요청에 대해 어떤 Servlet을 쓸지 정의하는 `web.xml`

## `@Component` +`Scan()`

- 컴포넌트 어노테이션이 붙은 스프링 빈 객체 IoC Container에 등록

## Spring Framework / Spring MVC

### `DispatcherServlet`

- 하나의 `HTTPServlet` 에서 모든 요청을 받고, 이후 개발자가 정의한 POJO에게 요청 위임

## Spring Boot Project

---

# Serialization

- 직렬화
- 메모리 상에 저장된 데이터를 전송 가능한 형태로 바꾸는 작업
- java → yam; 직렬화
- java ← yaml; 역직렬화

# JSON (JavaScript Object Notation)

- Lightweight data-interchange format
- 데이터를 주고받는 데 활용할 수 있는 데이터 표현 방식
- JavaScript 객체를 표현하는 방식에서 문법 차용
- `{ }` 내부에 key - value  쌍을 `:` 으로 구분

```json
{
		"title": "게시글 제목"
		"content": "Lorem ipsum"
}
```

- 사람이 읽고 쓰기 쉬움
- 기계가 파싱하고 만들기 편함
- 거의 대부분의 API 통신에서 사용

# `@RequestMapping`

- 클라이언트의 요청이 들어왔을 때 `@RequestMapping` 에 전달한 설정이 일치할 경우 실행할 메서드 설정



```java
@RequestMapping(
        value = "/example/{pathVar}",
        // method: 어떤 HTTP 메서드에 대해 실행이 되는지
        method = {RequestMethod.GET, RequestMethod.POST},
				// contsumes: 어떤 데이터 형식에 대해 실행이 되는지
        // 요청의 Content-Type 헤더
        consumes = MediaType.APPLICATION_JSON_VALUE,
        // headers: 어떤 헤더가 포함되어야 실행이 되는지
        // x-likelion 헤더에 hello 추가
        headers = "x-likelion=hello",
        // param: 어떤 Query Parameter가 있어야 하는지
        params = "likelion=hello"
)
@ResponseBody
public String example(@PathVariable("pathVar") String pathVar) {
    return "done";
}
```

- `value` - 요청 URL의 PATH
- `method` - 요청의 HTTP Method. 복수 설정 가능
- `headers` - 요청 헤더
- `params` - 요청 Query Parameter
- `consumes` / `produes` - 요청 응답으로 주고받는 데이터 형식을 정하는 용도

# `@RequestHeader`

- HTTP 요청의 헤더를 확인하고 싶을 때 사용
- 단일 헤더의 값을 받아옴

```java
public String optionHeader(
		@RequestHeader("x-likelion") String likelionHeader
)
```

- 전체 헤더를 `Map`, `MultiMap`, `HttpHeader`형으로 받아옴

```java
public String allHeaders(
		@RequestHeader Map<String, String> headers
)
```

- 데이터 형식을 알면 반드시 문자열로 설정할 필요 없음
- 데이터 형식이 일치하지 않으면 400 에러 발생
- `required` 활용 가능

```java
public String optionHeader(
		@RequestHeader(
				value = "x-likelion",
				// required: 포함을 반드시 해야하는지
				required = false
		)
		String likelionHeader
)
```

# `@RequestBody` / `@ResponseBody`

- HTTP 요청과 응답의 Body를 결정
- 클라이언트가 보낸 데이터의 필드가 지정된 객체와 동일할 경우 그 데이터를 할당해 객체를 만듦
- 응답하는 객체의 필드를 해석 가능한 데이터의 형태(JSON 등)로 Body에 할당

```java
@PostMapping("articles/create")
// 메서드의 반환값을
// View로 취급하지 않고,
// 순수한 전달될 HTTP Response Body로 취급하는 어노테이션
@ResponseBody
public ArticleDto create(
        @RequestBody
        ArticleDto dto
) {
    log.info(dto.toString());
    String title = dto.getTitle();
    String content = dto.getContent();
    dto.setTitle(content);
    dto.setContent(title);
    return dto;
}
```

# `ResponseEntity`

```java
@PostMapping("student/entity")
public ResponseEntity<ResponseDto> postEntity(
        @RequestBody
        StudentDto studentDto
) {
    log.info(studentDto.toString());
    ResponseDto responseDto = new ResponseDto();
    responseDto.setMessage("등록 완료");
    HttpHeaders headers = new HttpHeaders();
    headers.add("x-likelion-response", "hello");
    return new ResponseEntity<>(
            responseDto, HttpStatus.CREATED);
}
```

- 응답 코드 설정

```java
return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
```

- 응답 헤더 추가

```java
HttpHeaders headers = new HttpHeaders();
headers.add("x-likelion-response", "hello");
```