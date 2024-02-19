# RESTful

REpresentational State Transfer의 약자로, Roy Fielding이 제안한 API 제작 가이드라인.
명확한 기술이 아니며, 기술을 활용한 인터페이스가 갖춰야 하는 모습을 이야기한다.
반드시 지켜야지만 API 개발이 가능한건 아니며, 만들고자 하는 서비스에 따라
어쩔 수 없이 어기게 되는 가이드라인이 일부 존재하기도 한다. 다만 지키려고 하는 노력이 결과적으로
사용하기 편한 API를 만드는 만큼 알아볼 가치가 있다.

## URL 구성

- 게시글 생성: `POST /articles`
- 게시글 전체 조회: `GET /articles`
- 게시글 단일 조회: `GET /articles/{id}`
- 게시글 수정: `PUT /articles/{id}`
- 게시글 삭제: `DELETE /articles/{id}`

```java
@RestController
@RequestMapping("/articles")
public class ArticleController {
    private final ArticleService service;

    @PostMapping
    public ArticleDto create(@RequestBody ArticleDto dto) {
        return service.createArticle(dto);
    }

    @GetMapping
    public List<ArticleDto> readAll() {
        return service.readArticleAll();
    }

    @GetMapping("/{id}")
    public ArticleDto read(@PathVariable("id") Long id) {
        return service.readArticle(id);
    }

    @PutMapping("/{id}")
    public ArticleDto update(@PathVariable("id") Long id, @RequestBody ArticleDto dto) {
        return service.updateArticle(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        service.deleteArticle(id);
    }
}
```

## 1:N 자원 표현하기

- 게시글에 댓글 추가: `POST /articles/{articleId}/comments`
- 게시글 댓글 전체 조회: `GET /articles/{articleId}/comments`
- 게시글 댓글 수정: `PUT /articles/{articleId}/comments/{commentId}`
- 게시글 댓글 삭제: `DELETE /articles/{articleId}/comments/{commentId}`

```java
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/articles/{articleId}/comments")
public class CommentController {
    private final CommentService service;

    @PostMapping
    public CommentDto create(
            @PathVariable("articleId") Long articleId,
            @RequestBody CommentDto dto
    ) {
        return service.createComment(articleId, dto);
    }

    @GetMapping
    public List<CommentDto> readAll(
            @PathVariable("articleId") Long articleId
    ) {
        return service.readCommentAll(articleId);
    }

    @PutMapping("/{commentId}")
    public CommentDto update(
            @PathVariable("articleId") Long articleId,
            @PathVariable("commentId") Long commentId,
            @RequestBody CommentDto dto
    ) {
        return service.updateComment(articleId, commentId, dto);
    }

    @DeleteMapping("/{commentId}")
    public void delete(
            @PathVariable("articleId") Long articleId,
            @PathVariable("commentId") Long commentId
    ) {
        service.deleteComment(articleId, commentId);
    }
}
```
