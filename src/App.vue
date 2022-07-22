<script lang="ts">
import { defineComponent } from "vue";
import { RouterLink, RouterView } from "vue-router";
import NavBar from './views/NavBar.vue';

export default defineComponent({
  setup() {
    // document.documentElement.classList.add('dark')
  },
  components: {
    NavBar
  }
});

</script>

<template>
  <div id="app" class="p-2">
    <div>
      <NavBar />
      <RouterView />
    </div>
  </div>

</template>

<style>

</style>
