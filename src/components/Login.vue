<template>
  <Modal
    :isOpen="isOpen"
    :key="modalKey"
    :title="labels[actionMode][inf.lang].modal"
  >
    <div class="relative bg-white rounded-lg dark:bg-gray-700 font-bangla">
      <div class="pb-6 px-4">
        <form class="space-y-6" @submit.prevent="processSubmission">
          <div v-if="actionMode == 'orgRegistration'">
            <label
              for="name"
              class="block mb-2 text-sm font-medium text-gray-900 dark:text-gray-300"
              >{{ labels.inputLabel.orgName[inf.lang] }}</label
            >
            <input
              type="text"
              name="name"
              id="name"
              v-model="name"
              class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white"
              :placeholder="labels.inputLabel.orgName[inf.lang]"
              required
            />
          </div>
          <div v-if="actionMode == 'orgRegistration'">
            <label
              for="name"
              class="block mb-2 text-sm font-medium text-gray-900 dark:text-gray-300"
              >{{ labels.inputLabel.orgDesc[inf.lang] }}</label
            >
            <input
              type="text"
              name="name"
              id="name"
              v-model="name"
              class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white"
              :placeholder="labels.inputLabel.orgDesc[inf.lang]"
              required
            />
          </div>
          <div
            v-if="
              actionMode == 'registration' || actionMode == 'orgRegistration'
            "
          >
            <label
              for="name"
              class="block mb-2 text-sm font-medium text-gray-900 dark:text-gray-300"
              >{{ labels.inputLabel.name[inf.lang] }}</label
            >
            <input
              type="text"
              name="name"
              id="name"
              v-model="name"
              class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white"
              placeholder="Your name"
              required
            />
          </div>
          <div>
            <label
              for="phone"
              class="block mb-2 text-sm font-medium text-gray-900 dark:text-gray-300"
              >{{ labels.inputLabel.phone[inf.lang] }}</label
            >
            <input
              type="phone"
              name="phone"
              id="phone"
              v-model="phone"
              class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white"
              placeholder="01*********"
              required
            />
          </div>
          <div>
            <label
              for="password"
              class="block mb-2 text-sm font-medium text-gray-900 dark:text-gray-300"
              >{{ labels.inputLabel.password[inf.lang] }}</label
            >
            <input
              type="password"
              name="password"
              id="password"
              v-model="password"
              placeholder="••••••••"
              class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white"
              required
            />
          </div>
          <div class="flex justify-center" v-if="loginerror">
            <div class="flex items-center"></div>
            <span href="#" class="text-sm text-red-500">{{ errlabel }}</span>
          </div>
          <button
            type="submit"
            class="w-full text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
          >
            {{ labels[actionMode][inf.lang].submitBtn }}
          </button>
          <div
            class="flex flex-row items-center text-gray-500 dark:text-gray-300"
          ></div>

          <div class="flex flex-row items-center justify-center">
            <div
              @click="toggleMode(labels[actionMode].toggleTo)"
              class="mr-2 underline text-blue-400 cursor-pointer flex flex-col items-center justify-center"
            >
              {{ labels[actionMode][inf.lang].toggleBtn }}
            </div>
            <span class="text-white dark:text-white">{{
              labels.inputLabel.or[inf.lang]
            }}</span>
            <div
              @click="toggleMode(labels[actionMode].toggleTo2)"
              class="ml-2 underline text-blue-400 cursor-pointer flex flex-col items-center justify-center"
            >
              {{ labels[actionMode][inf.lang].toggleBtn2 }}
            </div>
          </div>
        </form>
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
});

let orgName = ref("");
let orgDesc = ref("");
let name = ref("");
let phone = ref("");
let password = ref("");
const loginerror = ref(false);
const errlabel = ref("Failed to login. Please check your credentials.");

