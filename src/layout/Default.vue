<template>
  <div
    class="bg-white dark:bg-gradient-to-r from-slate-900 to-slate-700 text-black dark:text-white font-body min-h-screen min-w-screen"
  >
    <HeaderVue />
    <slot />
  </div>
</template>

<script setup>
import HeaderVue from "../components/Header.vue";
</script>
