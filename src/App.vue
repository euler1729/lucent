<template>
  <RouterView />
</template>

<script setup>
import { RouterLink, RouterView } from "vue-router";
import { onMounted, onBeforeMount } from "@vue/runtime-core";
import { useInf } from "./stores/inf.js";

const inf = useInf();

onMounted(() => {
  setTheme();
  setLanguage();
});

function setLanguage() {
  if (localStorage.lang === "en" || !("lang" in localStorage)) {
    inf.setLang("en");
    localStorage.setItem("lang", "en");
  } else {
    inf.setLang("bn");
    localStorage.setItem("lang", "bn");
  }
}

function setTheme() {
  if (
    localStorage.theme === "dark" ||
    (!("theme" in localStorage) &&
      window.matchMedia("(prefers-color-scheme: dark)").matches)
  ) {
    document.documentElement.classList.add("dark");
    localStorage.setItem("theme", "dark");
  } else {
    document.documentElement.classList.remove("dark");
    localStorage.setItem("theme", "light");
  }
}
</script>
