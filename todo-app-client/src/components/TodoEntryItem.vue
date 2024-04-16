<script setup lang="ts">
import {TodoEntry} from "@/types/TodoEntry";
import {PropType, ref, defineProps} from "vue";
import {useMutation, useQueryClient} from "@tanstack/vue-query";
import {removeTodoEntry, updateTodoEntry} from "@/service/api";
import {TodoEntryDto} from "@/types/TodoEntryDto";

const queryClient = useQueryClient();
const updateMutation = useMutation({
  mutationFn: (variables: {
    id: number,
    dto: TodoEntryDto,
  }) => updateTodoEntry(variables.id, variables.dto),
})

const removeMutation = useMutation({
  mutationFn: removeTodoEntry,
  onSuccess() {
    queryClient.invalidateQueries({
      queryKey: ['todo-entry']
    })
  }
})

const props = defineProps({
  todo: {
    type: Object as PropType<TodoEntry>,
    required: true,
  },
})

const checked = ref(props.todo.status === "DONE");
const currentLabel = ref<string | null>(props.todo.label);

const onCheck = () => {
  updateMutation.mutate({
    id: props.todo.id,
    dto: {
      status: checked.value ? "DONE" : "TODO",
      label: props.todo.label,
    }
  }, {
    onSuccess(){
      queryClient.invalidateQueries({
        queryKey: ['todo-entry']
      })
    }
  })
}

const onConfirm = () => {
  if (currentLabel.value === null) {
    return;
  }
  updateMutation.mutate({
    id: props.todo.id,
    dto: {
      status: props.todo.status,
      label: currentLabel.value,
    }
  }, {
    onSuccess() {
      queryClient.invalidateQueries({
        queryKey: ['todo-entry']
      })
    }
  })
}

const onRemove = () => {
  removeMutation.mutate(props.todo.id)
}

</script>

<template>

  <div class="todo-entry-item">
    <div class="todo-entry-main">
      <input type="checkbox" v-model="checked" @change="onCheck"/>
      <input v-if="checked" class="checked-label" type="text" v-model="currentLabel" @change="onConfirm">
      <input v-if="!checked" type="text" v-model="currentLabel" @change="onConfirm">
      <button class="danger" @click="onRemove">Remove</button>
    </div>
    <div class="todo-entry-date">
      <span v-if="todo.createdAt != null">Created at: {{new Date(todo.createdAt).toLocaleString()}}</span>
      <span v-if="todo.doneAt != null">Done at: {{new Date(todo.doneAt).toLocaleString()}}</span>
    </div>
  </div>


</template>

<style scoped>

.checked-label {
  text-decoration-line: line-through;
}

.todo-entry-item {
  display: flex;
  flex-direction: row;
  gap: 1em;
}

input[type="text"] {
  border: none;
  border-bottom: 1px black solid;
  font-size: 16px;
}

input[type="text"]:focus-visible {
  border: none;
  outline: none;
}

input[type="text"]:focus {
  border: none;
  border-bottom: 2px lightblue solid;
}

.todo-entry-main {
  display: flex;
  flex-direction: row;
  gap: 1em;
}

.todo-entry-date {
  font-size: 14px;
  margin-left: 2em;
  display: flex;
  flex-direction: column;
  gap: 0.5em;
  justify-content: center;
}

</style>