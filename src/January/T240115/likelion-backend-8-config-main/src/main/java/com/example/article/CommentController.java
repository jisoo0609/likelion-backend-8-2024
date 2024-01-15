package com.example.article;

import com.example.article.dto.CommentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("article/{articleId}/comment")
@RequiredArgsConstructor
public class CommentController {
    private final ArticleService articleService;
    private final CommentService commentService;

    @PostMapping
    public String create(
            @PathVariable("articleId")
            Long articleId,
            @RequestParam("writer")
            String writer,
            @RequestParam("content")
            String content
    ) {
        commentService.create(articleId, new CommentDto(content, writer));
        return String.format("redirect:/article/%d", articleId);
    }

    @GetMapping("{commentId}/edit")
    public String edit(
            @PathVariable("articleId")
            Long articleId,
            @PathVariable("commentId")
            Long commentId,
            Model model
    ) {
        model.addAttribute("article", articleService.read(articleId));
        model.addAttribute("comment", commentService.read(commentId));
        return "comment/edit";
    }

    @PostMapping("{commentId}/update")
    public String update(
            @PathVariable("articleId")
            Long articleId,
            @PathVariable("commentId")
            Long commentId,
            @RequestParam("content")
            String content
    ) {
        commentService.update(commentId, new CommentDto(content));
        return String.format("redirect:/article/%d", articleId);
    }

    @PostMapping("{commentId}/delete")
    public String delete(
            @PathVariable("articleId")
            Long articleId,
            @PathVariable("commentId")
            Long commentId
    ) {
        commentService.delete(commentId);
        return String.format("redirect:/article/%d", articleId);
    }
}
