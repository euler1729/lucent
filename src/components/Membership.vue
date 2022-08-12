<template>
  <Modal :isOpen="isOpen" title="Membership">
    <div class="relative bg-white rounded-lg dark:bg-gray-700 font-bangla">
      <div class="pb-6 px-4">
        <form class="space-y-6" @submit.prevent="processSubmission(0)">
          <div v-if="props.orgInfo.requireCode">
            <label
              for="name"
              class="block mb-2 text-sm font-medium text-gray-900 dark:text-gray-300"
              >Membership Code</label
            >
            <input
              type="text"
              name="code"
              id="code"
              v-model="code"
              class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white"
              placeholder="Membership Code"
              required
            />
          </div>

          <div v-if="props.orgInfo.requireNID">
            <label
              for="name"
              class="block mb-2 text-sm font-medium text-gray-900 dark:text-gray-300"
              >NID</label
            >
            <input
              type="text"
              name="nid"
              id="nid"
              v-model="nid"
              class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white"
              placeholder="NID"
              required
            />
          </div>

          <div class="dark:text-gray-300 text-center">
            <input type="checkbox" />
            I agree to the
            <span class="underline"> Terms and Conditions. </span>
          </div>

          <button
            type="submit"
            class="w-full text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
          >
            Request for a membership
          </button>
        </form>
        <!-- {{ props.orgInfo }} -->
      </div>
    </div>
  </Modal>
</template>

<script setup>
import { ref } from "vue";
import Modal from "./Modal.vue";
import { useInf } from "../stores/inf.js";
import api from "../api.js";
import { useUserStore } from "../stores/user.js";

const inf = useInf();
const user = useUserStore();
const emit = defineEmits(["onsuccess"]);

const props = defineProps({
  isOpen: {
    type: Boolean,
    default: false,
  },
  orgInfo: {
    type: Object,
    required: true,
  },
});
const isOpen = ref(props.isOpen);
const orgInfo = props.orgInfo;
const code = ref();
const nid = ref();

function processSubmission(counter) {
  //   console.log(orgInfo.id);
  if (counter < 2) {
    api
      .post(
        "/membership/request",
        {
          organizationId: orgInfo.id,
        },
        {
          headers: {
            AUTHORIZATION: `Bearer ${user.access_token}`,
          },
        }
      )
      .then((response) => {
        emit("onsuccess");
        modalKey.value = Math.random();
        isOpen.value = false;
      })
      .catch((err) => {
        if (err.response.status == 403) {
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
              processSubmission(counter + 1);
            })
            .catch((err) => {
              user.logout();
              loading.value = false;
            });
        }
      });
  }
}
</script>
