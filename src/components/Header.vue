<template>
  <EmailVerificationAlert v-if="user.loggedIn && user.verified == false" />
  <div class="flex flex-row items-center justify-between px-4 bg-bg py-4">
    <div
      @click="gotoHome"
      class="cursor-pointer font-display text-4xl md:text-5xl"
    >
      Lucent
    </div>
    <div class="flex flex-row items-center">
      <!-- <div
        @click="toggleLanguage"
        class="cursor-pointer mx-2 text-xl md:text-2xl"
      >
        <font-awesome-icon icon="language" />
      </div>
      <div @click="toggleTheme" class="cursor-pointer mx-2 text-xl md:text-2xl">
        <font-awesome-icon icon="moon" />
      </div> -->
      <!-- <div>
        <Btn @click="login">
          <span v-if="user.loggedIn">
            {{ user.name }}
          </span>
          <span v-else>
            {{ btnLabel[inf.lang] }}
          </span></Btn
        >
      </div> -->

      <!-- Menu -->
    </div>
    <div class="dropdown">
      <div>
        <Btn @click="login">
          <span v-if="user.loggedIn">
            {{ user.name }}
          </span>
          <span v-else>
            {{ btnLabel[inf.lang] }}
          </span></Btn
        >
      </div>
      <div v-if="user.loggedIn" class="dropdown-content">
        <div>
          <div>
            <Btn @click="login"> My Profile </Btn>
          </div>
        </div>
        <div v-if="user.role === user.managerRole">
          <Btn @click="navSettings">
            <span class="md:inline">{{ orgSettingsLabel[inf.lang] }}</span>
          </Btn>
        </div>
        <div v-if="user.role === user.adminRole">
          <Btn @click="navAdminDashboard">
            <span class="md:inline">Dashboard </span>
          </Btn>
        </div>
        <!-- <div>
          <Btn @click="login"> Donations </Btn>
        </div> -->

        <div>
          <Btn @click="user.logout"> Log Out</Btn>
        </div>
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
import EmailVerificationAlert from "./EmailVerificationAlert.vue";
import { useUserStore } from "../stores/user.js";
import { useRouter } from "vue-router";
import { Switch, SwitchGroup, SwitchLabel } from "@headlessui/vue";
import { Menu, MenuButton, MenuItems, MenuItem } from "@headlessui/vue";

const user = useUserStore();
const router = useRouter();
const inf = useInf();
const loginModal = ref(false);
const loginModalKey = ref(0);

const btnLabel = ref({
  bn: "লগ ইন",
  en: "Log in",
});

const orgSettingsLabel = ref({
  bn: "সেটিংস",
  en: "Settings",
});

function login() {
  if (!user.loggedIn) {
    loginModalKey.value = Math.random();
    loginModal.value = true;
  } else {
    router.push({ name: "profile" });
  }
}

function navSettings() {
  router.push({ name: "orgsettings" });
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

function navAdminDashboard() {
  router.push({ name: "adminDashboard" });
}
</script>

<style scoped>
.dropdown {
  position: relative;
  display: inline-block;
}

.dropdown-content {
  display: none;
  position: absolute;
  min-width: 170px;
  z-index: 1;
}

.dropdown:hover .dropdown-content {
  display: block;
}
</style>
