<template>
  <Modal :isOpen="isOpen" :key="modalKey" title="Spending">
    <div class="relative bg-white rounded-lg dark:bg-gray-700">
      <div class="pb-6 px-4">
        <form
          v-if="spendingSuccesful == false"
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
              for="description"
              class="block mb-2 text-sm font-medium text-gray-900 dark:text-gray-300"
              >Description</label
            >
            <textarea
              required
              type="text"
              name="description"
              id="description"
              v-model="description"
              class="bg-darkblue/20 text-lg rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 px-5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400"
              placeholder="Spending description"
            />
          </div>

          <div>
            {{ errLabel }}
          </div>
          <div class="flex flex-row items-center justify-center">
            <button
              :disabled="spendingOnProcess"
              type="submit"
              class="text-white text-xl bg-gradient-to-r from-purple1 to-purple2 hover:bg-gradient-to-br focus:ring-4 focus:outline-none focus:ring-purple-300 dark:focus:ring-purple-800 shadow-lg shadow-purple1/50 dark:shadow-lg dark:shadow-purple2/80 font-semibold font-inter rounded-lg px-5 py-2.5 text-center mb-2"
            >
              {{ donationLabel }}
            </button>
          </div>
        </form>

        <div v-if="spendingSuccesful" class="text-center text-gray-300">
          <div>Spending Succesful</div>
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
const description = ref("");
const trxid = ref("12345678");

const donationLabel = ref("Spend");
const spendingSuccesful = ref(false);
const spendingOnProcess = ref(false);

const errLabel = ref("");

function processSubmission(counter) {
  //   console.log(orgInfo.id);
  if (counter < 2) {
    donationLabel.value = "Processing";
    spendingOnProcess.value = true;
    api
      .post(
        "/spending/request",
        {
          oganizationId: orgInfo.id,
          amount: amount.value,
          description: description.value,
        },
        {
          headers: {
            AUTHORIZATION: `Bearer ${user.access_token}`,
          },
        }
      )
      .then((response) => {
        spendingSuccesful.value = true;
        spendingOnProcess.value = false;
        errLabel.value = "";
      })
      .catch((err) => {
        if (err.response.status == 403) {
          if (err.response.data) {
            for (let key in err.response.data) {
              errLabel.value = err.response.data[key];
            }
          } else {
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
