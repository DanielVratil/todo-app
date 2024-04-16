package cz.vratil.todo_app_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.vratil.todo_app_backend.dto.TodoEntryDto;
import cz.vratil.todo_app_backend.enums.TodoEntryStatus;
import cz.vratil.todo_app_backend.model.TodoEntry;
import cz.vratil.todo_app_backend.service.TodoEntryService;
import cz.vratil.todo_app_backend.utils.Utils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class TodoEntryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoEntryService todoEntryService;

    @Test
    void listTodoEntries() throws Exception {
        Date createdDate = new Date();
        Date doneDate = new Date();


        List<TodoEntry> entries = new ArrayList<>() {
            {
                add(new TodoEntry().setLabel("TODO1").setStatus(TodoEntryStatus.TODO).setCreatedAt(createdDate).setDoneAt(null));
                add(new TodoEntry().setLabel("TODO2").setStatus(TodoEntryStatus.TODO).setCreatedAt(createdDate).setDoneAt(null));
                add(new TodoEntry().setLabel("TODO3").setStatus(TodoEntryStatus.DONE).setCreatedAt(createdDate).setDoneAt(doneDate));
            }
        };


        when(todoEntryService.list()).thenReturn(entries);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat(Utils.DATE_TIME_FORMAT));
        this.mockMvc.perform(get("/api/todo-entry"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(entries)));
    }

    @Test
    void createTodoEntries() throws Exception {
        TodoEntryDto dto = new TodoEntryDto();
        dto.label = "New task";
        dto.status = TodoEntryStatus.TODO;

        Date createdDate = new Date();
        TodoEntry todoEntry = new TodoEntry().setLabel("New task").setStatus(TodoEntryStatus.TODO).setCreatedAt(createdDate);

        when(todoEntryService.create(any())).thenReturn(todoEntry);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat(Utils.DATE_TIME_FORMAT));

        this.mockMvc.perform(post("/api/todo-entry").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(todoEntry)));
    }

    @Test
    void updateTodoEntries() throws Exception {
        Long id = 3L;
        TodoEntryDto dto = new TodoEntryDto();
        dto.label = "New task updated";
        dto.status = TodoEntryStatus.DONE;

        Date createdDate = new Date();
        Date doneDate = new Date();
        TodoEntry todoEntry = new TodoEntry().setId(id).setLabel("New task updated").setStatus(TodoEntryStatus.DONE).setCreatedAt(createdDate).setDoneAt(doneDate);

        when(todoEntryService.update(any(), any())).thenReturn(todoEntry);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat(Utils.DATE_TIME_FORMAT));

        this.mockMvc.perform(put("/api/todo-entry/" + id).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(todoEntry)));
    }

}
