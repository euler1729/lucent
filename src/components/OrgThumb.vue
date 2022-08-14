<template>
  <div
    class="font-body flex flex-col md:flex-row bg-white shadow-xl rounded-lg px-4 py-4"
  >
    <!-- Profile Picture -->
    <div class="w-52 h-52 rounded-lg self-center my-4 md:my-0">
      <img class="rounded-lg" :src="organization.profilePicURL" />
    </div>

    <!-- Information -->
    <div class="flex flex-col items-center justify-between py-2 px-10">
      <div class="text-3xl font-bold my-4 md:my-0">{{ organization.name }}</div>
      <div class="font-semibold text-lg w-64 my-4 md:my-0">
        {{ getShortDesc(organization.description) }}
      </div>
      <Btn class="my-4 md:my-0">Donate</Btn>
    </div>
  </div>
</template>

<script setup>
import Btn from "../components/Btn.vue";
import { ref } from "vue";
import { useInf } from "../stores/inf.js";

const inf = useInf();

defineProps({
  organization: Object,
});

const detailsBtnLabel = ref({
  bn: "বিস্তারিত",
  en: "Details",
});

function getShortDesc(desc) {
  let shortDesc = desc.substring(0, 50);
  if (desc.length > 50) shortDesc += "...";
  return shortDesc;
}
</script>
