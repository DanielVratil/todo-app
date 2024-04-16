package cz.vratil.todo_app_backend.service;

import cz.vratil.todo_app_backend.dto.TodoEntryDto;
import cz.vratil.todo_app_backend.model.TodoEntry;
import cz.vratil.todo_app_backend.repository.TodoEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TodoEntryService {

    @Autowired
    private TodoEntryRepository todoEntryRepository;

    public List<TodoEntry> list() {
        return todoEntryRepository.findAll();
    }

    public TodoEntry create(TodoEntryDto dto) {
        TodoEntry todoEntry =
                new TodoEntry()
                        .setLabel(dto.label)
                        .setStatus(dto.status)
                        .setCreatedAt(new Date());

        todoEntry = todoEntryRepository.save(todoEntry);
        return todoEntry;
    }

    public TodoEntry update(Long id, TodoEntryDto todoEntryDto) throws NoSuchElementException {
        TodoEntry todoEntry = todoEntryRepository.findById(id).orElseThrow();

        todoEntry
                .setLabel(todoEntryDto.label)
                .setStatus(todoEntryDto.status);

        todoEntryRepository.save(todoEntry);
        return todoEntry;
    }

    public void delete(Long id) throws NoSuchElementException {
        TodoEntry todoEntry = todoEntryRepository.findById(id).orElseThrow();
        todoEntryRepository.delete(todoEntry);
    }
}
