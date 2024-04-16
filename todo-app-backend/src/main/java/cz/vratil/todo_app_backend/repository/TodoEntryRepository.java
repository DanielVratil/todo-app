package cz.vratil.todo_app_backend.repository;

import cz.vratil.todo_app_backend.model.TodoEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoEntryRepository extends JpaRepository<TodoEntry, Long> {}
