package com.webapp.backend.controllers;

import java.util.List;

import com.webapp.backend.mappers.TodoMapper;
import com.webapp.backend.models.Todo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
    private TodoMapper todoMapper;

    public TodoController(TodoMapper todoMapper) {
        this.todoMapper = todoMapper;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Todo>> GetAll() {
        return new ResponseEntity<>(todoMapper.GetAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> Get(@PathVariable long id) {
        Todo result = todoMapper.GetTodo(id);
        if (result == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @PostMapping(path = "")
    public ResponseEntity<Integer> Add(@RequestBody Todo newTodo) {
        Integer insertedRows = todoMapper.AddTodo(newTodo);
        if (insertedRows == 1) {
            return new ResponseEntity<>(newTodo.getIdTodo(), HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Integer> Update(@PathVariable long id, @RequestBody Todo newTodo) {
        Todo targetTodo = todoMapper.GetTodo(id);
        if (targetTodo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Integer affectedRows = todoMapper.UpdateTodo(newTodo);
        if (affectedRows == 1) {
            return new ResponseEntity<>(affectedRows, HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}