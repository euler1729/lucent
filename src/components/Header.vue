<template>
  <div class="flex flex-row items-center justify-between px-4 py-2">
    <div
      @click="gotoHome"
      class="cursor-pointer font-display italic underline font-semibold text-xl md:text-2xl"
    >
      Lucent
    </div>
    <div class="flex flex-row items-center">
      <div
        @click="toggleLanguage"
        class="cursor-pointer mx-2 text-xl md:text-2xl"
      >
        <font-awesome-icon icon="language" />
      </div>
      <div @click="toggleTheme" class="cursor-pointer mx-2 text-xl md:text-2xl">
        <font-awesome-icon icon="moon" />
      </div>
      <div class="mx-2">
        <Btn @click="login"
          ><font-awesome-icon icon="user" />
          <span v-if="user.loggedIn" class="hidden sm:inline ml-2 font-bangla">
            {{ user.name }}
          </span>
          <span v-else class="hidden sm:inline ml-2 font-bangla">
            {{ btnLabel[inf.lang] }}
          </span></Btn
        >
      </div>
    </div>
  </div>
  <Login
    :isOpen="loginModal"
    @onsuccess="afterLoginSuccess"
    :key="loginModalKey"
  />
</template>

<script setup>
import { ref } from "vue";
import Btn from "./Btn.vue";
import { useInf } from "../stores/inf.js";
import Login from "./Login.vue";
import { useUserStore } from "../stores/user.js";
import { useRouter } from "vue-router";
const user = useUserStore();
const router = useRouter();
const inf = useInf();
const loginModal = ref(false);
const loginModalKey = ref(0);

const btnLabel = ref({
  bn: "লগ ইন",
  en: "Log in",
});

function login() {
  if (!user.loggedIn) {
    loginModalKey.value = Math.random();
    loginModal.value = true;
  } else {
    router.push({ name: "profile" });
  }
}

function afterLoginSuccess() {
  console.log("Successfuly Logged In");
}

function gotoHome() {
  router.push({ name: "home" });
}
function toggleLanguage() {
  if (localStorage.lang === "en" || !("lang" in localStorage)) {
    inf.setLang("bn");
    localStorage.setItem("lang", "bn");
  } else {
    inf.setLang("en");
    localStorage.setItem("lang", "en");
  }
}

function toggleTheme() {
  if (
    localStorage.theme === "dark" ||
    (!("theme" in localStorage) &&
      window.matchMedia("(prefers-color-scheme: dark)").matches)
  ) {
    document.documentElement.classList.remove("dark");
    localStorage.theme = "light";
  } else {
    document.documentElement.classList.add("dark");
    localStorage.theme = "dark";
  }
}
</script>