const labels = ref({
  inputLabel: {
    phone: {
      en: "Your phone",
      bn: "ফোন নম্বর",
    },
    name: {
      en: "Your name",
      bn: "নাম",
    },

    password: {
      en: "Your password",
      bn: "পাসওয়ার্ড",
    },

    orgName: {
      en: "Organization Name",
      bn: "পাসওয়ার্ড",
    },

    orgDesc: {
      en: "Organization Description",
      bn: "পাসওয়ার্ড",
    },

    or: {
      en: " | ",
      bn: " | ",
    },
  },

  login: {
    en: {
      modal: "Sign in to continue",
      submitBtn: "Login to your account",
      toggleBtn: "Create account",
      toggleBtn2: "Register an Organization",
    },
    bn: {
      modal: "লগইন করুন",
      submitBtn: "লগইন করুন",
      toggleBtn: "রেজিস্ট্রেশন করুন",
      toggleBtn2: "Organization রেজিস্ট্রেশন করুন",
    },
    toggleTo: "registration",
    toggleTo2: "orgRegistration",
  },
  registration: {
    en: {
      modal: "Create Account",
      submitBtn: "Sign up",
      toggleBtn: "Login instead",
      toggleBtn2: "Register an Organization",
    },
    bn: {
      modal: "অ্যাকাউন্ট খুলুন",
      submitBtn: "রেজিস্ট্রেশন করুন",
      toggleBtn: "লগইন করুন",
      toggleBtn2: "Organization রেজিস্ট্রেশন করুন",
    },
    toggleTo: "login",
    toggleTo2: "orgRegistration",
  },
  orgRegistration: {
    en: {
      modal: "Register Organiztion",
      submitBtn: "Create Organiztion",
      toggleBtn: "Login instead",
      toggleBtn2: "Create an account",
    },
    bn: {
      modal: "Create an account",
      submitBtn: "Organization রেজিস্ট্রেশন করুন",
      toggleBtn: "লগইন করুন",
      toggleBtn2: "রেজিস্ট্রেশন করুন",
    },
    toggleTo: "login",
    toggleTo2: "registration",
  },
});

const actionMode = ref("login");

const isOpen = ref(props.isOpen);
const modalKey = ref(0);

function login() {
  labels.value["login"][inf.lang].submitBtn = "Logging you in";
  loginerror.value = false;
  api
    .get("/user/login", {
      params: {
        phone: phone.value,
        password: password.value,
      },
    })
    .then((response) => {
      user.setToken(response.data.access_token, response.data.refresh_token);

      api
        .get("/user/profile", {
          headers: {
            AUTHORIZATION: `Bearer ${response.data.access_token}`,
          },
        })
        .then((response) => {
          labels.value["login"][inf.lang].submitBtn = "Logged in";
          user.setUser(response.data);
          emit("onsuccess");
          modalKey.value = Math.random();
          isOpen.value = false;
        });
    })
    .catch((err) => {
      labels.value["login"][inf.lang].submitBtn = "Login to your account";
      errlabel.value = "Failed to login. Please check your credentials.";
      loginerror.value = true;
    });
}

function registration() {
  loginerror.value = false;
  labels.value["registration"][inf.lang].submitBtn = "Signing up";
  api
    .post("/user/registration", {
      name: name.value,
      phone: phone.value,
      password: password.value,
    })
    .then((response) => {
      login();
      labels.value["registration"][inf.lang].submitBtn = "Signed up";
    })
    .catch((err) => {
      for (let key in err.response.data) {
        errlabel.value = err.response.data[key];
      }
      loginerror.value = true;
      labels.value["registration"][inf.lang].submitBtn = "Sign up";
    });
}

function orgRegistration() {
  loginerror.value = false;
  labels.value["orgRegistration"][inf.lang].submitBtn = "Creating Organization";
  api
    .post("/org/registration", {
      orgName: orgName.value,
      orgDescription: orgDesc.value,
      name: name.value,
      phone: phone.value,
      password: password.value,
    })
    .then((response) => {
      login();
      labels.value["orgRegistration"][inf.lang].submitBtn =
        "Organization Created";
    })
    .catch((err) => {
      for (let key in err.response.data) {
        errlabel.value = err.response.data[key];
      }
      loginerror.value = true;
      labels.value["orgRegistration"][inf.lang].submitBtn =
        "Create Organization";
    });
}

function processSubmission() {
  if (actionMode.value == "login") login();
  else if (actionMode.value == "registration") registration();
  else if (actionMode.value == "orgRegistration") orgRegistration();
}

function toggleMode(toggleTo) {
  actionMode.value = toggleTo;
}
</script>
