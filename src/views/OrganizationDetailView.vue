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

        <div
          v-if="user.role == user.managerRole && orgManagerId == user.id"
          class="flex flex-row items-center justify-center self-center"
        >
          <Btn @click="navMembershipRequest" class="mr-2">
            <font-awesome-icon class="mr-2" icon="user-group" /> Membership
            Requests
          </Btn>
          <Btn @click="spend">
            <font-awesome-icon class="mr-2" icon="hand-holding-heart" /> Spend
          </Btn>
        </div>
        <Btn v-else @click="donate" class="self-center">
          <font-awesome-icon class="mr-2" icon="paper-plane" /> Donate
        </Btn>
      </div>

      <!-- Top Donations -->
      <div
        class="col-end-0 mt-6 py-6 flex flex-col items-center justify-center bg-slate-200/10 shadow-lg rounded-md md:mx-24 h-full"
      >
        <div class="font-semibold">Top Donations</div>
        <div
          class="w-48 mb-4 mt-2 px-20 border-purple-500 border-b-2 rounded-lg"
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
          <div
            @click="navSpendings"
            class="self-center text-sm underline font-semibold text-gray-500 mt-4 cursor-pointer text-center"
          >
            See details spendings
          </div>
        </div>
      </div>

      <!-- Latest Donations -->
      <div
        class="col-end-0 mt-6 py-6 flex flex-col items-center justify-center bg-slate-200/10 shadow-lg rounded-md md:mx-24 h-full"
      >
        <div class="font-semibold">Latest Donations</div>
        <div
          class="w-48 mb-4 mt-2 px-20 border-cyan-500 border-b-2 rounded-lg"
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
          @click="navDonations"
          class="text-sm underline font-semibold text-gray-500 mt-4 cursor-pointer"
        >
          All Donations
        </div>
      </div>
    </div>
  </DefaultLayout>
  <Login :isOpen="loginModal" @onsuccess="donate" :key="loginModalKey" />
  <Membership
    :isOpen="memebrshipModal"
    :orgInfo="orgInfo"
    @onsuccess="onMemberShipRequest"
    :key="memebrshipModalKey"
  />
  <MembershipPending
    :isOpen="memebrshipRejectedModal"
    :key="memebrshipRejectedModalKey"
  />
</template>

<script setup>
import DefaultLayout from "../layout/Default.vue";
import Loading from "../components/Loading.vue";
import { useRoute, useRouter } from "vue-router";
import { ref, onMounted } from "vue";
import api from "../api";
import Btn from "../components/Btn.vue";
import Login from "../components/Login.vue";
import Membership from "../components/Membership.vue";
import MembershipPending from "../components/MembershipPending.vue";
import { useUserStore } from "../stores/user";

const user = useUserStore();
const route = useRoute();
const router = useRouter();

const loading = ref(false);
const orgInfo = ref({});
const orgManagerId = ref(null);
const spendings = ref([]);
const latestDonation = ref([]);
const topDonation = ref([]);

const loginModal = ref(false);
const loginModalKey = ref(0);
const memebrshipModal = ref(false);
const memebrshipModalKey = ref(0);
const memebrshipRejectedModal = ref(false);
const memebrshipRejectedModalKey = ref(0);

const membership = ref({
  checked: false,
  member: false,
  nid: null,
  membershipCode: null,
  approved: false,
});

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
      orgManagerId.value = response.data.manager.id;

      loadLatestDonations();
      loadSpendings();
      loadTopDonations();
    })
    .catch((err) => {
      loading.value = false;
    });
}

function navDonations() {
  router.push(`/donations/${route.params.id}`);
}

function navSpendings() {
  router.push(`/spendings/${route.params.id}`);
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

function checkMembership(counter) {
  if (counter < 2) {
    membership.value.checked = false;
    loading.value = true;
    api
      .get(`/membership/check/${route.params.id}`, {
        headers: {
          AUTHORIZATION: `Bearer ${user.access_token}`,
        },
      })
      .then((response) => {
        membership.value.checked = true;
        membership.value.member = true;
        membership.value.approved = response.data.approved;
        membership.value.nid = response.data.nid;
        membership.value.membershipCode = response.data.membershipCode;
        loading.value = false;
        donate();
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
              checkMembership(counter + 1);
            })
            .catch((err) => {
              user.logout();
              loading.value = false;
            });
        } else {
          console.log(err);
          membership.value.approved = false;
          membership.value.member = false;
          membership.value.checked = true;
          loading.value = false;
          donate();
        }
      });
  }
}

function onMemberShipRequest() {
  checkMembership(0);
}

function donate() {
  if (!user.loggedIn) {
    loginModal.value = true;
    loginModalKey.value = Math.random();
  } else {
    if (membership.value.checked) {
      if (membership.value.member) {
        if (membership.value.approved) {
          // Membership approved - Process to donate
          console.log("Approved");
        } else {
          memebrshipRejectedModal.value = true;
          memebrshipRejectedModalKey.value = Math.random();
        }
      } else {
        // Not a member - Open the membership modal
        console.log("Not a member");
        memebrshipModal.value = true;
        memebrshipModalKey.value = Math.random();
      }
    } else {
      // Memberhip hasnt been checked
      console.log("Membership is to be decided");
      checkMembership(0);
    }
  }
}

function navMembershipRequest() {
  router.push(`/membership-requests/${route.params.id}`);
}
</script>
