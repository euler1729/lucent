<template>
  <DefaultLayout>
    <div class="px-10 py-10 grid gri grid-cols-4">
      <div v-for="org in orgs" :key="org.id">
        <OrgThumb :organization="org" />
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

const orgs = ref([]);

onMounted(() => {
  loadOrganizations();
});

function loadOrganizations() {
  api.get("/org/published?page=0&size=3&sortBy=name").then((response) => {
    orgs.value = response.data;
    console.log(response.data);
  });
}
</script>
