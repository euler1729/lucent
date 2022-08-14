<template>
  <div
    class="bg-bg dark:bg-gradient-to-r from-slate-900 to-slate-700 text-black dark:text-white min-h-screen min-w-screen pb-20"
  >
    <HeaderVue />
    <slot />
  </div>
</template>

<script setup>
import HeaderVue from "../components/Header.vue";
</script>
