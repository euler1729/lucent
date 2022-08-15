<template>
  <Loading v-if="loading" :msg="loadingLabel" />
  <DefaultLayout>
    <!-- Cover Photo -->
    <div class="flex flex-col md:mx-20 items-center">
      <div
        class="w-full h-64 md:h-96 md:rounded-lg self-center my-4 md:my-0 bg-cover bg-center"
        :style="'background-image: url(' + orgInfo.coverPicURL + ');'"
      ></div>
    </div>
    <div
      class="px-10 py-10 grid grid-flow-row grid-cols-1 md:grid-cols-3 gap-4 font-body"
    >
      <!-- Organiztion Info -->
      <div class="md:px-10 col-span-2 row-span-1 flex flex-col items-start">
        <div class="flex flex-col md:flex-row items-center justify-center md:items-start">
          <div>
            <div class="w-52 h-52 rounded-lg self-center my-4 md:my-0">
              <img class="rounded-lg" :src="orgInfo.profilePicURL" />
            </div>
          </div>
          <div class="ml-10 flex flex-col items-start h-full md:mt-4">
            <div class="text-3xl font-bold my-4 md:my-0">
              {{ orgInfo.name }}
            </div>
            <div class="mt-2 md:mt-8 font-semibold text-lg">
              {{ orgInfo.description }}
            </div>
          </div>
        </div>

        <div
          v-if="user.role == user.managerRole && orgManagerId == user.id"
          class="flex flex-row items-center justify-center self-center"
        >
          <Btn @click="navMembershipRequest" class="mr-2">
            <font-awesome-icon class="mr-2" icon="user-group" />
            {{ label.membershipRequests[inf.lang] }}
          </Btn>
          <Btn @click="spend">
            <font-awesome-icon class="mr-2" icon="hand-holding-heart" />
            {{ label.spend[inf.lang] }}
          </Btn>
        </div>

        <div
          class="mt-10 md:px-10 flex flex-col md:flex-row items-center md:justify-between md w-full"
        >
          <div class="py-4 text-2xl font-semibold text-darkblue">
            Balance: {{ orgInfo.balance }} /-
          </div>
          <Btn
            v-if="user.role != user.managerRole && orgManagerId != user.id"
            @click="donate"
            class="self-center"
          >
            <font-awesome-icon class="mr-2" icon="paper-plane" />
            {{ label.donate[inf.lang] }}
          </Btn>
        </div>
      </div>

      <!-- Top Donations -->
      <div
        class="col-end-0 mt-6 py-6 flex flex-col items-center justify-evenly bg-white/70 shadow-lg rounded-md md:mx-24 h-full"
      >
        <div>
          <div class="text-xl font-semibold text-center">
            {{ label.topDonation[inf.lang] }}
          </div>
          <div
            class="w-48 mb-4 mt-2 px-20 border-purple1 border-b-2 rounded-lg"
          ></div>
        </div>

        <div
          class="w-full text-lg py-1 px-10 flex flex-row items-center justify-between font-semibold"
          v-for="(donation, index) in topDonation"
          :key="donation.id"
        >
          <div>{{ index + 1 }}. {{ donation.donor.name }}</div>
          <div>{{ donation.amount }} /-</div>
        </div>
        <div
          @click="navDonations"
          class="text-sm underline font-semibold text-gray-500 mt-4 cursor-pointer"
        >
          All Donations
        </div>
      </div>
      <!-- Spendings -->
      <div
        class="col-span-2 mt-6 mb-8 flex flex-col items-center justify-start"
      >
        <div class="py-6 w-full flex flex-row items-center justify-center">
          <div
            class="w-full md:mx-10 border-purple2 border-b-2 rounded-md"
          ></div>
          <div class="px-8 text-xl font-semibold">
            {{ label.spendings[inf.lang] }}
          </div>
          <div
            class="w-full md:mx-10 border-purple2 border-b-2 rounded-md"
          ></div>
        </div>
        <div>
          <div class="text-lg" v-for="spending in spendings" :key="spending.id">
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
        class="col-end-0 mt-6 py-6 flex flex-col items-center justify-evenly bg-white/70 shadow-lg rounded-md md:mx-24 h-full"
      >
        <div>
          <div class="text-xl font-semibold text-center">
            {{ label.latestDonations[inf.lang] }}
          </div>

          <div
            class="w-48 mb-4 mt-2 px-20 border-purple1 border-b-2 rounded-lg"
          ></div>
        </div>

        <div
          class="w-full text-lg py-1 px-10 flex flex-row items-center font-body font-semibold justify-between"
          v-for="(donation, index) in latestDonation"
          :key="donation.id"
        >
          <div>
            <div>{{ index + 1 }}. {{ donation.donor.name }}</div>
            <div class="text-sm font-semibold">
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
  <Donation
    :isOpen="donationModal"
    :orgInfo="orgInfo"
    :key="donationModalKey"
    @onsuccess="handleDonationSuccess"
  />
  <Spending
    :isOpen="spendingModal"
    :orgInfo="orgInfo"
    :key="spendingModalKey"
    @onsuccess="donationSuccess"
  />
  <VerifyPhone
    :isOpen="verifyPhoneModal"
    @onsuccess="handleVerifyPhone"
    :key="verifyPhoneModalKey"
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
import Donation from "../components/Donation.vue";
import Spending from "../components/Spending.vue";
import VerifyPhone from "../components/VerifyPhone.vue";
import { useUserStore } from "../stores/user";
import { useInf } from "../stores/inf";

