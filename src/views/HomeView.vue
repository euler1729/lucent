<template>
  <DefaultLayout>
    <div
      class="mx-10 py-10 flex flex-row flex-wrap items-center justify-center"
    >
      <div v-for="org in orgs" :key="org.id" class="mx-4 my-2">
        <OrgThumb @click="navOrg(org.id)" :organization="org" />
      </div>
    </div>
  </DefaultLayout>
</template>

<script setup>
import DefaultLayout from "../layout/Default.vue";
import OrgThumb from "../components/OrgThumb.vue";
import api from "../api";

import { ref } from "vue";
import { onMounted } from "@vue/runtime-core";
import { useRouter } from "vue-router";

const router = useRouter();
const orgs = ref([]);

onMounted(() => {
  loadOrganizations();
});

function navOrg(id) {
  router.push(`/org/${id}`);
}

function loadOrganizations() {
  api.get("/org/published?page=0&size=3&sortBy=name").then((response) => {
    orgs.value = response.data;
  });
}
</script>
