<template>
  <Loading v-if="loggingIn" />
  <RouterView />
</template>

<script setup>
import { RouterLink, RouterView } from "vue-router";
import { onMounted, onBeforeMount, ref } from "@vue/runtime-core";
import { useInf } from "./stores/inf.js";
import api from "./api";
import { useUserStore } from "./stores/user";
import Loading from "./components/Loading.vue";

const user = useUserStore();
const inf = useInf();

onBeforeMount(() => {
  if (localStorage.getItem("refresh_token")) {
    refreshToken();
  }
});

onMounted(() => {
  setTheme();
  setLanguage();
});

const loggingIn = ref(false);

function refreshToken() {
  loggingIn.value = true;
  api
    .get("/token/refresh", {
      headers: {
        AUTHORIZATION: `Bearer ${localStorage.getItem("refresh_token")}`,
      },
    })
    .then((refreshResponse) => {
      user.setToken(
        refreshResponse.data.access_token,
        refreshResponse.data.refresh_token
      );

      api
        .get("/user/profile", {
          headers: {
            AUTHORIZATION: `Bearer ${refreshResponse.data.access_token}`,
          },
        })
        .then((response) => {
          user.setUser(response.data);
          loggingIn.value = false;
        })
        .catch((err) => {
          loggingIn.value = false;
        });
    })
    .catch((err) => {
      user.logout();
      loggingIn.value = false;
    });
}

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
  if (localStorage.theme === "dark") {
    document.documentElement.classList.add("dark");
    localStorage.setItem("theme", "dark");
  } else {
    document.documentElement.classList.remove("dark");
    localStorage.setItem("theme", "light");
  }
}
</script>
