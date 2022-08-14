<template>
  <Modal :isOpen="isOpen" :key="modalKey" title="Donation">
    <div class="relative bg-white rounded-lg dark:bg-gray-700">
      <div class="pb-6 px-4">
        <form
          v-if="donationSuccesful == false"
          class="space-y-6"
          @submit.prevent="processSubmission(0)"
        >
          <div>
            <label
              for="amount"
              class="block mb-2 text-sm font-medium text-gray-900 dark:text-gray-300"
              >Amount</label
            >
            <input
              type="text"
              name="amount"
              id="amount"
              v-model="amount"
              class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white"
              placeholder="Amount"
              required
            />
          </div>

          <div>
            <label
              for="trxid"
              class="block mb-2 text-sm font-medium text-gray-900 dark:text-gray-300"
              >TrxId</label
            >
            <input
              type="text"
              name="trxid"
              id="trxid"
              v-model="trxid"
              class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white"
              placeholder="TrxId"
              required
            />
          </div>
          <div>
            <label
              for="gateway"
              class="block mb-2 text-sm font-medium text-gray-900 dark:text-gray-300"
              >Payment Gateway</label
            >
            <input
              disabled
              type="text"
              name="gateway"
              id="gateway"
              v-model="gateway"
              class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white"
              placeholder="Payment Gateway"
            />
          </div>
          <button
            :disabled="donationOnProcess"
            type="submit"
            class="w-full text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
          >
            {{ donationLabel }}
          </button>
        </form>

        <div v-if="donationSuccesful" class="text-center text-gray-300">
          <div>Donation Succesful</div>
          <Btn class="mt-4" @click="closeModal">Ok</Btn>
        </div>
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
import Btn from "./Btn.vue";

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
const modalKey = ref(0);
const orgInfo = props.orgInfo;
const amount = ref();
const gateway = ref("mock");
const trxid = ref("12345678");

const donationLabel = ref("Donate");
const donationSuccesful = ref(false);
const donationOnProcess = ref(false);

function processSubmission(counter) {
  //   console.log(orgInfo.id);
  if (counter < 2) {
    donationLabel.value = "Donating";
    donationOnProcess.value = true;
    api
      .post(
        "/donate",
        {
          organizationID: orgInfo.id,
          amount: amount.value,
          gateway: gateway.value,
        },
        {
          headers: {
            AUTHORIZATION: `Bearer ${user.access_token}`,
          },
        }
      )
      .then((response) => {
        donationSuccesful.value = true;
        donationOnProcess.value = false;
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

function closeModal() {
  isOpen.value = false;
  modalKey.value = Math.random();
  emit("onsuccess");
}
</script>
