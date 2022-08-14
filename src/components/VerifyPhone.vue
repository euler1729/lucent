<template>
  <Modal :isOpen="isOpen" title="Account Verification" :key="openKey">
    <div class="relative bg-white rounded-lg dark:bg-gray-700">
      <div class="pb-6 dark:text-gray-300">
        Please enter the code sent to you phone to verify your account.
      </div>

      <form class="space-y-6" @submit.prevent="verifyAccount">
        <label
          for="name"
          class="block mb-2 text-sm font-medium text-gray-900 dark:text-gray-300"
          >Verification Code</label
        >
        <input
          type="text"
          name="code"
          id="code"
          v-model="code"
          class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white"
          placeholder="######"
          required
        />
        <div
          @click="resend"
          class="cursor-pointer underline pb-6 dark:text-gray-300"
        >
          {{ resendLabel }}
        </div>
        <div class="flex flex-row items-center justify-center">
          <button
            type="submit"
            class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
          >
            {{ btnLabel }}
          </button>
        </div>
      </form>
    </div>
  </Modal>
</template>

<script setup>
import Modal from "./Modal.vue";
import Btn from "./Btn.vue";
import { ref } from "vue";
import { useUserStore } from "../stores/user";
import api from "../api";
const emit = defineEmits(["onsuccess"]);
const props = defineProps({
  isOpen: {
    type: Boolean,
    default: false,
  },
});

const user = useUserStore();
const isOpen = ref(props.isOpen);
const openKey = ref(0);

const code = ref(null);
const btnLabel = ref("Verify");
const resendLabel = ref("Resend the code");

function closeModal() {
  isOpen.value = false;
  openKey.value = Math.random();
}

function verifyAccount() {
  btnLabel.value = "Verifying";
  api
    .post(`/user/verify?code=${code.value}&phone=${user.phone}`)
    .then((response) => {
      btnLabel.value = "Verification Succesful";
      isOpen.value = false;
      openKey.value = Math.random();

      api
        .get("/user/profile", {
          headers: {
            AUTHORIZATION: `Bearer ${user.access_token}`,
          },
        })
        .then((response) => {
          user.setUser(response.data);
          emit("onsuccess");
        })
        .catch((err) => {
          user.logout();
        });
    })
    .catch((err) => {
      console.log(err);
      btnLabel.value = "Verification Failed. Please enter the correct code.";
    });
}

function resend(counter) {
  api
    .get("/user/resendcode", {
      headers: {
        AUTHORIZATION: `Bearer ${user.access_token}`,
      },
    })
    .then((response) => {
      resendLabel.value = "Code Sent";
    })
    .catch((err) => {
      if (err.response.status == 403 && err.response.data == null) {
        // refresh token
        api
          .get("/token/refresh", {
            headers: {
              AUTHORIZATION: `Bearer ${user.refresh_token}`,
            },
          })
          .then((refreshResponse) => {
            user.setToken(
              refreshResponse.data.access_token,
              refreshResponse.data.refresh_token
            );
            resend(counter + 1);
          })
          .catch((err) => {
            user.logout();
          });
      } else {
        console.log(err.response.data);
      }
    });
}
</script>
