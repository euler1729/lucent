<template>
  <DefaultLayout>
    <!-- Hero -->
    <div class="max-w-screen overflow-hidden" style="height: 48rem">
      <div
        class="flex flex-row items-center justify-evenly"
        style="
          min-height: 48rem;
          max-height: 48rem;
          background-image: url(https://cdn.dribbble.com/users/1012997/screenshots/17883184/media/2839a19e60056688c5ee4cb470a7b5f0.png);
          background-size: contain;
          background-repeat: no-repeat;
          background-position: center;
          overflow-x: hidden;
          transform: scale(1.4);
        "
      >
        <div></div>
        <div></div>
        <div
          class="bg-darkblue/30 px-4 py-4 text-xl md:text-4xl text-white font-hero font-semibold border-white/90 border-4 rounded-lg pointer-events-none backdrop-blur-sm"
        >
          <span>Make the world a better place</span>
        </div>
        <div></div>
      </div>
    </div>
    <!-- My Organization -->
    <div class="flex flex-row items-center justify-between px-10">
      <div class="text-xl font-semibold text-darkblue">My Organizations</div>
      <button
        type="button"
        class="text-white bg-darkblue font-semibold font-inter rounded-lg px-5 py-1 text-center mr-2 mb-2"
      >
        Show all
      </button>
    </div>

    <div
      class="flex flex-row items-center justify-evenly md:justify-start flex-wrap"
    >
      <div v-for="org in orgs" :key="org.id" class="mt-10 mx-6">
        <OrgThumb
          @click="navOrg(org.id)"
          v-if="org.id != 2"
          :organization="org"
        />
      </div>
    </div>

    <!-- Top organization -->
    <div class="flex flex-row items-center justify-between px-10 mt-10">
      <div class="text-xl font-semibold text-darkblue">Top Organizations</div>
      <button
        type="button"
        class="text-white bg-darkblue font-semibold font-inter rounded-lg px-5 py-1 text-center mr-2 mb-2"
      >
        Show all
      </button>
    </div>

    <div
      class="flex flex-row items-center justify-evenly md:justify-start flex-wrap self-center"
    >
      <div v-for="org in orgs" :key="org.id" class="mt-10 mx-6">
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
