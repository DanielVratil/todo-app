import axios from "axios";
import {TodoEntryDto} from "@/types/TodoEntryDto";


const api = axios.create({
    baseURL: "http://localhost:8080/api"
})

export function fetchTodoEntries() {
    return api.get("/todo-entry");
}

export function updateTodoEntry(id: number, dto: TodoEntryDto) {
    return api.put(`/todo-entry/${id}`, dto);
}

export function createTodoEntry(dto: TodoEntryDto) {
    return api.post(`/todo-entry`, dto);
}

export function removeTodoEntry(id: number) {
    return api.delete(`/todo-entry/${id}`);
}

