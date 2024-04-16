<template>
  <h1>
    TODO App
  </h1>
  <TodoApp />
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component';
import TodoApp from "@/components/TodoApp.vue";

@Options({
  components: {
    TodoApp,
  },
})
export default class App extends Vue {}
</script>

<style>
button.info {
  background-color: #0d6efd;
  color: white;
}

button.danger {
  background-color: #dc3545;
  color: white;
}

button {
  border-radius: 8px;
  padding: 8px;
  border: none;
}
</style>
