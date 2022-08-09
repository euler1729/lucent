<template>
  <div
    class="w-64 h-48 shadow-lg md:shadow-2xl shadow-blue-500/20 md:shadow-blue-500/20 rounded-lg border-2 border-slate-500 dark:border-white flex flex-col items-center justify-between"
  >
    <div
      class="h-12 w-full flex flex-col items-center justify-center border-b-2 border-slate-500 dark:border-white text-xl md:text-2xl font-semibold"
    >
      {{ organization.name }}
    </div>

    <div class="px-4 py-4 flex flex-col items-center justify-evenly">
      <div>
        {{ getShortDesc(organization.description) }}
      </div>
    </div>
    <div class="mb-2">
      <Btn>Donate Now</Btn>
    </div>
  </div>
</template>

<script setup>
import Btn from "../components/Btn.vue";
defineProps({
  organization: Object,
});

function getShortDesc(desc) {
  let shortDesc = desc.substring(0, 77);
  if (desc.length > 77) shortDesc += "...";
  return shortDesc;
}
</script>
