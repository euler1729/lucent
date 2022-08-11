<template>
  <DefaultLayout> Donations {{ $route.params.orgId }}</DefaultLayout>
</template>

<script setup>
import DefaultLayout from "../layout/Default.vue";
import api from "../api";
</script>
