<template>
  <div
    class="flex items-center py-2 px-4 bg-emerald-200 dark:bg-emerald-700 text-sm"
  >
    <div class="mr-2 text-lg">Your phone is not verified.</div>
    <button
      type="button"
      class="font-bold text-lg bg-darkblue p-2 rounded text-white"
      @click="handleVerify"
    >
      {{ label }}
    </button>
  </div>

  <VerifyPhone :isOpen="isOpen" :key="openKey" />
</template>

<script setup>
import Btn from "./Btn.vue";
import { ref } from "vue";
import api from "../api.js";
import VerifyPhone from "./VerifyPhone.vue";
import { useUserStore } from "../stores/user.js";
const user = useUserStore();

const label = ref("Verify your account now");
const isOpen = ref(false);
const openKey = ref(0);

function handleVerify() {
  isOpen.value = true;
  openKey.value = Math.random();
}
</script>
