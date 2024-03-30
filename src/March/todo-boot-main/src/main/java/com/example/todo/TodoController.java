package com.example.todo;

import com.example.todo.entity.Todo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
@RequiredArgsConstructor
public class TodoController {
    private final TodoRepository repository;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("todoList", repository.findAll());
        return "index";
    }

    @PostMapping("/todos")
    public String create(
            @RequestParam("todo")
            String todo
    ) {
        repository.save(Todo.builder()
                .todo(todo)
                .done(false)
                .build());
        return "redirect:/";
    }

    @PostMapping("/todos/{id}/done")
    public String done(
            @PathVariable("id")
            Long id
    ) {
        Todo todo = repository.findById(id).orElseThrow();
        todo.setDone(true);
        repository.save(todo);
        return "redirect:/";
    }
}
