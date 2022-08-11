<template>
  <Loading v-if="loading" msg="Loading Organization" />
  <DefaultLayout>
    <div
      class="px-10 py-10 grid grid-flow-row grid-cols-1 md:grid-cols-3 gap-4"
    >
      <!-- Organiztion Info -->
      <div class="md:px-10 col-span-2 row-span-1 flex flex-col items-start">
        <div class="text-xl font-bold py-4">{{ orgInfo.name }}</div>
        <div>{{ orgInfo.description }}</div>
        <div class="py-4 text-xl font-semibold">
          Balance: {{ orgInfo.balance }} /-
        </div>
        <Btn class="self-center">
          <font-awesome-icon class="mr-2" icon="paper-plane" /> Donate
        </Btn>
      </div>

      <!-- Top Donations -->
      <div
        class="col-end-0 mt-6 py-6 flex flex-col items-center justify-center bg-slate-200/10 shadow-lg rounded-md md:mx-24 h-full"
      >
        <div class="font-semibold">Top Donations</div>
        <div
          class="w-48 mb-4 mt-2 mx-20 border-purple-500 border-b-2 rounded-lg"
        ></div>

        <div
          class="w-full py-1 px-10 flex flex-row items-center justify-between"
          v-for="(donation, index) in topDonation"
          :key="donation.id"
        >
          <div>{{ index + 1 }}. {{ donation.donor.name }}</div>
          <div>{{ donation.amount }} /-</div>
        </div>
      </div>
      <!-- Spendings -->
      <div
        class="col-span-2 mt-6 mb-8 flex flex-col items-center justify-start"
      >
        <div class="py-6 w-full flex flex-row items-center justify-center">
          <div
            class="w-full md:mx-10 border-blue-500 border-b-2 rounded-md"
          ></div>
          <div class="px-8 text-xl font-semibold">Spendings</div>
          <div
            class="w-full md:mx-10 border-blue-500 border-b-2 rounded-md"
          ></div>
        </div>
        <div>
          <div v-for="spending in spendings" :key="spending.id">
            {{ spending.amount }} /- | {{ spending.description }}
          </div>
        </div>
      </div>

      <!-- Latest Donations -->
      <div
        class="col-end-0 mt-6 py-6 flex flex-col items-center justify-center bg-slate-200/10 shadow-lg rounded-md md:mx-24 h-full"
      >
        <div class="font-semibold">Latest Donations</div>
        <div
          class="w-48 mb-4 mt-2 mx-20 border-cyan-500 border-b-2 rounded-lg"
        ></div>

        <div
          class="w-full py-1 px-10 flex flex-row items-center justify-between"
          v-for="(donation, index) in latestDonation"
          :key="donation.id"
        >
          <div>
            <div>{{ index + 1 }}. {{ donation.donor.name }}</div>
            <div class="text-sm font-medium">
              {{ getTimeDiff(donation.created) }}
            </div>
          </div>
          <div>{{ donation.amount }} /-</div>
        </div>

        <div
          class="text-sm underline font-semibold text-gray-500 mt-4 cursor-pointer"
        >
          All Donations
        </div>
      </div>
    </div>
  </DefaultLayout>
</template>

<script setup>
import DefaultLayout from "../layout/Default.vue";
import Loading from "../components/Loading.vue";
import { useRoute } from "vue-router";
import { ref, onMounted } from "vue";
import api from "../api";
import Btn from "../components/Btn.vue";

const route = useRoute();

const loading = ref(false);
const orgInfo = ref({});
const spendings = ref([]);
const latestDonation = ref([]);
const topDonation = ref([]);

onMounted(() => {
  loadOrg();
});

function loadSpendings() {
  api
    .get(`/spending/latest/${route.params.id}`)
    .then((response) => {
      spendings.value = response.data;
    })
    .catch((err) => {
      console.log("Latest Spending", err);
    });
}

function loadLatestDonations() {
  api
    .get(`/donation/latest/${route.params.id}`)
    .then((response) => {
      latestDonation.value = response.data;
    })
    .catch((err) => {
      console.log("Latest Donation", err);
    });
}

function loadTopDonations() {
  api
    .get(`/donation/top/${route.params.id}`)
    .then((response) => {
      topDonation.value = response.data;
    })
    .catch((err) => {
      console.log("Top Donation", err);
    });
}

function loadOrg() {
  loading.value = true;
  api
    .get(`/org/det/${route.params.id}`)
    .then((response) => {
      loading.value = false;
      orgInfo.value = response.data;

      loadLatestDonations();
      loadSpendings();
      loadTopDonations();
    })
    .catch((err) => {
      loading.value = false;
    });
}

function getTimeDiff(created) {
  let createdTime = new Date(created);
  let timeNow = new Date();
  let diff = timeNow - createdTime;

  let msec = diff;
  let hh = Math.floor(msec / 1000 / 60 / 60);
  msec -= hh * 1000 * 60 * 60;
  let mm = Math.floor(msec / 1000 / 60);
  msec -= mm * 1000 * 60;
  let ss = Math.floor(msec / 1000);
  msec -= ss * 1000;

  if (hh > 0) {
    if ((hh = 1)) return hh + " hour ago";
    else return hh + " hours ago";
  } else if (mm > 0) {
    if ((mm = 1)) return mm + " minutes ago";
    else return mm + " minute ago";
  } else {
    if ((ss = 1)) return ss + " seconds ago";
    else return ss + " second ago";
  }
}
</script>
