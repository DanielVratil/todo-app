package cz.vratil.todo_app_backend.dto;

import cz.vratil.todo_app_backend.enums.TodoEntryStatus;
import jakarta.validation.constraints.NotNull;

public class TodoEntryDto {

    public String label;

    @NotNull(message = "Status cannot be empty.")
    public TodoEntryStatus status;
}
