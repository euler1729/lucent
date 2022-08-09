<template>
  <div
    :class="`${color} hover:bg-blue-700 text-white font-medium py-1 px-4 rounded cursor-pointer`"
  >
    <slot />
  </div>
</template>

<script setup>
const props = defineProps({
  color: {
    type: String,
    default: "bg-blue-500",
  },
});
</script>