const user = useUserStore();
const inf = useInf();
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
const donationModal = ref(false);
const donationModalKey = ref(0);
const spendingModal = ref(false);
const spendingModalKey = ref(0);
const verifyPhoneModal = ref(false);
const verifyPhoneModalKey = ref(0);
const loadingLabel = ref("Loading");

const membership = ref({
  checked: false,
  member: false,
  nid: null,
  membershipCode: null,
  approved: false,
});

const label = {
  donate: {
    bn: "ডোনেট",
    en: "Donate",
  },
  spend: {
    bn: "খরচ করুন",
    en: "Spend",
  },
  membershipRequests: {
    bn: "মেম্বারশিপ অনুরোধ ",
    en: "Membership Requests",
  },
  donate: {
    bn: "ডোনেট",
    en: "Donate",
  },
  topDonation: {
    bn: "সর্বোচ্চ ডোনেশন",
    en: "Top Donations",
  },
  latestDonations: {
    bn: "Latest ডোনেশন",
    en: "Latest Donations",
  },
  spendings: {
    bn: "খরচ",
    en: "Spendings",
  },
};
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
  loadingLabel.value = "Loading Organization Information";
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
    loadingLabel.value = "Loading Membership Information";
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
  console.log(user.verified);
  if (!user.loggedIn) {
    loginModal.value = true;
    loginModalKey.value = Math.random();
  } else {
    if (user.verified) {
      if (membership.value.checked) {
        if (membership.value.member) {
          if (membership.value.approved) {
            // Membership approved - Process to donate
            console.log("Approved");
            donationModal.value = true;
            donationModalKey.value = Math.random();
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
    } else {
      verifyPhoneModal.value = true;
      verifyPhoneModalKey.value = Math.random();
    }
  }
}

function handleDonationSuccess() {
  memebrshipRejectedModal.value = false;
  memebrshipRejectedModalKey.value = Math.random();
  loadOrg(0);
}

function spend() {
  spendingModal.value = true;
  spendingModalKey.value = Math.random();
}

function donationSuccess() {
  spendingModal.value = false;
  spendingModalKey.value = Math.random();
  loadOrg(0);
}

function handleVerifyPhone() {
  // verifyPhoneModal.value = false;
  // verifyPhoneModalKey.value = Math.random();

  donate();
}

function navMembershipRequest() {
  router.push(`/membership-requests/${route.params.id}`);
}
</script>
