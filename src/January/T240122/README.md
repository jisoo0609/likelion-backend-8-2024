# Query Parameter

```java
http://example.com/path?query=keyword&limit=20
```

- 요구하는 자원에 대한 동적인 조건을 전달하는 용도로 사용
- 페이지, 검색 등
- ?뒤에 `key=value` 형식으로 활용, 각 인자는 `&` 로 구분

# `@RequestParam`

- Spring의 RequestMapping 메서드에서 `@RequestParam` 을 이용해 Query의 인자를 받아올 수 있다

```java
// GET /query-example?query=keyword&limit=20 HTTP/1.1
    @GetMapping("/query-example")
    public String queryParams(
            // key전달
            @RequestParam("query")
            String query,
            // 받을 자료형 선택 가능
            // 만약 변환 불가한 자료형일 경우 Bad Request (400)
            @RequestParam("limit")
            Integer limit,
            // 반드시 포함해야 하는지 아닌지를 required로 정의 가능
            @RequestParam(value = "notreq", required = false)
            String notRequired,
            // 기본값 설정을 원한다면 defaultValue
            @RequestParam(value = "default", defaultValue = "hello")
            String defaultVal
    ) {
        log.info("query: "+ query);
        log.info("limit: "+ limit);
        log.info("notRequired: "+ notRequired);
        log.info("default: "+ defaultVal);

        return "done";
    }
```

- `defalutValue` : 기본값 설정
- `required` : 필수 포함 여부

# Pagination

- 조회할 데이터의 개수가 많을 때, 조회되는 데이터의 개수를 한정시켜 페이지 단위로 나누는 기법
- 조회할 데이터의 개수가 줄어들기 때문에 성능 향상 가능
- 사용자가 데이터를 확인하는 과정에서 확인해야 하는 데이터를 줄여 UX 향상

## JPA와 Pagination

- Repository

```java
// ID가 큰 순서대로 최상위 20개
List<Article> findTop20ByOrderByIdDesc();
```

### Pageable

- `PagingAndSortingRepository` 의 메서드에 전달 가능한 페이지 구분 용도

### JPA Query Method

```java
// 페이지 단위를 구분하기 힘들다
// 마지막으로 확인한 게시글의 ID를 바탕으로 조회해야 한다는 단점
public List<ArticleDto> readTop20() {
    List<ArticleDto> articleDtoList = new ArrayList<>();
    List<Article> articleList = repository.findTop20ByOrderByIdDesc();
    for (Article entity : articleList) {
        articleDtoList.add(ArticleDto.fromEntity(entity));
    }
    return articleDtoList;
}
```

- Pageable 사용해서, List로 반환

```java
public List<ArticleDto> readArticlePagedList() {
    // PagingAndSortingRepository의 findAll의 인자로 전달함으로써
    // 조회하고 싶은 페이지와, 각 페이지 별 갯수를 조절해서
    // 조회하는 것을 도와주는 객체
    Pageable pageable = PageRequest.of(0, 20);

    // Page<Article>: pageable을 전달해서 받은 결과를 정리해둔 객체
    Page<Article> articlePage = repository.findAll(pageable);
    // 결과 반환 준비
    List<ArticleDto> articleDtoList = new ArrayList<>();
    for (Article entity : articlePage) {
        articleDtoList.add(ArticleDto.fromEntity(entity));
    }
    return articleDtoList;
}
```

- Pageable을 사용해서 Page<Entity>를 Page<Dto>로 변환 후 모든 정보 활용

```java
public Page<ArticleDto> readArticlePaged(
        Integer pageNum,
        Integer pageSize
) {
    Pageable pageable = PageRequest.of(
            pageNum, pageSize, Sort.by("id").descending());
    Page<Article> articlePage = repository.findAll(pageable);
    // map: Page의 각 데이터(Entity)를 인자로 특정 메서드를 실행한 후
    // 해당 메서드 실행 결과를 모아서 새로운 Page 객체를
    // 만약 반환형이 바뀐다면 타입을 바꿔서 반환한다.
    Page<ArticleDto> articleDtoPage
//              = articlePage.map(entity -> ArticleDto.fromEntity(entity));
            = articlePage.map(ArticleDto::fromEntity);
    return articleDtoPage;
}
```

---

# File Handling

# 정적 파일 (Static File)

- 사용자에게 변환 없이 전달되는 파일
    - CSS
    - 이미지, 영상 파일
    - 몇몇 HTML 등
- Spring Boot  기본 설정으로 전달 가능

```java
spring:
  mvc:
    static-path-pattern: /static/**
```

- `static-path-pattern` : 어떤 경로에 대한 요청의 응답으로 정적 파일 응답을 할지 결정하는 설정

```java
web:
    resources:
      static-locations: file:media/,classpath:/static
```

- `static-locations` : 어떤 폴더의 파일을 정적 응답으로 전달할지를 설정

# Multipart

## form

- HTML에서 JS없이 데이터 보낼 때 사용
- 내부에 `input` 요소를 이용해 전달할 데이터 정의
- `enctype` 속성으로 데이터 인코딩 방식 적용 가능

### `multipart/form-data`

- 요청을 여러 부분으로 구분해서 전송하는 형태
- 텍스트와 파일이 혼합된 요청이라는 의미
- Form을 이용해 파일을 보낼 경우 선택해야 하는 방식

```java
<h1>Hello Static!</h1>
<img src="http://localhost:8080/assets/images/whale.png" alt="">
<form enctype="multipart/form-data">
    <input type="file">
</form>
```

## MultipartFile 받기

- `consumes = MediaType.*MULTIPART_FORM_DATA_VALUE` :* multpart/form-data 요청 기다림
- `@RequestParam` : `MultipartFile` 인자 받을 수 있음4

```java
@PostMapping(
        value = "/multipart",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
)
public String multipart(
        @RequestParam("path")
        String name,
        // 받아주는 자료형을 MultipartFile
        @RequestParam("file")
        MultipartFile multipartFile
) throws IOException {
    log.info(multipartFile.getOriginalFilename());
    // 파일을 저장할 경로 + 파일명 지정
    Path downloadPath = Path.of("media/" + multipartFile.getOriginalFilename());
    // 저장한다.
    multipartFile.transferTo(downloadPath);
    // 저장할 파일 이름
    File file = new File("./media/" + multipartFile.getOriginalFilename());
    try (OutputStream outputStream = new FileOutputStream(file)){
        // byte[] 형태로 파일을 받는다.
        byte[] fileBytes = multipartFile.getBytes();
        outputStream.write(fileBytes);
    }

    return "http://localhost:8080/static/" + multipartFile.getOriginalFilename();
}
```