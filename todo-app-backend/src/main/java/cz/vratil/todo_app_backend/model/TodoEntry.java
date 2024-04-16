package cz.vratil.todo_app_backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import cz.vratil.todo_app_backend.enums.TodoEntryStatus;
import cz.vratil.todo_app_backend.utils.Utils;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "todo_entry")
public class TodoEntry {

    @Id
    @GeneratedValue
    private Long id;

    private String label;
    private TodoEntryStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Utils.DATE_TIME_FORMAT)
    private Date createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Utils.DATE_TIME_FORMAT)
    private Date doneAt;

    public TodoEntry() {
    }

    public Long getId() {
        return id;
    }

    public TodoEntry setId(Long id) {
        this.id = id;
        return this;
    }

    public String getLabel() {
        return label;
    }

    public TodoEntry setLabel(String label) {
        this.label = label;
        return this;
    }

    public TodoEntryStatus getStatus() {
        return status;
    }

    public TodoEntry setStatus(TodoEntryStatus status) {
        if (this.status != TodoEntryStatus.DONE && status == TodoEntryStatus.DONE) {
            this.setDoneAt(new Date());
        } else if (this.status != TodoEntryStatus.DONE) {
            this.setDoneAt(null);
        }
        this.status = status;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public TodoEntry setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Date getDoneAt() {
        return doneAt;
    }

    public TodoEntry setDoneAt(Date doneAt) {
        this.doneAt = doneAt;
        return this;
    }
}
