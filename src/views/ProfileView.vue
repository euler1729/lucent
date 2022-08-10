<template>
  <DefaultLayout>
    <div class="flex flex-col items-center justify-center">
      <Btn @click="logout"> Log Out</Btn>
    </div>
  </DefaultLayout>
</template>

<script setup>
import DefaultLayout from "../layout/Default.vue";
import Btn from "../components/Btn.vue";
import { onMounted } from "vue";
import { useUserStore } from "../stores/user.js";
import { useRouter } from "vue-router";
const user = useUserStore();
const router = useRouter();

onMounted(() => {
  if (!user.loggedIn) router.push({ name: "home" });
});

function logout() {
  user.logout();
  router.push({ name: "home" });
}
</script>
