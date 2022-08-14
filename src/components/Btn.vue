<template>
  <button
    type="button"
    class="text-white text-xl bg-gradient-to-r from-purple1 to-purple2 hover:bg-gradient-to-br focus:ring-4 focus:outline-none focus:ring-purple-300 dark:focus:ring-purple-800 shadow-lg shadow-purple1/50 dark:shadow-lg dark:shadow-purple2/80 font-semibold font-inter rounded-lg px-5 py-2.5 text-center mr-2 mb-2"
  >
    <slot />
  </button>
</template>

<script setup>
const props = defineProps({
  color: {
    type: String,
    default: "bg-blue-500",
  },
});
</script>
