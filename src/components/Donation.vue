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
              class="bg-darkblue/20 text-lg rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 px-5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400"
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
              class="bg-darkblue/20 text-lg rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 px-5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400"
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
              class="bg-darkblue/20 text-lg rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 px-5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400"
              placeholder="Payment Gateway"
            />
          </div>
          <div class="flex flex-row items-center justify-center">
            <button
              :disabled="donationOnProcess"
              type="submit"
              class="text-white text-xl bg-gradient-to-r from-purple1 to-purple2 hover:bg-gradient-to-br focus:ring-4 focus:outline-none focus:ring-purple-300 dark:focus:ring-purple-800 shadow-lg shadow-purple1/50 dark:shadow-lg dark:shadow-purple2/80 font-semibold font-inter rounded-lg px-5 py-2.5 text-center mb-2"
            >
              {{ donationLabel }}
            </button>
          </div>
        </form>

        <div v-if="donationSuccesful" class="text-center text-gray-300">
          <div class="text-2xl p-6 font-body font-bold">Donation Succesful</div>
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
