<template>
  <Modal :isOpen="isOpen" title="Membership Approval" :key="openKey">
    <div class="relative bg-white rounded-lg dark:bg-gray-700 font-bangla">
      <div class="pb-6 px-4 dark:text-gray-300">
        Your membership is pending. You'll be notified when your membership
        request is accepted.
      </div>

      <Btn class="text-center" @click="closeModal">Ok</Btn>
    </div>
  </Modal>
</template>

<script setup>
import Modal from "./Modal.vue";
import Btn from "./Btn.vue";
import { ref } from "vue";
const props = defineProps({
  isOpen: {
    type: Boolean,
    default: false,
  },
});

const isOpen = ref(props.isOpen);
const openKey = ref(0);

function closeModal() {
  isOpen.value = false;
  openKey.value = Math.random();
}
</script>
