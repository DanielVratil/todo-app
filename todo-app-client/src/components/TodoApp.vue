<script setup lang="ts">

import {useMutation, useQuery, useQueryClient} from "@tanstack/vue-query";
import {createTodoEntry, fetchTodoEntries} from "@/service/api";
import TodoEntryItem from "@/components/TodoEntryItem.vue";

const queryClient = useQueryClient();
const {isSuccess, isLoading, isError, data, error} = useQuery({
  queryKey: ['todo-entry'],
  queryFn: fetchTodoEntries,
  select(data) {
    return data.data;
  }
})

const createTodoMutation = useMutation({
  mutationFn: createTodoEntry,
});

const submit = (e: SubmitEvent) => {
  e.preventDefault();
  createTodoMutation.mutate({
    label: '',
    status: 'TODO',
  }, {
    onSuccess() {
      queryClient.invalidateQueries({
        queryKey: ['todo-entry']
      })
    }
  })
}

</script>

<template>
  <div class="main">
    <div v-if="isLoading">
      Loading todo entries...
    </div>

    <div v-if="isError">
      Error: {{ error.message }}
    </div>

    <div>
      <button class="info" @click="submit">Add TODO</button>
    </div>

    <div v-if="isSuccess">
      <div v-if="data.length === 0">
        There are no TODOs, start by adding your first one.
      </div>

      <div v-if="data.length > 0" class="todo-entry-list">
        <div>
          You have {{data.length}} TODOS,
          <strong>{{data.filter(todo => todo.status === 'DONE').length}} completed</strong> and
          <strong>{{data.filter(todo => todo.status === 'TODO').length}} incomplete</strong>.
        </div>
        <div v-bind:key="todo.id" v-for="(todo) in data">
          <TodoEntryItem :todo="todo"/>
        </div>
      </div>
    </div>
  </div>
</template>


<style scoped>
.todo-entry-list {
  display: flex;
  flex-direction: column;
  gap: 1em;
}

.main {
  display: flex;
  flex-direction: column;
  gap: 2em;
}
</style>

