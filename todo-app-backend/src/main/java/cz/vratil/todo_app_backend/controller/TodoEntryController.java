package cz.vratil.todo_app_backend.controller;

import cz.vratil.todo_app_backend.dto.TodoEntryDto;
import cz.vratil.todo_app_backend.model.TodoEntry;
import cz.vratil.todo_app_backend.service.TodoEntryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@CrossOrigin(origins = {"http://localhost:8081"})
@RestController
@RequestMapping("/api/todo-entry")
public class TodoEntryController {

    @Autowired
    private TodoEntryService todoEntryService;


    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<TodoEntry> list() {
        return todoEntryService.list();
    }

    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.CREATED)
    public TodoEntry create(@RequestBody @Valid TodoEntryDto todoEntryDto) {
        return todoEntryService.create(todoEntryDto);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TodoEntry> update(@PathVariable("id") Long id, @RequestBody @Valid TodoEntryDto todoEntryDto) {
        try {
            TodoEntry todoEntry = todoEntryService.update(id, todoEntryDto);
            return ResponseEntity.ok(todoEntry);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity delete(@PathVariable("id") Long id) {
        try {
            todoEntryService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
